package com.ilku1297.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilku1297.VKRestSender;
import com.ilku1297.db.hibernate.photo.Photo;
import com.ilku1297.objects.photos.PhotoPOJO;
import com.ilku1297.proxy.Constants;
import lombok.Data;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;
import java.util.List;

import static com.ilku1297.VKRestSender.ADDRESS;
import static com.ilku1297.VKRestSender.VER_ACC_TOK;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonIgnore
    private static Logger logger = Logger.getLogger(User.class);

    @JsonIgnore
    public static String fields = "&fields=photo,last_seen,sex,hometown,has_photo,friend_status,followers_count,education,country,common_count,blacklisted_by_me,bdate,online,relation,relationPartner,schools,universities,connections,site,photo_max_orig";
    //@JsonIgnore
    //public static String fields = "&fields=photo,last_seen,sex";

    @JsonProperty("id")
    private Integer ID;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty
    private String photo;
    @JsonIgnore
    private BufferedImage photoBufferedImage;

    /**
     * 1 — женщина;
     * 2 — мужчина;
     * 0 — любой (по умолчанию).
     */
    @JsonProperty
    private Integer sex;

    @JsonProperty("last_seen")
    private LastSeen lastSeen;//Последний заход Unix time
    @JsonProperty("hometown")
    private Integer homeTown;//Родной город //TODO
    @JsonProperty("has_photo")
    private Integer hasPhoto;
    /**
     * attr friendStatus
     * 0 — не является другом,
     * 1 — отправлена заявка/подписка пользователю,
     * 2 — имеется входящая заявка/подписка от пользователя,
     * 3 — является другом.
     */
    @JsonProperty("friend_status")
    private Integer friendStatus;
    @JsonProperty("followers_count")
    private Integer followersCount;//Количество подписчиков
//    @JsonSetter
//    private Education education;//Высшее образование //TODO
    @JsonSetter
    private Country country;//Высшее образование
    @JsonSetter
    private City city; //TODO
    @JsonProperty("common_count")
    private Integer commonCount;//Количество общих друзей
    @JsonProperty("blacklisted_by_me")
    private Integer blacklistedByMe;//В ЧС ли я
    @JsonProperty
    private String bdate;//Дата рождения
    @JsonProperty
    private Integer online;

    /**
     * семейное положение. Возможные значения:
     * 1 — не женат/не замужем;
     * 2 — есть друг/есть подруга;
     * 3 — помолвлен/помолвлена;
     * 4 — женат/замужем;
     * 5 — всё сложно;
     * 6 — в активном поиске;
     * 7 — влюблён/влюблена;
     * 8 — в гражданском браке;
     * 0 — не указано.
     * <p>
     * Если в семейном положении указан другой пользователь, дополнительно возвращается объект relation_partner, содержащий id и имя этого человека.
     */
    @JsonProperty
    private Integer relation; //Todo
    @JsonProperty("relation_partner")
    private Integer relationPartner;//Todo
    /**
     * список школ, в которых учился пользователь. Массив объектов, описывающих школы. Каждый объект содержит следующие поля:
     * id (integer) — идентификатор школы;
     * country (integer) — идентификатор страны, в которой расположена школа;
     * city (integer) — идентификатор города, в котором расположена школа;
     * name (string) — наименование школы
     * year_from (integer) — год начала обучения;
     * year_to (integer) — год окончания обучения;
     * year_graduated (integer) — год выпуска;
     * class (string) — буква класса;
     * speciality (string) — специализация;
     * type (integer) — идентификатор типа;
     * type_str (string) — название типа. Возможные значения для пар type-typeStr:
     * 0 — "школа";
     * 1 — "гимназия";
     * 2 —"лицей";
     * 3 — "школа-интернат";
     * 4 — "школа вечерняя";
     * 5 — "школа музыкальная";
     * 6 — "школа спортивная";
     * 7 — "школа художественная";
     * 8 — "колледж";
     * 9 — "профессиональный лицей";
     * 10 — "техникум";
     * 11 — "ПТУ";
     * 12 — "училище";
     * 13 — "школа искусств".
     */
    @JsonProperty
    private List<Schools> schools; //Массив школ
    /**
     * список вузов, в которых учился пользователь. Массив объектов, описывающих университеты. Каждый объект содержит следующие поля:
     * id (integer)— идентификатор университета;
     * country (integer) — идентификатор страны, в которой расположен университет;
     * city (integer) — идентификатор города, в котором расположен университет;
     * name (string) — наименование университета;
     * faculty (integer) — идентификатор факультета;
     * faculty_name (string) — наименование факультета;
     * chair (integer) — идентификатор кафедры;
     * chair_name (string) — наименование кафедры;
     * graduation (integer) — год окончания обучения;
     * education_form (string) — форма обучения;
     * education_status (string) — статус (например, «Выпускник (специалист)»).
     */
    @JsonProperty
    private List<Universities> universities; //Массив университетов

    @JsonProperty
    private Integer university;

    /**
     * возвращает данные об указанных в профиле сервисах пользователя, таких как: skype, facebook, twitter, livejournal, instagram. Для каждого сервиса возвращается отдельное поле с типом string, содержащее никнейм пользователя. Например, "instagram": "username".
     */
    @JsonProperty("connections")//todo
    private String connections;

    @JsonProperty
    private String site;


    /**
     * есть ли у текущего пользователя возможность видеть профиль пользователя при is_closed = true
     * */

    @JsonProperty ("can_access_closed")
    private Boolean canAccessClosed;

    /**
     * включена ли приватность профиля;
     * */
    @JsonProperty ("is_closed")
    private Boolean isClosed;

    /***
     * url фотографии максимального размера. Может быть возвращена фотография, имеющая ширину как 400, так и 200 пикселей. В случае отсутствия у пользователя фотографии возвращается https://vk.com/images/camera_400.png.
     */
    @JsonProperty("photo_max_orig")
    private String photoMaxOrig;

    @JsonProperty
    private String instagram;

    @JsonIgnore
    public String getCustomPhotoMaxOrig() {
        if(photoMaxOrig != null){
            return photoMaxOrig;
        }
        else {
            String url = ADDRESS + "users.get?user_ids=" + ID + "&fields=photo_max_orig"  + VER_ACC_TOK;
            try {
                String response = VKRestSender.sendGet(url, false);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readValue(response, JsonNode.class);
                JsonNode itemsNode = jsonNode.findValue("photo_max_orig");
                if(itemsNode != null){
                    photoMaxOrig = itemsNode.asText();
                    System.out.println(photoMaxOrig);
                    return photoMaxOrig;
                }
            } catch (Exception e) {
                logger.error("Error getPhotoMaxOrig", e);
            }
            hasPhoto = 0;
            return Constants.NO_PHOTO;
        }
    }
    @JsonIgnore
    private Photo mainPhoto;

    public void setMainPhoto(Photo mainPhoto) {
        this.mainPhoto = mainPhoto;
        mainPhoto.setUser(ID);//TODO Переносим логику в Photo, и User в Girl
    }

    public User() {
    }

    @JsonIgnore
    private List<PhotoPOJO> photoList;

    @JsonIgnore
    public List<PhotoPOJO> getPhotoList() {
        return photoList;
    }

    @JsonIgnore
    public void setPhotoList(List<PhotoPOJO> photoList) {
        this.photoList = photoList;
        //this.photoList = new ArrayList<>();
        //this.photoList.add(mainPhoto);
        //this.photoList.addAll(photoList);
    }

    @JsonIgnore
    private Boolean isLoaded = false;

    @JsonIgnore
    private String track_code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return ID.equals(user.ID);
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }


    //TODO Unused property
    @JsonProperty
    private String university_name;
    @JsonProperty
    private Integer faculty;
    @JsonProperty
    private String faculty_name;
    @JsonProperty
    private String graduation;
    @JsonProperty
    private String twitter;
    @JsonProperty
    private String online_app;
    @JsonProperty
    private String online_mobile;
    @JsonProperty
    private String skype;
    @JsonProperty
    private String education_form;
    @JsonProperty
    private String education_status;
    @JsonProperty
    private String facebook;
    @JsonProperty
    private String facebook_name;
}
