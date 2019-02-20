package com.ilku1297.objects.photos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Likes {
    @JsonProperty
    private Boolean user_likes;

    @JsonProperty
    private Integer count;

}
