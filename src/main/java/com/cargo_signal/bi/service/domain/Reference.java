package com.cargo_signal.bi.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reference {
  private String referenceId;
  private String type;
  private String value;
  private String parentId;

  public String getReferenceId() {
    return referenceId;
  }

  public String getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  public String getParentId() {
    return parentId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Reference reference = (Reference) o;

    return new EqualsBuilder()
        .append(referenceId, reference.referenceId)
        .append(type, reference.type)
        .append(value, reference.value)
        .append(parentId, reference.parentId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(referenceId)
        .append(type)
        .append(value)
        .append(parentId)
        .toHashCode();
  }
}
