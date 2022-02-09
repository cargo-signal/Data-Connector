package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentAlert {
    private String alertItemIdentifier;
    private String shipmentIdentifier;
    private String alertStatus;
    private String trackingNumber;
    private String triggerValue;
    private String triggerCriteria;
    private String alertName;
    private String triggeredDatetime;
    private String address;
    private String waypointType;
    private String geofenceName;
    private String updatedDateTime;
    private String sourceType;
    private Location geolocation;
    private String resolvedDateTime;
    private String sourceIdentifier;
    private String pointOfInterestName;
    private String customerIdentifier;
}
