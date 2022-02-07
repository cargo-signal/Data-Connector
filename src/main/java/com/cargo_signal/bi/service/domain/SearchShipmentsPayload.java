package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
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
}
