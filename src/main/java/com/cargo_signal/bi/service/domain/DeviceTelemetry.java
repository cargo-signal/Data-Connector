package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceTelemetry {
  private String deviceId;
  private String shipmentId;
  private String deviceNumber;
  private String sensorTimestamp;
  private Double temperature;
  private Double batteryPercentage;
  private Double humidity;
  private Double hepe;
  private Double pressure;
  private Double tilt;
  private Double light;
  private Double latitude;
  private Double longitude;

  public String getDeviceId() {
    return deviceId;
  }

  public String getShipmentId() {
    return shipmentId;
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

  public Double getHepe() {
    return hepe;
  }

  public Double getPressure() {
    return pressure;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    DeviceTelemetry that = (DeviceTelemetry) o;

    return new EqualsBuilder()
        .append(deviceId, that.deviceId)
        .append(shipmentId, that.shipmentId)
        .append(deviceNumber, that.deviceNumber)
        .append(sensorTimestamp, that.sensorTimestamp)
        .append(temperature, that.temperature)
        .append(batteryPercentage, that.batteryPercentage)
        .append(humidity, that.humidity)
        .append(hepe, that.hepe)
        .append(pressure, that.pressure)
        .append(tilt, that.tilt)
        .append(light, that.light)
        .append(latitude, that.latitude)
        .append(longitude, that.longitude)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(deviceId)
        .append(shipmentId)
        .append(deviceNumber)
        .append(sensorTimestamp)
        .append(temperature)
        .append(batteryPercentage)
        .append(humidity)
        .append(hepe)
        .append(pressure)
        .append(tilt)
        .append(light)
        .append(latitude)
        .append(longitude)
        .toHashCode();
  }
}
