package com.ilku1297.db.hibernate.photo;

import com.ilku1297.db.hibernate.girl.Girl;
import com.ilku1297.objects.User;
import com.ilku1297.objects.photos.PhotoPOJO;
import com.ilku1297.objects.photos.Type;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import lombok.Data;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "photo")
@Data
public class Photo {
    @Id
    private Integer id;
    @ManyToOne(targetEntity=Girl.class, cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name="girl_id", insertable = false, updatable = false)
    private Girl user;
    private Integer album_id;
    private String text;
    private Integer post_id;
    private Integer dateUnix;
    private Boolean user_like;
    private Integer count_likes;
    private Integer width;
    private Integer height;
    private Boolean isDownloaded;
    private String url;

    public Photo() {
    }

    public Photo(PhotoPOJO photo, Girl user) {
        this.id = photo.getId();
        this.user = user;
        this.album_id = photo.getAlbum_id();
        this.text = photo.getText();
        this.post_id = photo.getPost_id();
        this.dateUnix = photo.getDateUnix();
        this.user_like = photo.getLikes().getUser_likes();
        this.count_likes = photo.getLikes().getCount();
        Type maxSize = photo.getMaxSize();
        if (maxSize != null) {
            this.width = maxSize.getWidth();
            this.height = maxSize.getHeight();
            this.url = maxSize.getUrl();
        }
    }


    public static Collection<? extends Photo> listPOJOToDO(List<PhotoPOJO> allPhotoPojo, Girl girl) {
        List<Photo> photos = new ArrayList<>();
        System.out.println(allPhotoPojo);
        for(PhotoPOJO photo: allPhotoPojo){
            photos.add(photoPOJOToDO(photo, girl));
        }
        return photos;
    }

    public static Photo photoPOJOToDO(PhotoPOJO photoPOJO, Girl girl){
        return new Photo(photoPOJO, girl);
    }



    public Photo(BufferedImage photo) {
        downloadedMaxImage = bufToFXImage(photo);
    }

    @Transient
    private boolean mainPhoto = false;

    @Transient
    public Date getDate() {
        return new Date((long) dateUnix * 1000);
    }

    @Transient
    private WritableImage downloadedMaxImage;

    @Transient
    public WritableImage getDownloadedMaxImage() {
        return downloadedMaxImage;
    }

    @Transient
    public void setDownloadedMaxImage(BufferedImage bufferedMaxImage) {
        downloadedMaxImage = bufToFXImage(bufferedMaxImage);
    }

    @Transient
    public void setDownloadedMaxImage(WritableImage downloadedMaxImage) {
        this.downloadedMaxImage = downloadedMaxImage;
    }

    public static WritableImage bufToFXImage(BufferedImage bufferedImage){
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return id.equals(photo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}