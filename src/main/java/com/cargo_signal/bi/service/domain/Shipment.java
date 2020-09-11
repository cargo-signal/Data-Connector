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
//  private List<String> serviceProvider; // FIXME looks wrong in payload, array with one null
  private String status;
  private String scheduledPickupTime;
  private Location pickupLocation;
  private String scheduledDeliveryTime;
  private Location deliveryLocation;
  private Location lastKnownLocation;
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

  public String getStatus() {
    return status;
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
        .append(status, shipment.status)
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
        .append(status)
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
