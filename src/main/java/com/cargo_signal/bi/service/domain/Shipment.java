package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipment {
    private String customerId;
    private String shipperId;
    private String serviceProviderId;
    private String consigneeId;
    private String customerName;
    private String origin;
    private String destination;
    private String mode;
    private String status;
    private String serviceType;
    private String scheduledPickupDateTime;
    private Location pickupLocation;
    private String scheduledDeliveryDateTime;
    private Location deliveryLocation;
    private Location lastKnownLocation;
    private List<Reference> references;
    private String shipmentCompletedDateTime;
    private String trackingNumber;
}
