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

import com.cargo_signal.bi.service.ShipmentsService;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
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
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Cargo Signal BI 'shipments' processed a request.");

        // Parse query parameter
        final String minDate = request.getQueryParameters().get("minDate");

        if (minDate == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("'minDate' parameter required in the query string.").build();
        }

        try {
            ShipmentsService shipmentsService = new ShipmentsService(context);
            shipmentsService.uploadShipments(minDate);
            return request.createResponseBuilder(HttpStatus.OK).body("Uploaded shipment data to blob storage containers.").build();
        } catch (Exception ex) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload shipment data.  Exception: " + ex).build();
        }        
    }

    @FunctionName("shipmentsTimer")
    public void getShipmentsTimer(
            @TimerTrigger(
            name = "shipmentsTimer",
            schedule = "0 0 6 * * *")  // Modify this CRON expression to set the schedule; currently every day at 6:00 am Pacific
            String timerInfo,
            final ExecutionContext context) {

        // CRON examples: https://docs.microsoft.com/en-us/azure/azure-functions/functions-bindings-timer?tabs=csharp#ncrontab-expressions

        // WEBSITE_TIME_ZONE app setting allows to use a local time for the CRON expression.  The name will vary between Linux and Windows host.

        context.getLogger().info("Cargo Signal BI 'shipments' processing a timer request.");

        // Modify minDate and cron schedule for your needs
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        // starting from 1 day ago
        String minDate = formatter.format(new Date(today.getTime() - Duration.ofDays(1).toMillis()));
        
        context.getLogger().info("Retrieving shipments for one day starting at " + minDate);

        try
        {
            ShipmentsService shipments = new ShipmentsService(context);
            shipments.uploadShipments(minDate);
            context.getLogger().info("Uploaded shipment data to blob storage containers.");
        } catch (Exception ex) {
            context.getLogger().severe("Failed to upload shipment data.  Exception: " + ex);
        }

        return;
    }
}
