package com.ilku1297.db.hibernate.photo;

import java.util.List;

public class PhotoService {

    private PhotoDao usersDao = new PhotoDao();

    public PhotoService() {
    }

    public Photo findGirl(int id) {
        return usersDao.findById(id);
    }

    public void saveGirl(Photo user) {
        usersDao.save(user);
    }

    public void deleteGirl(Photo user) {
        usersDao.delete(user);
    }

    public void updateGirl(Photo user) {
        usersDao.update(user);
    }

    public List<Photo> findAllUsers() {
        return usersDao.findAll();
    }

}