package com.ilku1297.objects.photos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilku1297.objects.photos.Photo;
import lombok.Data;

import java.util.List;

@Data
@Deprecated
public class PhotoList {
    @JsonProperty("items")
    private List<Photo> photos;
}
