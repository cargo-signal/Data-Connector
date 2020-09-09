package com.cargo_signal.bi.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.TimerTrigger;

import com.cargo_signal.bi.service.Shipments;
import com.cargo_signal.blobstorage.BlobStorageManager;

import java.util.Optional;

/**
 * Cargo Signal BI Connector Azure Function
 */
public class Connector {
    /**
     * This function listens at endpoint "/api/health". To invoke it using "curl":
     * curl "{your host}/api/health"
     */
    @FunctionName("health")
    public HttpResponseMessage healthCheck(
            @HttpTrigger(
                name = "health",
                methods = {HttpMethod.GET},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Cargo Signal BI Health Check processed a request.");

        return request.createResponseBuilder(HttpStatus.OK).body("Ok").build();
    }

    /**
     * This function listens at endpoint "/api/shipments". To invoke it using "curl":
     * curl -d "HTTP Body" {your host}/api/shipments?minDate={utc datetime}
     */
    @FunctionName("shipments")    
    public HttpResponseMessage getShipments(
            @HttpTrigger(
                name = "shipments",
                methods = {HttpMethod.GET},
                authLevel = AuthorizationLevel.ANONYMOUS) // TODO: Once we have bearer token, change to AuthorizationLevel.FUNCTION
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Cargo Signal BI 'shipments' processed a request.");

        // Parse query parameter
        final String query = request.getQueryParameters().get("minDate");
        final String minDate = request.getBody().orElse(query);

        if (minDate == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("'minDate' parameter required in the query string or in the request body").build();
        } 

        try 
        {
            Shipments shipments = new Shipments(context);
            String containerName = shipments.uploadShipments(minDate);
            return request.createResponseBuilder(HttpStatus.OK).body(String.format("Uploaded shipment data to blob storage container %s", containerName)).build();
        } catch (Exception ex) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload shipment data.  Exception: " + ex).build();
        }        
    }

    @FunctionName("shipmentsTimer")
    public void getShipmentsTimer(
            @TimerTrigger(
            name = "shipmentsTimer",
            schedule = "*/15 * * * * *")  // Modify this CRON expression to set the schedule
            String timerInfo,
            final ExecutionContext context) {

        // CRON examples: https://crontab.guru/examples.html

        context.getLogger().info("Cargo Signal BI 'shipments' processed a timer request.");

        // TODO: MinDate should be set in configuration and read here.  Add a note to developer regarding this.

        // TODO: Enable the code below once development done (don't want it running every 15 seconds)
        /* try
        {
            Shipments shipments = new Shipments(context);
            String containerName = shipments.uploadShipments(minDate);
            context.getLogger().info(String.format("Uploaded shipment data to blob storage container %s", containerName));
        } catch (Exception ex) {
            context.getLogger().severe("Failed to upload shipment data.  Exception: " + ex);
        } */

        return;
    }
}
