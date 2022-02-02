package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

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

    public List<Shipment> getContent() {
        return content;
    }

    public Double getPageNumber() {
        return pageNumber;
    }

    public Double getPageSize() {
        return pageSize;
    }

    public Double getNumberOfElements() {
        return numberOfElements;
    }

    public Double getTotalElements() {
        return totalElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public Boolean getLast() {
        return last;
    }

    public Double getTotalPages() {
        return totalPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        return new EqualsBuilder()
                .append(content, ((ShipmentPageableContent) o).getContent())
                .append(pageNumber, ((ShipmentPageableContent) o).getPageNumber())
                .append(pageSize, ((ShipmentPageableContent) o).getPageSize())
                .append(numberOfElements, ((ShipmentPageableContent) o).getNumberOfElements())
                .append(totalElements, ((ShipmentPageableContent) o).getTotalElements())
                .append(first,((ShipmentPageableContent) o).getFirst())
                .append(last,((ShipmentPageableContent) o).getLast())
                .append(totalPages, ((ShipmentPageableContent) o).getTotalPages())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(content)
                .append(pageNumber)
                .append(pageSize)
                .append(numberOfElements)
                .append(totalElements)
                .append(first)
                .append(last)
                .append(totalPages)
                .toHashCode();
    }
}
