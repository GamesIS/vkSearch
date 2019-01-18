package com.ilku1297.objects;

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
public class Universities {
    private Integer id;
    private Integer country;
    private Integer city;
    private String name;
    private Integer faculty;
    private String facultyName;
    private Integer chair;
    private String chairName;
    private Integer graduation;
    private String educationForm;
    private String educationStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFaculty() {
        return faculty;
    }

    public void setFaculty(Integer faculty) {
        this.faculty = faculty;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Integer getChair() {
        return chair;
    }

    public void setChair(Integer chair) {
        this.chair = chair;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }

    public String getEducationForm() {
        return educationForm;
    }

    public void setEducationForm(String educationForm) {
        this.educationForm = educationForm;
    }

    public String getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(String educationStatus) {
        this.educationStatus = educationStatus;
    }

    @Override
    public String toString() {
        return "Universities{" +
                "id=" + id +
                ", country=" + country +
                ", city=" + city +
                ", name='" + name + '\'' +
                ", faculty=" + faculty +
                ", facultyName='" + facultyName + '\'' +
                ", chair=" + chair +
                ", chairName='" + chairName + '\'' +
                ", graduation=" + graduation +
                ", educationForm='" + educationForm + '\'' +
                ", educationStatus='" + educationStatus + '\'' +
                '}';
    }
}
