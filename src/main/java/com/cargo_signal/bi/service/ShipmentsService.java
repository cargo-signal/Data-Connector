package com.cargo_signal.bi.service;

import com.cargo_signal.bi.service.domain.DeviceTelemetry;
import com.cargo_signal.bi.service.domain.Shipment;
import com.cargo_signal.bi.service.domain.ShipmentAlert;
import com.cargo_signal.blobstorage.BlobStorageManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Integration with Cargo Signal shipment APIs to retrieve data and push to Azure.
 */
public class ShipmentsService {

  private BlobStorageManager blobManager = null;

  // TODO using qa for now
  private String host = "https://qa-api.cargosignal.dev"; // "https://api.cargosignal.com";
  private String shipmentsPath = "/shipments?status=Complete&from=%s&to=%s";
  private String shipmentAlertsPath = "/shipments/%s/alerts";
  private String shipmentTelemetryPath = "/shipments/%s/telemetry";

  private String fetchErrorMessage = "Exception when fetching %s for shipment ID %s: %s";
  private String parseErrorMessage = "Exception when parsing %s for shipment ID %s: %s";
  private String shipmentFetchErrorMessage = "Exception when fetching shipments for date range %s to %s: %s";
  private String shipmentParseErrorMessage = "Exception when parsing shipments for date range %s to %s: %s";

  private HttpClient httpClient;
  private ObjectMapper objectMapper;
  private SimpleDateFormat simpleDateFormat;
  private Logger logger;
  // TODO for now, this goes here
  private String bearerToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJFeHBlZGl0b3JzIEludGVybmF0aW9uYWwiLCJzdWIiOiJHMDM4MzMxMiIsImNvbXBhbnlJZCI6IkcwMzgzMzEyIiwibmFtZSI6Ikdvb2dsZSBJbmMuIiwidHlwZSI6IkNVU1RPTUVSIiwicmVxdWVzdG9yIjoiUmFuZHkuR291bGRAY2FyZ29zaWduYWwuY29tIiwicmVxdWVzdG9yRW1haWwiOiJSYW5keS5Hb3VsZEBjYXJnb3NpZ25hbC5jb20ifQ.-RaqfzSgJbc9DS-szPP0VZ2T-IPGabrggviiJi-JoG1YhQuAadYa5McyjXV--J0qYfrhMbwpVhmQm0nUi_lNRw";

  // All possible status values
  private List<String> statusValues = Arrays.asList("Draft", "Future", "Current", "Invalid", "Complete", "Canceled", "Deleted");

  public ShipmentsService(ExecutionContext context) {
    blobManager = new BlobStorageManager(context);
    logger = context.getLogger();
    httpClient = HttpClientBuilder.create().build();
    objectMapper = new ObjectMapper();
    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  }

  public String uploadShipments(String minDate) throws Exception {
    // TODO: How to pass the bearer token in? maybe some az env var, if such a thing exists?

    // TODO: validation on minDate format? or just pass through?

    // TODO: assumes API payloads always fit in memory

    // Container is only created once, then createBlob is called once per API call
    String containerName = createContainer();

    String today = simpleDateFormat.format(new Date());

    List<Shipment> shipments = getShipments(minDate, today + "T00:00:00.000Z");
    String rawShipments = objectMapper.writeValueAsString(shipments);
    blobManager.createBlob(containerName, "shipments" + today + ".json", rawShipments);

    List<ShipmentAlert> shipmentAlerts = getShipmentAlerts(shipments);
    String rawShipmentAlerts = objectMapper.writeValueAsString(shipmentAlerts);
    blobManager.createBlob(containerName, "shipmentAlerts" + today + ".json", rawShipmentAlerts);

    List<DeviceTelemetry> telemetries = getDeviceTelemetries(shipments);
    String rawShipmentTelemetry = objectMapper.writeValueAsString(telemetries);
    blobManager.createBlob(containerName, "shipmentTelemetry" + today + ".json", rawShipmentTelemetry);

    return containerName;
  }

  private List<Shipment> getShipments(String minDate, String maxDate) {
    String shipmentsUrl = host + String.format(shipmentsPath, minDate, maxDate);
    List<Shipment> shipments = Collections.EMPTY_LIST;
    try {
      String rawShipments = get(shipmentsUrl);
      shipments = objectMapper.readValue(rawShipments, new TypeReference<List<Shipment>>() {});
    } catch (JsonProcessingException e) {
      logger.warning(String.format(shipmentParseErrorMessage, minDate, maxDate, e.toString()));
    } catch (IOException e) {
      logger.warning(String.format(shipmentFetchErrorMessage, minDate, maxDate, e.toString()));
    }
    return shipments;
  }

  private List<DeviceTelemetry> getDeviceTelemetries(List<Shipment> shipments) {
    List<DeviceTelemetry> telemetries = new ArrayList<>();
    for (Shipment shipment : shipments) {
      String shipmentId = shipment.getShipmentId();
      if (shipmentId != null) {
        String shipmentsTelemetryUrl = host + String.format(shipmentTelemetryPath, shipmentId);
        try {
            String shipmentsTelemetry = get(shipmentsTelemetryUrl);
            telemetries.addAll(objectMapper.readValue(shipmentsTelemetry, new TypeReference<List<DeviceTelemetry>>() {}));
        } catch (JsonProcessingException e) {
          logger.warning(String.format(parseErrorMessage, "telemetry", shipmentId, e.toString()));
        } catch (IOException e) {
          logger.warning(String.format(fetchErrorMessage, "telemetry", shipmentId, e.toString()));
        }
      }
    }
    return telemetries;
  }

  /**
   * Performs a GET for each shipment ID in the shipments list
   * If a problem with a particular shipment, will log the problem and move on to the next shipment.
   */
  private List<ShipmentAlert> getShipmentAlerts(List<Shipment> shipments) {
    List<ShipmentAlert> shipmentAlerts = new ArrayList<>();
    for (Shipment shipment : shipments) {
      String shipmentId = shipment.getShipmentId();
      if (shipmentId != null) {
        String shipmentsAlertUrl = host + String.format(shipmentAlertsPath, shipmentId);
        try {
          String shipmentsAlerts = get(shipmentsAlertUrl);
          shipmentAlerts.addAll(objectMapper.readValue(shipmentsAlerts, new TypeReference<List<ShipmentAlert>>() {}));
        } catch (JsonProcessingException e) {
          logger.warning(String.format(parseErrorMessage, "alerts", shipmentId, e.toString()));
        } catch (IOException e) {
          logger.warning(String.format(fetchErrorMessage, "alerts", shipmentId, e.toString()));
        }
      }
    }
    return shipmentAlerts;
  }

  private String get(String url) throws IOException {
    HttpGet getRequest = new HttpGet(url);
    getRequest.addHeader(HttpHeaders.ACCEPT, "application/json");
    getRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);
    try {
      HttpResponse response = httpClient.execute(getRequest);
      if (response.getStatusLine().getStatusCode() == 200) {
        String result = IOUtils.toString((response.getEntity().getContent()), StandardCharsets.UTF_8);
        return result;
      } else if (response.getStatusLine().getStatusCode() == 404) {
        return "[]"; // FIXME endpoint should return empty list instead of 404
      } else {
        throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
      }
    } finally {
      getRequest.releaseConnection();
    }
  }

  private String createContainer() throws Exception {
    Instant instant = Instant.now();    
    String containerName = "connector-blob-" + blobManager.sanitizeContainerName(instant.toString());
    blobManager.createContainer(containerName);

    return containerName;
  }
}
