package com.ilku1297.objects;

public class Area {
    protected Integer ID;
    protected String title;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Area{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                '}';
    }
}
