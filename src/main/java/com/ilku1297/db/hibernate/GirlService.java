package com.ilku1297.db.hibernate;

import java.util.List;

public class GirlService {

    private GirlDao usersDao = new GirlDao();

    public GirlService() {
    }

    public Girl findGirl(int id) {
        return usersDao.findById(id);
    }

    public void saveGirl(Girl user) {
        usersDao.save(user);
    }

    public void deleteGirl(Girl user) {
        usersDao.delete(user);
    }

    public void updateGirl(Girl user) {
        usersDao.update(user);
    }

    public List<Girl> findAllUsers() {
        return usersDao.findAll();
    }

}