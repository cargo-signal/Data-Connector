package com.cargo_signal.bi.service.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class TelemetryPageableContent {

        private List<DeviceTelemetry> content;
        private Double pageNumber;
        private Double pageSize;
        private Double numberOfElements;
        private Double totalElements;
        private Boolean first;
        private Boolean last;
        private Double totalPages;

        public List<DeviceTelemetry> getContent() {
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
                    .append(content, ((TelemetryPageableContent) o).getContent())
                    .append(pageNumber, ((TelemetryPageableContent) o).getPageNumber())
                    .append(pageSize, ((TelemetryPageableContent) o).getPageSize())
                    .append(numberOfElements, ((TelemetryPageableContent) o).getNumberOfElements())
                    .append(totalElements, ((TelemetryPageableContent) o).getTotalElements())
                    .append(first,((TelemetryPageableContent) o).getFirst())
                    .append(last,((TelemetryPageableContent) o).getLast())
                    .append(totalPages, ((TelemetryPageableContent) o).getTotalPages())
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
