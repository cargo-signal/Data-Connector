package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchShipmentsPayload {

    private List<Status> status;
    private String deliveryDateTimeFrom;
    private String deliveryDateTimeTo;
    private String pickupDateTimeFrom;
    private String pickupDateTimeTo;
    private String completedDateTimeFrom;
    private String completedDateTimeTo;
    private String origin;
    private String destination;
    private List<ServiceType> serviceType;
    private List<Mode> mode;

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public String getDeliveryDateTimeFrom() {
        return deliveryDateTimeFrom;
    }

    public void setDeliveryDateTimeFrom(String deliveryDateTimeFrom) {
        this.deliveryDateTimeFrom = deliveryDateTimeFrom;
    }

    public String getDeliveryDateTimeTo() {
        return deliveryDateTimeTo;
    }

    public void setDeliveryDateTimeTo(String deliveryDateTimeTo) {
        this.deliveryDateTimeTo = deliveryDateTimeTo;
    }

    public String getPickupDateTimeFrom() {
        return pickupDateTimeFrom;
    }

    public void setPickupDateTimeFrom(String pickupDateTimeFrom) {
        this.pickupDateTimeFrom = pickupDateTimeFrom;
    }

    public String getPickupDateTimeTo() {
        return pickupDateTimeTo;
    }

    public void setPickupDateTimeTo(String pickupDateTimeTo) {
        this.pickupDateTimeTo = pickupDateTimeTo;
    }

    public String getCompletedDateTimeFrom() {
        return completedDateTimeFrom;
    }

    public void setCompletedDateTimeFrom(String completedDateTimeFrom) {
        this.completedDateTimeFrom = completedDateTimeFrom;
    }

    public String getCompletedDateTimeTo() {
        return completedDateTimeTo;
    }

    public void setCompletedDateTimeTo(String completedDateTimeTo) {
        this.completedDateTimeTo = completedDateTimeTo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<ServiceType> getServiceType() {
        return serviceType;
    }

    public void setServiceType(List<ServiceType> serviceType) {
        this.serviceType = serviceType;
    }

    public List<Mode> getMode() {
        return mode;
    }

    public void setMode(List<Mode> mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        return new EqualsBuilder()
                .append(getStatus(), ((SearchShipmentsPayload) o).getStatus())
                .append(getDeliveryDateTimeFrom(), ((SearchShipmentsPayload) o).getDeliveryDateTimeFrom())
                .append(getDeliveryDateTimeTo(), ((SearchShipmentsPayload) o).getDeliveryDateTimeTo())
                .append(getPickupDateTimeFrom(), ((SearchShipmentsPayload) o).getPickupDateTimeFrom())
                .append(getPickupDateTimeTo(), ((SearchShipmentsPayload) o).getPickupDateTimeTo())
                .append(getCompletedDateTimeFrom(), ((SearchShipmentsPayload) o).getCompletedDateTimeFrom())
                .append(getCompletedDateTimeTo(), ((SearchShipmentsPayload) o).getCompletedDateTimeTo())
                .append(getOrigin(), ((SearchShipmentsPayload) o).getOrigin())
                .append(getDestination(), ((SearchShipmentsPayload) o).getDestination())
                .append(getMode(), ((SearchShipmentsPayload) o).getMode())
                .append(getStatus(), ((SearchShipmentsPayload) o).getStatus())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getStatus())
                .append(getDeliveryDateTimeFrom())
                .append(getDeliveryDateTimeTo())
                .append(getPickupDateTimeFrom())
                .append(getPickupDateTimeTo())
                .append(getCompletedDateTimeFrom())
                .append(getCompletedDateTimeTo())
                .append(getOrigin())
                .append(getDestination())
                .append(getMode())
                .append(getStatus())
                .toHashCode();
    }

}
