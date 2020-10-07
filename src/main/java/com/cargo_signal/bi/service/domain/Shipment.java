package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipment {
  private String shipmentNumber;
  private String referenceNumber;
  private String customer;
  private List<String> serviceProvider;
  private String origin;
  private String destination;
  private String mode;
  private String status;
  private String serviceType;
  private String scheduledPickupTime;
  private Location pickupLocation;
  private String scheduledDeliveryTime;
  private Location deliveryLocation;
  private Location lastKnownLocation;
  private Integer activeAlertCount;
  private List<Reference> references;
  private String shipmentCompletedDateTime;
  private String shipmentId;

  public String getShipmentNumber() {
    return shipmentNumber;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

  public String getCustomer() {
    return customer;
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

  public String getServiceType() {
    return serviceType;
  }

  public String getScheduledPickupTime() {
    return scheduledPickupTime;
  }

  public Location getPickupLocation() {
    return pickupLocation;
  }

  public String getScheduledDeliveryTime() {
    return scheduledDeliveryTime;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Shipment shipment = (Shipment) o;

    return new EqualsBuilder()
        .append(shipmentNumber, shipment.shipmentNumber)
        .append(referenceNumber, shipment.referenceNumber)
        .append(customer, shipment.customer)
        .append(serviceProvider, shipment.serviceProvider)
        .append(origin, shipment.origin)
        .append(destination, shipment.destination)
        .append(mode, shipment.mode)
        .append(status, shipment.status)
        .append(serviceType, shipment.serviceType)
        .append(scheduledPickupTime, shipment.scheduledPickupTime)
        .append(pickupLocation, shipment.pickupLocation)
        .append(scheduledDeliveryTime, shipment.scheduledDeliveryTime)
        .append(deliveryLocation, shipment.deliveryLocation)
        .append(lastKnownLocation, shipment.lastKnownLocation)
        .append(references, shipment.references)
        .append(shipmentCompletedDateTime, shipment.shipmentCompletedDateTime)
        .append(shipmentId, shipment.shipmentId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(shipmentNumber)
        .append(referenceNumber)
        .append(customer)
        .append(serviceProvider)
        .append(origin)
        .append(destination)
        .append(mode)
        .append(status)
        .append(serviceType)
        .append(scheduledPickupTime)
        .append(pickupLocation)
        .append(scheduledDeliveryTime)
        .append(deliveryLocation)
        .append(lastKnownLocation)
        .append(references)
        .append(shipmentCompletedDateTime)
        .append(shipmentId)
        .toHashCode();
  }
}
