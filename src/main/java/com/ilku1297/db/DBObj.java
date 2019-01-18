package com.ilku1297.db;

import com.ilku1297.objects.User;

import java.util.ArrayList;
import java.util.List;

public class DBObj {
    private List<User> items = new ArrayList<User>();

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
