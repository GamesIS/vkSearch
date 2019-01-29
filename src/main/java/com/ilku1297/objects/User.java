package com.ilku1297.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.scene.image.Image;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public static String fields = "&fields=photo,last_seen,sex,hometown,has_photo,friend_status,followers_count,education,country,common_count,blacklisted_by_me,bdate,online,relation,relationPartner,schools,universities,connections,site,photo_max_orig";

    @JsonProperty("id")
    private BigInteger ID;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("photo")
    private String photo;
    private BufferedImage photoBufferedImage;

    /**
     * 1 — женщина;
     * 2 — мужчина;
     * 0 — любой (по умолчанию).
     */
    @JsonProperty("sex")
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
    @JsonSetter("education")
    private Education education;//Высшее образование //TODO
    @JsonSetter("country")
    private Country country;//Высшее образование
    @JsonSetter("city")
    private City city; //TODO
    @JsonProperty("common_count")
    private Integer commonCount;//Количество общих друзей
    @JsonProperty("blacklisted_by_me")
    private Integer blacklistedByMe;//В ЧС ли я
    @JsonProperty("bdate")
    private String bdate;//Дата рождения
    @JsonProperty("online")
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
    @JsonProperty("relation")
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
    @JsonProperty("schools")//todo
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
    @JsonProperty("universities")//todo
    private List<Universities> universities; //Массив университетов
    /**
     * возвращает данные об указанных в профиле сервисах пользователя, таких как: skype, facebook, twitter, livejournal, instagram. Для каждого сервиса возвращается отдельное поле с типом string, содержащее никнейм пользователя. Например, "instagram": "username".
     */
    @JsonProperty("connections")//todo
    private String connections;

    @JsonProperty("site")
    private String site;

    /***
     * url фотографии максимального размера. Может быть возвращена фотография, имеющая ширину как 400, так и 200 пикселей. В случае отсутствия у пользователя фотографии возвращается https://vk.com/images/camera_400.png.
     */
    @JsonProperty("photo_max_orig")
    private String photoMaxOrig;

    private BufferedImage photoMaxBufferedImage;


    public User() {
    }
}
