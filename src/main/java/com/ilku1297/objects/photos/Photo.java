package com.ilku1297.objects.photos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilku1297.objects.User;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {
    @JsonIgnore
    private BufferedImage maxImage;

    @JsonProperty
    private Integer id;

    @JsonProperty("owner_id")
    private Integer userID;

    @JsonProperty
    private Integer album_id;

    @JsonProperty
    private String text;

    @JsonProperty
    private Integer post_id;

    @JsonProperty("date")
    private Integer dateUnix;

    @JsonProperty
    private Likes likes;

    @JsonProperty("sizes")
    private List<Type> sizes;

    @JsonIgnore
    public Date getDate(){
        return new Date((long) dateUnix*1000);
    }
    @JsonIgnore
    public String getMaxPhoto(){
        if(sizes.size() == 0) return User.NO_PHOTO;
        int tmpSize = 0;
        String tmpUrl = sizes.get(0).getUrl();
        for(Type type :sizes){
            int currentSize = type.getWidth() * type.getHeight();
            if(currentSize > tmpSize){
                tmpSize = currentSize;
                tmpUrl = type.getUrl();
            }
        }
        if (tmpUrl == null){
            return User.NO_PHOTO;
        }
        return tmpUrl;
    }

}
