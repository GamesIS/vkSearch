package com.ilku1297.db.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;


public class GirlDao {

    public Girl findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Girl.class, id);
    }

    public void save(Girl girl) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(girl);
        tx1.commit();
        session.close();
    }

    public void update(Girl girl) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(girl);
        tx1.commit();
        session.close();
    }

    public void delete(Girl girl) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(girl);
        tx1.commit();
        session.close();
    }

    public List<Girl> findAll() {
        List<Girl> users = (List<Girl>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }
}