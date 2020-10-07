package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
  private String formattedAddress;
  private Geolocation geolocation;

  public String getFormattedAddress() {
    return formattedAddress;
  }

  public Geolocation getGeolocation() {
    return geolocation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Location location = (Location) o;

    return new EqualsBuilder()
        .append(formattedAddress, location.formattedAddress)
        .append(geolocation, location.geolocation)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(formattedAddress)
        .append(geolocation)
        .toHashCode();
  }
}
