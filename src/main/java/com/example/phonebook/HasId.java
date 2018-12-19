package com.example.phonebook;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface HasId {
    Integer getId();

    void setId(Integer id);

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    default boolean isNew() {
        return getId() == null;
    }
}
