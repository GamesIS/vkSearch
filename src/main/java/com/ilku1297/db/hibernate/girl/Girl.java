package com.ilku1297.db.hibernate.girl;

import com.ilku1297.db.hibernate.photo.Photo;
import com.ilku1297.objects.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "girls")
@Data
public class Girl {

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String bDate;
    private String photo;
    private Integer hasPhoto;
    private Integer lastSeen;
    private Integer cityID;//TODO Потом добавить таблицу с городами
    private Integer numberMutualFriends;
    private Integer relation;//TODO Потом добавить таблицу/Enum статусов
    private Integer relationPartner;
    private Integer followersCount;
    private Integer sex;
    private Boolean accessClosed;
    private Date finded;
    private Date lastUpdateDate;
    private Integer friendStatus;
    private Integer nativeCity;
    private Integer blackListedByMe;
    private Integer university;//TODO Потом добавить таблицу/Enum универов
    private String instagram;//TODO Потом добавить таблицу/Enum универов
    @OneToMany(targetEntity = Photo.class, cascade = CascadeType.ALL)
    @JoinColumn(name="girl_id")
    private Set<Photo> photoList = new HashSet<>();//TODO Потом добавить таблицу/Enum универов
    //private Integer schools;//TODO Потом добавить таблицу/Enum школ

    @Transient
    User user;

    public Girl() {
    }

    public Girl(User user, Date date) {
        this.user = user;

        this.id = user.getID();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.bDate = user.getBdate();
        this.photo = user.getPhoto();
        this.hasPhoto = user.getHasPhoto();
        this.lastSeen = user.getLastSeen().getTime();
        this.cityID = user.getCity() == null ? null : user.getCity().getID();
        this.relation = user.getRelation();
        this.relationPartner = user.getRelationPartner();
        this.followersCount = user.getFollowersCount();
        this.numberMutualFriends = user.getCommonCount();
        this.sex = user.getSex();
        this.accessClosed = user.getCanAccessClosed();
        this.finded = date;
        this.friendStatus = user.getFriendStatus();
        this.nativeCity = user.getHomeTown();
        this.blackListedByMe = user.getBlacklistedByMe();

        //TODO
        this.university = user.getUniversity();
        this.instagram = user.getInstagram();
        //this.blackListedByMe = user.getSchools();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Girl girl = (Girl) o;

        return id == girl.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}