package com.ilku1297.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LastSeen {
    private Integer time;
    @JsonProperty
    private Integer platform;
}
