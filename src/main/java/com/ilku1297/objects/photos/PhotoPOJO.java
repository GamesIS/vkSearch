package com.ilku1297.objects.photos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilku1297.objects.User;
import com.ilku1297.proxy.Constants;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoPOJO {
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

    public PhotoPOJO() {
    }

    @JsonIgnore
    public Type getMaxSize(){
        int tmpSize = 0;
        Type result = null;
        for (Type type : sizes) {
            int currentSize = type.getWidth() * type.getHeight();
            if (currentSize > tmpSize) {
                result = type;
                tmpSize = currentSize;
            }
        }
        return result;
    }

    @JsonIgnore
    public PhotoPOJO(BufferedImage photo) {
        downloadedMaxImage = bufToFXImage(photo);
    }


    @JsonIgnore
    public Date getDate() {
        return new Date((long) dateUnix * 1000);
    }

    @JsonIgnore
    private WritableImage downloadedMaxImage;

    @JsonIgnore
    public WritableImage getDownloadedMaxImage() {
        return downloadedMaxImage;
    }

    @JsonIgnore
    public void setDownloadedMaxImage(BufferedImage bufferedMaxImage) {
        downloadedMaxImage = bufToFXImage(bufferedMaxImage);
    }

    @JsonIgnore
    public void setDownloadedMaxImage(WritableImage downloadedMaxImage) {
        this.downloadedMaxImage = downloadedMaxImage;
    }

    public static WritableImage bufToFXImage(BufferedImage bufferedImage){
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
