package com.ilku1297.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Area {
    @JsonProperty
    protected Integer ID;
    @JsonProperty
    protected String title;
}
