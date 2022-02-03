package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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


    public String getDeviceId() {
        return deviceId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public String getSensorTimestamp() {
        return sensorTimestamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getBatteryPercentage() {
        return batteryPercentage;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getMetersAccuracy() {
        return metersAccuracy;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getShock() {
        return shock;
    }

    public Double getTilt() {
        return tilt;
    }

    public Double getLight() {
        return light;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getFixType() {
        return fixType;
    }

    public String getSource() {
        return source;
    }

    public String getSourceType() {
        return sourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DeviceTelemetry that = (DeviceTelemetry) o;

        return new EqualsBuilder()
                .append(deviceId, that.deviceId)
                .append(trackingNumber, that.trackingNumber)
                .append(deviceNumber, that.deviceNumber)
                .append(sensorTimestamp, that.sensorTimestamp)
                .append(temperature, that.temperature)
                .append(batteryPercentage, that.batteryPercentage)
                .append(humidity, that.humidity)
                .append(metersAccuracy, that.metersAccuracy)
                .append(pressure, that.pressure)
                .append(shock, that.shock)
                .append(tilt, that.tilt)
                .append(light, that.light)
                .append(latitude, that.latitude)
                .append(longitude, that.longitude)
                .append(address, that.address)
                .append(customerId, that.customerId)
                .append(customerName, that.customerName)
                .append(fixType, that.fixType)
                .append(source, that.source)
                .append(sourceType, that.sourceType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(deviceId)
                .append(trackingNumber)
                .append(deviceNumber)
                .append(sensorTimestamp)
                .append(temperature)
                .append(batteryPercentage)
                .append(humidity)
                .append(metersAccuracy)
                .append(pressure)
                .append(shock)
                .append(tilt)
                .append(light)
                .append(latitude)
                .append(longitude)
                .append(address)
                .append(customerId)
                .append(customerName)
                .append(fixType)
                .append(source)
                .append(sourceType)
                .toHashCode();
    }

}
