package com.ilku1297.db.hibernate.photo;

import com.ilku1297.db.hibernate.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class PhotoDao {

    public Photo findById(int id) {
          return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Photo.class, id);
    }

    public void save(Photo photo) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try{
            session.save(photo);
            tx1.commit();
        }
        catch (Exception e){
            tx1.rollback();
            throw e;
        }
        session.close();
    }

    public void update(Photo photo) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try{
            session.update(photo);
            tx1.commit();
        }
        catch (Exception e){
            tx1.rollback();
            throw e;
        }
        session.close();
    }

    public void delete(Photo photo) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try{
            session.delete(photo);
            tx1.commit();
        }
        catch (Exception e){
            tx1.rollback();
            throw e;
        }
        session.close();
    }

    public List<Photo> findAll() {
        List<Photo> photos = (List<Photo>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return photos;
    }
}