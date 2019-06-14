package com.ilku1297.db.hibernate;

import com.ilku1297.objects.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "girls")
@Data
public class Girl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Date lastUpdateDate;
    private Integer friendStatus;
    private Integer nativeCity;
    private Integer blackListedByMe;
    //private Integer universities;//TODO Потом добавить таблицу/Enum универов
    //private Integer schools;//TODO Потом добавить таблицу/Enum школ

    public Girl() {
    }

    public Girl(User user, Date date) {
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
        this.lastUpdateDate = date;
        this.friendStatus = user.getFriendStatus();
        this.nativeCity = user.getHomeTown();
        this.blackListedByMe = user.getBlacklistedByMe();

        //TODO
        //this.blackListedByMe = user.getUniversities();
        //this.blackListedByMe = user.getSchools();
    }

}