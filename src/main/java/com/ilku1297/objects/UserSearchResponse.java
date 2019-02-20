package com.ilku1297.objects;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class UserSearchResponse {
    private List<User> items = new ArrayList<User>();

    public UserSearchResponse() {
    }

    public List<User> getItems() {
        return this.items;
    }

    public void addItem(User user) {
        items.add(user);
    }
}