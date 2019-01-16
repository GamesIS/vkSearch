package com.ilku1297.objects;

import java.math.BigInteger;
import java.util.List;

public class User {
    public static String fields = "&fields=photo,last_seen";

    private BigInteger ID;
    private String firstName;
    private String lastName;
    private String photo;

    /**
     1 — женщина;
     2 — мужчина;
     0 — любой (по умолчанию).
     */
    private Integer sex;

    private Integer lastSeen;//Последний заход Unix time
    private Integer homeTown;//Родной город
    private Integer hasPhoto;
    /**
     attr friendStatus
     0 — не является другом,
     1 — отправлена заявка/подписка пользователю,
     2 — имеется входящая заявка/подписка от пользователя,
     3 — является другом.
     */
    private Integer friendStatus;
    private Integer followersCount;//Количество подписчиков
    private Education education;//Высшее образование
    private Country country;//Высшее образование
    private City city;
    private Integer commonCount;//Количество общих друзей
    private Integer blacklistedByMe;//В ЧС ли я
    private String bdate;//Дата рождения
    private Integer online;

    /**
     * семейное положение. Возможные значения:
     1 — не женат/не замужем;
     2 — есть друг/есть подруга;
     3 — помолвлен/помолвлена;
     4 — женат/замужем;
     5 — всё сложно;
     6 — в активном поиске;
     7 — влюблён/влюблена;
     8 — в гражданском браке;
     0 — не указано.

     Если в семейном положении указан другой пользователь, дополнительно возвращается объект relation_partner, содержащий id и имя этого человека.
     */
    private Integer relation;
    private Integer relationPartner;
    /**
     * список школ, в которых учился пользователь. Массив объектов, описывающих школы. Каждый объект содержит следующие поля:
     id (integer) — идентификатор школы;
     country (integer) — идентификатор страны, в которой расположена школа;
     city (integer) — идентификатор города, в котором расположена школа;
     name (string) — наименование школы
     year_from (integer) — год начала обучения;
     year_to (integer) — год окончания обучения;
     year_graduated (integer) — год выпуска;
     class (string) — буква класса;
     speciality (string) — специализация;
     type (integer) — идентификатор типа;
     type_str (string) — название типа. Возможные значения для пар type-typeStr:
     0 — "школа";
     1 — "гимназия";
     2 —"лицей";
     3 — "школа-интернат";
     4 — "школа вечерняя";
     5 — "школа музыкальная";
     6 — "школа спортивная";
     7 — "школа художественная";
     8 — "колледж";
     9 — "профессиональный лицей";
     10 — "техникум";
     11 — "ПТУ";
     12 — "училище";
     13 — "школа искусств".
     */
    private List<Schools> schools; //Массив школ
    /**
     * список вузов, в которых учился пользователь. Массив объектов, описывающих университеты. Каждый объект содержит следующие поля:
     id (integer)— идентификатор университета;
     country (integer) — идентификатор страны, в которой расположен университет;
     city (integer) — идентификатор города, в котором расположен университет;
     name (string) — наименование университета;
     faculty (integer) — идентификатор факультета;
     faculty_name (string) — наименование факультета;
     chair (integer) — идентификатор кафедры;
     chair_name (string) — наименование кафедры;
     graduation (integer) — год окончания обучения;
     education_form (string) — форма обучения;
     education_status (string) — статус (например, «Выпускник (специалист)»).
     */
    private List<Universities> universities; //Массив университетов
    /**
     * 	возвращает данные об указанных в профиле сервисах пользователя, таких как: skype, facebook, twitter, livejournal, instagram. Для каждого сервиса возвращается отдельное поле с типом string, содержащее никнейм пользователя. Например, "instagram": "username".*/
    private String connections;

    private String site;

    /***
     * url фотографии максимального размера. Может быть возвращена фотография, имеющая ширину как 400, так и 200 пикселей. В случае отсутствия у пользователя фотографии возвращается https://vk.com/images/camera_400.png.
     */
    private Integer photoMaxOrig;



    public User() {
    }
    public BigInteger getID() {
        return ID;
    }

    public void setID(BigInteger ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public static String getFields() {
        return fields;
    }

    public static void setFields(String fields) {
        User.fields = fields;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public Integer getRelationPartner() {
        return relationPartner;
    }

    public void setRelationPartner(Integer relationPartner) {
        this.relationPartner = relationPartner;
    }

    public List<Schools> getSchools() {
        return schools;
    }

    public void setSchools(List<Schools> schools) {
        this.schools = schools;
    }

    public List<Universities> getUniversities() {
        return universities;
    }

    public void setUniversities(List<Universities> universities) {
        this.universities = universities;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getPhotoMaxOrig() {
        return photoMaxOrig;
    }

    public void setPhotoMaxOrig(Integer photoMaxOrig) {
        this.photoMaxOrig = photoMaxOrig;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Integer lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Integer getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(Integer homeTown) {
        this.homeTown = homeTown;
    }

    public Integer getHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(Integer hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public Integer getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        this.friendStatus = friendStatus;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getCommonCount() {
        return commonCount;
    }

    public void setCommonCount(Integer commonCount) {
        this.commonCount = commonCount;
    }

    public Integer getBlacklistedByMe() {
        return blacklistedByMe;
    }

    public void setBlacklistedByMe(Integer blacklistedByMe) {
        this.blacklistedByMe = blacklistedByMe;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photo='" + photo + '\'' +
                ", sex=" + sex +
                ", lastSeen=" + lastSeen +
                ", homeTown=" + homeTown +
                ", hasPhoto=" + hasPhoto +
                ", friendStatus=" + friendStatus +
                ", followersCount=" + followersCount +
                ", education=" + education +
                ", country=" + country +
                ", city=" + city +
                ", commonCount=" + commonCount +
                ", blacklistedByMe=" + blacklistedByMe +
                ", bdate='" + bdate + '\'' +
                ", online=" + online +
                ", relation=" + relation +
                ", relationPartner=" + relationPartner +
                ", schools=" + schools +
                ", universities=" + universities +
                ", connections='" + connections + '\'' +
                ", site='" + site + '\'' +
                ", photoMaxOrig=" + photoMaxOrig +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!ID.equals(user.ID)) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        if (lastSeen != null ? !lastSeen.equals(user.lastSeen) : user.lastSeen != null) return false;
        if (homeTown != null ? !homeTown.equals(user.homeTown) : user.homeTown != null) return false;
        if (hasPhoto != null ? !hasPhoto.equals(user.hasPhoto) : user.hasPhoto != null) return false;
        if (friendStatus != null ? !friendStatus.equals(user.friendStatus) : user.friendStatus != null) return false;
        if (followersCount != null ? !followersCount.equals(user.followersCount) : user.followersCount != null)
            return false;
        if (education != null ? !education.equals(user.education) : user.education != null) return false;
        if (country != null ? !country.equals(user.country) : user.country != null) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (commonCount != null ? !commonCount.equals(user.commonCount) : user.commonCount != null) return false;
        if (blacklistedByMe != null ? !blacklistedByMe.equals(user.blacklistedByMe) : user.blacklistedByMe != null)
            return false;
        if (bdate != null ? !bdate.equals(user.bdate) : user.bdate != null) return false;
        if (online != null ? !online.equals(user.online) : user.online != null) return false;
        if (relation != null ? !relation.equals(user.relation) : user.relation != null) return false;
        if (relationPartner != null ? !relationPartner.equals(user.relationPartner) : user.relationPartner != null)
            return false;
        if (schools != null ? !schools.equals(user.schools) : user.schools != null) return false;
        if (universities != null ? !universities.equals(user.universities) : user.universities != null) return false;
        if (connections != null ? !connections.equals(user.connections) : user.connections != null) return false;
        if (site != null ? !site.equals(user.site) : user.site != null) return false;
        return photoMaxOrig != null ? photoMaxOrig.equals(user.photoMaxOrig) : user.photoMaxOrig == null;
    }

    @Override
    public int hashCode() {
        int result = ID.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (lastSeen != null ? lastSeen.hashCode() : 0);
        result = 31 * result + (homeTown != null ? homeTown.hashCode() : 0);
        result = 31 * result + (hasPhoto != null ? hasPhoto.hashCode() : 0);
        result = 31 * result + (friendStatus != null ? friendStatus.hashCode() : 0);
        result = 31 * result + (followersCount != null ? followersCount.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (commonCount != null ? commonCount.hashCode() : 0);
        result = 31 * result + (blacklistedByMe != null ? blacklistedByMe.hashCode() : 0);
        result = 31 * result + (bdate != null ? bdate.hashCode() : 0);
        result = 31 * result + (online != null ? online.hashCode() : 0);
        result = 31 * result + (relation != null ? relation.hashCode() : 0);
        result = 31 * result + (relationPartner != null ? relationPartner.hashCode() : 0);
        result = 31 * result + (schools != null ? schools.hashCode() : 0);
        result = 31 * result + (universities != null ? universities.hashCode() : 0);
        result = 31 * result + (connections != null ? connections.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (photoMaxOrig != null ? photoMaxOrig.hashCode() : 0);
        return result;
    }
}
