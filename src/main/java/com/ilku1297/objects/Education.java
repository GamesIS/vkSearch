package com.ilku1297.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@Deprecated
public class Education {
    @JsonProperty
    private Integer university; //идентификатор университета
    @JsonProperty
    private String university_name; //название университета
    @JsonProperty
    private Integer faculty; //идентификатор факультета
    @JsonProperty
    private String faculty_name; //название факультета
    @JsonProperty
    private Integer graduation; //год окончания
}
