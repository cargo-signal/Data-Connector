package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Authorization {
    private String access_token;
    private String scope;
    private Double expires_in;
    private String token_type;


    public String getAccess_token() {
        return access_token;
    }

    public String getScope() {
        return scope;
    }

    public Double getExpires_in() {
        return expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        return new EqualsBuilder()
                .append(scope, ((Authorization) o).getScope())
                .append(expires_in, ((Authorization) o).getExpires_in())
                .append(access_token, ((Authorization) o).getAccess_token())
                .append(token_type, ((Authorization) o).getToken_type())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(scope)
                .append(expires_in)
                .append(access_token)
                .append(token_type)
                .toHashCode();
    }

}
