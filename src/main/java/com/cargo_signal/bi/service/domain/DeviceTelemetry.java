package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceTelemetry {
    private String deviceId;
    private String trackingNumber;
    private String deviceNumber;
    private String sensorTimestamp;
    private Double temperature;
    private Double batteryPercentage;
    private Double humidity;
    private Double metersAccuracy;
    private Double pressure;
    private Double shock;
    private Double tilt;
    private Double light;
    private Double latitude;
    private Double longitude;
    private String address;
    private String customerId;
    private String customerName;
    private String fixType;
    private String source;
    private String sourceType;
}
