package com.ilku1297.objects;
import java.util.ArrayList;
import java.util.List;

public class UserSearchResponse{
    private Integer count;
    private List<User> items = new ArrayList<User>();

    public UserSearchResponse() {
    }

    public List<User> getItems() {
        return this.items;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void addItem(User user){
        items.add(user);
    }
}