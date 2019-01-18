package com.ilku1297.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
@Data
public class Schools {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private Integer country;
    @JsonProperty
    private Integer city;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer year_from;
    @JsonProperty
    private Integer year_to;
    @JsonProperty
    private Integer year_graduated;
    @JsonProperty("class")
    private String class_;
    @JsonProperty
    private String speciality;
    @JsonProperty
    private Integer type;
    @JsonProperty
    private String type_string;
}
