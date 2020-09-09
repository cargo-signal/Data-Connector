package com.cargo_signal.bi.service;

import com.cargo_signal.blobstorage.BlobStorageManager;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpStatus;

import java.time.Instant;

/**
 * Integration with Cargo Signal shipment APIs to retrieve data and push to Azure.
 */
public class Shipments {

  private BlobStorageManager blobManager = null;

  public Shipments(ExecutionContext context) {
    blobManager = new BlobStorageManager(context);
  }

  public String uploadShipments(String minDate) throws Exception {

    // TODO: Retrieve shipment data from each of the endpoints (Shipment, Telemetry, Alerts)
    // You will need a bearer token.  This may be the "Trucker API" key.  Talk to David.
    
    String serviceData = "{\"shipmentId\": \"ABC123\",\"shipmentName\": \"Hello World\"}";
    
    // TODO: Stubbed out code simply sends one JSON string.  Real code will need to call each API, get results and send to each to Blob Storage
    // Consider private methods that handle individual responsibilities and support re-use within this class.

    // TODO: Error conditions for individual API call.  Say we can only get 2 of the 3 API results, do we upload nothing or upload the two we have and the third is missing
    // or do we upload two and the third has some content in it such as "DATA_ERROR"?

    // TODO: Related to previous, in the event there is no data for one of the API calls, but the call itself was successful, do we upload nothing or do we upload with 
    // some content such as an empty array?
    
    // Container is only created once, then createBlob is called once per API call
    String containerName = createContainer();
          
    // TODO: hard-coded as shipments for now, each time you call this, will need different blob name (e.g. shipments, telemetry, alerts)
    blobManager.createBlob(containerName, "shipments", serviceData);

    return containerName;
  }

  private String createContainer() throws Exception {
    Instant instant = Instant.now();    
    String containerName = "connector-blob-" + blobManager.sanitizeContainerName(instant.toString());
    blobManager.createContainer(containerName);

    return containerName;
  }
}