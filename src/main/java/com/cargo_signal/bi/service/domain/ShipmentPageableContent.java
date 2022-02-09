package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentPageableContent {
    private List<Shipment> content;
    private Double pageNumber;
    private Double pageSize;
    private Double numberOfElements;
    private Double totalElements;
    private Boolean first;
    private Boolean last;
    private Double totalPages;
}
