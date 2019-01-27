package com.ilku1297.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilku1297.objects.User;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@Data
public class DBObj {
    @JsonProperty
    private List<User> items = new ArrayList<User>();

    public DBObj(List<User> items) {
        this.items = items;
    }

    public List<User> getItems() {
        return items;

    }

    public void setItems(List<User> items) {
        this.items = items;
    }

    public void addItem(User user) {
        items.add(user);
    }
}
