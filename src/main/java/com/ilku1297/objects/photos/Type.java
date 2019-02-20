package com.ilku1297.objects.photos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Type {
    @JsonProperty
    private String type;

    @JsonProperty
    private String url;

    @JsonProperty
    private Integer width;

    @JsonProperty
    private Integer height;
}
