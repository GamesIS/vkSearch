package com.ilku1297.objects;

public class Education {
    private Integer university; //идентификатор университета
    private String university_name; //название университета
    private Integer faculty; //идентификатор факультета
    private String faculty_name; //название факультета
    private Integer graduation; //год окончания

    public Integer getUniversity() {
        return university;
    }

    public void setUniversity(Integer university) {
        this.university = university;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public Integer getFaculty() {
        return faculty;
    }

    public void setFaculty(Integer faculty) {
        this.faculty = faculty;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }

    @Override
    public String toString() {
        return "Education{" +
                "university=" + university +
                ", university_name='" + university_name + '\'' +
                ", faculty=" + faculty +
                ", faculty_name='" + faculty_name + '\'' +
                ", graduation=" + graduation +
                '}';
    }
}
