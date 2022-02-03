package com.cargo_signal.bi.service;

import com.cargo_signal.bi.service.domain.*;
import com.cargo_signal.blobstorage.BlobStorageManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


/**
 * Integration with Cargo Signal shipment APIs to retrieve data and push to Azure.
 */

public class ShipmentsService {

    private final BlobStorageManager blobManager;

    private final String host = System.getenv("HOST");
    private final String shipmentsPath = System.getenv("SHIPMENTS_PATH");
    private final String shipmentAlertsPath = System.getenv("SHIPMENT_ALERTS_PATH");
    private final String shipmentTelemetryPath = System.getenv("SHIPMENT_TELEMETRY_PATH");

    private final String audience = System.getenv("AUDIENCE");
    private final String authorizationURL = System.getenv("AUTHORIZATION_URL");
    private final String clientId = System.getenv("CLIENT_ID");
    private final String clientSecret = System.getenv("CLIENT_SECRET");
    private final String grantType = "client_credentials";

    private final String fetchErrorMessage = "Exception when fetching %s for shipment ID %s: %s";
    private final String parseErrorMessage = "Exception when parsing %s for shipment ID %s: %s";
    private final String encodingErrorMessage = "Exception when encoding shipment reference %s: error: %s";
    private final String shipmentFetchErrorMessage = "Exception when fetching shipments for date range %s to %s: %s";
    private final String shipmentParseErrorMessage = "Exception when parsing shipments for date range %s to %s: %s";

    private final String ContainerShipments = "connector-shipments";
    private final String ContainerAlerts = "connector-alerts";
    private final String ContainerTelemetry = "connector-telemetry";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final SimpleDateFormat simpleDateFormat;
    private final Logger logger;

    private String bearerToken;

    // All possible status values
    // private final List<String> statusValues = Arrays.asList("Draft", "Future", "Current", "Invalid", "Complete", "Canceled", "Deleted");

    public ShipmentsService(ExecutionContext context) throws Exception {
        blobManager = new BlobStorageManager(context);
        logger = context.getLogger();
        httpClient = HttpClientBuilder.create().build();
        objectMapper = new ObjectMapper();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        createContainers();
    }

    public void uploadShipments(String minDate) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(minDate));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String fileDate = simpleDateFormat.format(calendar.getTime());

        getAuthenticationToken();

        List<Shipment> shipments = getShipments(minDate + "T00:00:00.000Z", fileDate + "T00:00:00.000Z");
        String rawShipments = objectMapper.writeValueAsString(shipments);
        blobManager.createBlob(ContainerShipments, "shipments" + minDate + ".json", rawShipments);

        List<ShipmentAlert> shipmentAlerts = getShipmentAlerts(shipments);
        String rawShipmentAlerts = objectMapper.writeValueAsString(shipmentAlerts);
        blobManager.createBlob(ContainerAlerts, "shipmentAlerts" + minDate + ".json", rawShipmentAlerts);

        List<DeviceTelemetry> telemetries = getDeviceTelemetries(shipments);
        String rawShipmentTelemetry = objectMapper.writeValueAsString(telemetries);
        blobManager.createBlob(ContainerTelemetry, "shipmentTelemetry" + minDate + ".json", rawShipmentTelemetry);
    }

    private List<Shipment> getShipments(String minDate, String maxDate) throws Exception{
        String shipmentsUrl = host + shipmentsPath;

        List<Shipment> shipments = new ArrayList<>();

        List<Status> searchStatus = new ArrayList<>();
        searchStatus.add(Status.COMPLETE);

        SearchShipmentsPayload ssp = new SearchShipmentsPayload();
        ssp.setStatus(searchStatus);
        ssp.setCompletedDateTimeFrom(minDate);
        ssp.setCompletedDateTimeTo(maxDate);
        String postBody = objectMapper.writeValueAsString(ssp);

        try {
            int currentPage = 0;
            boolean lastPage = false;
            do {
                String tmpURL = String.format(shipmentsUrl, currentPage);
                logger.info("Calling the shipments API - " + tmpURL);
                String responseText = post(tmpURL, postBody);
                ShipmentPageableContent pc = objectMapper.readValue(responseText, new TypeReference<ShipmentPageableContent>() {
                });

                shipments.addAll(pc.getContent());
                lastPage = pc.getLast();
                currentPage++;
            } while (!lastPage);
        } catch (JsonProcessingException e) {
            logger.warning(String.format(shipmentParseErrorMessage, minDate, maxDate, e));
        } catch (IOException e) {
            logger.warning(String.format(shipmentFetchErrorMessage, minDate, maxDate, e));
        }
        return shipments;
    }


    private List<DeviceTelemetry> getDeviceTelemetries(List<Shipment> shipments) throws InterruptedException {
        List<DeviceTelemetry> telemetries = new ArrayList<>();
        for (Shipment shipment : shipments) {
            String trackingNumber = shipment.getTrackingNumber();
            String customerId = shipment.getCustomerId();
            if (trackingNumber != null && customerId != null) {
                int currentPage = 0;
                boolean lastPage = false;
                try {
                    do {
                        String shipmentsTelemetryUrl = host + String.format(shipmentTelemetryPath,
                                URLEncoder.encode(trackingNumber, StandardCharsets.UTF_8.toString()),
                                URLEncoder.encode(customerId, StandardCharsets.UTF_8.toString()),
                                currentPage);

                        logger.info("making call to telemetry api - " + shipmentsTelemetryUrl);

                        String shipmentsTelemetry = get(shipmentsTelemetryUrl);
                        TelemetryPageableContent tpc = objectMapper.readValue(shipmentsTelemetry, new TypeReference<TelemetryPageableContent>() {
                        });
                        telemetries.addAll(tpc.getContent());

                        lastPage = tpc.getLast();
                        currentPage++;
                    } while (!lastPage);
                } catch (JsonProcessingException e) {
                    logger.warning(String.format(parseErrorMessage, "telemetry", trackingNumber, e));
                } catch (UnsupportedEncodingException e) {
                    logger.warning(String.format(encodingErrorMessage, trackingNumber, e));
                } catch (IOException e) {
                    logger.warning(String.format(fetchErrorMessage, "telemetry", trackingNumber, e));
                }
            }
        }
        return telemetries;
    }

    /**
     * Performs a GET for each shipment ID in the shipments list
     * If a problem with a particular shipment, will log the problem and move on to the next shipment.
     */
    private List<ShipmentAlert> getShipmentAlerts(List<Shipment> shipments) throws InterruptedException {
        List<ShipmentAlert> shipmentAlerts = new ArrayList<>();
        for (Shipment shipment : shipments) {
            String trackingNumber = shipment.getTrackingNumber();
            String customerId = shipment.getCustomerId();
            if (trackingNumber != null && customerId != null) {
                try {
                    String shipmentsAlertUrl = host + String.format(shipmentAlertsPath,
                            URLEncoder.encode(trackingNumber, StandardCharsets.UTF_8.toString()),
                            URLEncoder.encode(customerId, StandardCharsets.UTF_8.toString()));
                    logger.info("making call to get alerts api - " + shipmentsAlertUrl);
                    String shipmentsAlerts = get(shipmentsAlertUrl);
                    List<ShipmentAlert> sa = objectMapper.readValue(shipmentsAlerts, new TypeReference<List<ShipmentAlert>>() {
                    });
                    shipmentAlerts.addAll(sa);
                } catch (JsonProcessingException e) {
                    logger.warning(String.format(parseErrorMessage, "alerts", trackingNumber, e));
                } catch (UnsupportedEncodingException e) {
                    logger.warning(String.format(encodingErrorMessage, trackingNumber, e));
                } catch (IOException e) {
                    logger.warning(String.format(fetchErrorMessage, "alerts", trackingNumber, e));
                }
            }
        }
        return shipmentAlerts;
    }

    private void getAuthenticationToken() throws IOException {

        List<NameValuePair> payload = new ArrayList<NameValuePair>();
        payload.add(new BasicNameValuePair("audience", audience));
        payload.add(new BasicNameValuePair("client_id", clientId));
        payload.add(new BasicNameValuePair("client_secret", clientSecret));
        payload.add(new BasicNameValuePair("grant_type", grantType));

        HttpPost postRequest = new HttpPost(authorizationURL);
        postRequest.addHeader(HttpHeaders.ACCEPT, "application/json");

        postRequest.setEntity(new UrlEncodedFormEntity(payload));

        try {
            HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                final String responseText = IOUtils.toString((response.getEntity().getContent()), StandardCharsets.UTF_8);
                Authorization auth = objectMapper.readValue(responseText, new TypeReference<Authorization>() {
                });
                bearerToken = auth.getAccess_token();
            }

        } finally {
            postRequest.releaseConnection();
        }
    }

    private String post(String url, String payload) throws IOException {
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type", "application/json");
        post.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);

        try {
            StringEntity postBody = new StringEntity(payload);
            post.setEntity(postBody);

            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            } else {
                logger.warning("Failed : HTTP error code : " +
                                response.getStatusLine().getStatusCode() +
                                "with text: " +
                                response.getStatusLine().getReasonPhrase());
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }
        } finally {
            post.releaseConnection();
        }
    }

    private String get(String url) throws IOException,InterruptedException {
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader(HttpHeaders.ACCEPT, "application/json");
        getRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);

        try {
            for (int i =0; i < 5; i++) {
                HttpResponse response = httpClient.execute(getRequest);
                if (response.getStatusLine().getStatusCode() == 200) {
                    return IOUtils.toString((response.getEntity().getContent()), StandardCharsets.UTF_8);
                    // doing this for rate limiting
                } else if (response.getStatusLine().getStatusCode() == 503) {
                    logger.info("am being rate limited waiting a minute to try again");
                    TimeUnit.MINUTES.sleep(1);
                } else {
                    logger.warning("Failed : HTTP error code : " +
                            response.getStatusLine().getStatusCode() +
                            "with text: " +
                            response.getStatusLine().getReasonPhrase());
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
                }
            }
            throw new RemoteException("Failed : HTTP error code : 503, got rate limited to many times");
        }finally {
            getRequest.releaseConnection();
        }
    }

    private void createContainers() throws Exception {

        blobManager.createContainer(ContainerShipments);
        blobManager.createContainer(ContainerAlerts);
        blobManager.createContainer(ContainerTelemetry);

    }
}
