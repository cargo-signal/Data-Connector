package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipment {
    private String shipmentNumber;
    private String referenceNumber;
    private String customerId;
    private String shipperId;
    private String serviceProviderId;
    private String customerName;
    private List<String> serviceProvider;
    private String origin;
    private String destination;
    private String mode;
    private String status;
    private String serviceLevel;
    private String scheduledPickupDateTime;
    private Location pickupLocation;
    private String scheduledDeliveryDateTime;
    private Location deliveryLocation;
    private Location lastKnownLocation;
    private Integer activeAlertCount;
    private List<Reference> references;
    private String shipmentCompletedDateTime;
    private String shipmentId;
    private String trackingNumber;

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<String> getServiceProvider() {
        return serviceProvider;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getMode() {
        return mode;
    }

    public String getStatus() {
        return status;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public String getScheduledPickupDateTime() {
        return scheduledPickupDateTime;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public String getScheduledDeliveryDateTime() {
        return scheduledDeliveryDateTime;
    }

    public Location getDeliveryLocation() {
        return deliveryLocation;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public String getShipmentCompletedDateTime() {
        return shipmentCompletedDateTime;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getShipperId() {
        return shipperId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Shipment shipment = (Shipment) o;

        return new EqualsBuilder()
                .append(shipmentNumber, shipment.shipmentNumber)
                .append(referenceNumber, shipment.referenceNumber)
                .append(customerName, shipment.customerName)
                .append(serviceProvider, shipment.serviceProvider)
                .append(origin, shipment.origin)
                .append(destination, shipment.destination)
                .append(mode, shipment.mode)
                .append(status, shipment.status)
                .append(serviceLevel, shipment.serviceLevel)
                .append(scheduledPickupDateTime, shipment.scheduledPickupDateTime)
                .append(pickupLocation, shipment.pickupLocation)
                .append(scheduledDeliveryDateTime, shipment.scheduledDeliveryDateTime)
                .append(getDeliveryLocation(), shipment.getDeliveryLocation())
                .append(lastKnownLocation, shipment.lastKnownLocation)
                .append(references, shipment.references)
                .append(shipmentCompletedDateTime, shipment.shipmentCompletedDateTime)
                .append(shipmentId, shipment.shipmentId)
                .append(trackingNumber, shipment.trackingNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(shipmentNumber)
                .append(referenceNumber)
                .append(customerName)
                .append(serviceProvider)
                .append(origin)
                .append(destination)
                .append(mode)
                .append(status)
                .append(serviceLevel)
                .append(scheduledPickupDateTime)
                .append(pickupLocation)
                .append(scheduledDeliveryDateTime)
                .append(deliveryLocation)
                .append(lastKnownLocation)
                .append(references)
                .append(shipmentCompletedDateTime)
                .append(shipmentId)
                .append(trackingNumber)
                .toHashCode();
    }

}
