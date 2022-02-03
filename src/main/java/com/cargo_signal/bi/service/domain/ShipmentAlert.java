package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentAlert {
    private String alertItemIdentifier;
    private String shipmentIdentifier;
    private String deviceId;
    private String deviceNumber;
    private String alertStatus;
    private String severity;
    private String trackingNumber;
    private String triggerValue;
    private String triggerCriteria;
    private String alertName;
    private String triggerDatetime;
    private String address;
    private String waypointType;
    private String geofenceName;
    private String updatedDateTime;
    private String sourceType;
    private Location geolocation;
    private String resolvedDateTime;
    private String sourceIdentifier;
    private String pointOfInterestName;

    public String getAlertItemIdentifier() {
        return alertItemIdentifier;
    }

    public String getShipmentIdentifier() {
        return shipmentIdentifier;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public String getAlertStatus() {
        return alertStatus;
    }

    public String getSeverity() {
        return severity;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getTriggerValue() {
        return triggerValue;
    }

    public String getTriggerCriteria() {
        return triggerCriteria;
    }

    public String getAlertName() {
        return alertName;
    }

    public String getTriggerDatetime() {
        return triggerDatetime;
    }

    public String getAddress() {
        return address;
    }

    public String getWaypointType() {
        return waypointType;
    }

    public String getGeofenceName() {
        return geofenceName;
    }

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public String getSourceType() {
        return sourceType;
    }

    public Location getGeolocation() {
        return geolocation;
    }

    public String getResolvedDateTime() {
        return resolvedDateTime;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public String getPointOfInterestName() {
        return pointOfInterestName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ShipmentAlert that = (ShipmentAlert) o;

        return new EqualsBuilder()
                .append(getAlertItemIdentifier(), that.getAlertItemIdentifier())
                .append(getShipmentIdentifier(), that.getShipmentIdentifier())
                .append(getDeviceId(), that.getDeviceId())
                .append(getDeviceNumber(), that.getDeviceNumber())
                .append(getAlertStatus(), that.getAlertStatus())
                .append(getSeverity(), that.getSeverity())
                .append(getTrackingNumber(), that.getTrackingNumber())
                .append(getTriggerValue(), that.getTriggerValue())
                .append(getTriggerCriteria(), that.getTriggerCriteria())
                .append(getAlertName(), that.getAlertName())
                .append(getTriggerDatetime(), that.getTriggerDatetime())
                .append(getAddress(), that.getAddress())
                .append(getWaypointType(), that.getWaypointType())
                .append(getGeofenceName(),that.getGeofenceName())
                .append(getUpdatedDateTime(), that.getUpdatedDateTime())
                .append(getSourceType(), that.getSourceType())
                .append(getGeolocation(), that.getGeolocation())
                .append(getResolvedDateTime(), that.getResolvedDateTime())
                .append(getSourceIdentifier(), that.getSourceIdentifier())
                .append(getPointOfInterestName(), that.getPointOfInterestName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getAlertItemIdentifier())
                .append(getShipmentIdentifier())
                .append(getDeviceId())
                .append(getDeviceNumber())
                .append(getAlertStatus())
                .append(getSeverity())
                .append(getTrackingNumber())
                .append(getTriggerValue())
                .append(getTriggerCriteria())
                .append(getAlertName())
                .append(getTriggerDatetime())
                .append(getAddress())
                .append(getWaypointType())
                .append(getGeofenceName())
                .append(getUpdatedDateTime())
                .append(getSourceType())
                .append(getGeolocation())
                .append(getResolvedDateTime())
                .append(getSourceIdentifier())
                .append(getPointOfInterestName())
                .toHashCode();
    }

}
