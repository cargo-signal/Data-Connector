package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentAlert {
  private String alertItemId;
  private String shipmentId;
  private String deviceId;
  private String deviceNumber;
  private String status;
  private String severity;
  private String shipmentReference;
  private String triggerValue;
  private String triggerCriteria;
  private String name;
  private String triggerDatetime;

  public String getAlertItemId() {
    return alertItemId;
  }

  public String getShipmentId() {
    return shipmentId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public String getDeviceNumber() {
    return deviceNumber;
  }

  public String getStatus() {
    return status;
  }

  public String getSeverity() {
    return severity;
  }

  public String getShipmentReference() {
    return shipmentReference;
  }

  public String getTriggerValue() {
    return triggerValue;
  }

  public String getTriggerCriteria() {
    return triggerCriteria;
  }

  public String getName() {
    return name;
  }

  public String getTriggerDatetime() {
    return triggerDatetime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    ShipmentAlert that = (ShipmentAlert) o;

    return new EqualsBuilder()
        .append(alertItemId, that.alertItemId)
        .append(shipmentId, that.shipmentId)
        .append(deviceId, that.deviceId)
        .append(deviceNumber, that.deviceNumber)
        .append(status, that.status)
        .append(severity, that.severity)
        .append(shipmentReference, that.shipmentReference)
        .append(triggerValue, that.triggerValue)
        .append(triggerCriteria, that.triggerCriteria)
        .append(name, that.name)
        .append(triggerDatetime, that.triggerDatetime)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(alertItemId)
        .append(shipmentId)
        .append(deviceId)
        .append(deviceNumber)
        .append(status)
        .append(severity)
        .append(shipmentReference)
        .append(triggerValue)
        .append(triggerCriteria)
        .append(name)
        .append(triggerDatetime)
        .toHashCode();
  }
}
