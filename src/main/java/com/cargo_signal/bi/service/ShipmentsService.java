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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

  private BlobStorageManager blobManager;

  private final String host = System.getenv("HOST");
  private final String shipmentsPath = System.getenv("SHIPMENTS_PATH");
  private final String shipmentAlertsPath = System.getenv("SHIPMENT_ALERTS_PATH");
  private final String shipmentTelemetryPath = System.getenv("SHIPMENT_TELEMETRY_PATH");
  private final String bearerToken = System.getenv("BEARER_TOKEN");

  private final String fetchErrorMessage = "Exception when fetching %s for shipment ID %s: %s";
  private final String parseErrorMessage = "Exception when parsing %s for shipment ID %s: %s";
  private final String shipmentFetchErrorMessage = "Exception when fetching shipments for date range %s to %s: %s";
  private final String shipmentParseErrorMessage = "Exception when parsing shipments for date range %s to %s: %s";

  private final String ContainerShipments = "connector-shipments";
  private final String ContainerAlerts = "connector-alerts";
  private final String ContainerTelemetry = "connector-telemetry";

  private HttpClient httpClient;
  private ObjectMapper objectMapper;
  private SimpleDateFormat simpleDateFormat;
  private Logger logger;

  // All possible status values
  private List<String> statusValues = Arrays.asList("Draft", "Future", "Current", "Invalid", "Complete", "Canceled", "Deleted");

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

  private List<Shipment> getShipments(String minDate, String maxDate) {
    String shipmentsUrl = host + String.format(shipmentsPath, minDate, maxDate);
    logger.info("Calling the shipments API - " + shipmentsUrl);

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
        return IOUtils.toString((response.getEntity().getContent()), StandardCharsets.UTF_8);
      } else {
        throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
      }
    } finally {
      getRequest.releaseConnection();
    }
  }

  private void createContainers() throws Exception {

    blobManager.createContainer(ContainerShipments);
    blobManager.createContainer(ContainerAlerts);
    blobManager.createContainer(ContainerTelemetry);

  }
}
