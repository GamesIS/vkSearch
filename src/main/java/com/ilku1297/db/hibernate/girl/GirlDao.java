package com.ilku1297.db.hibernate.girl;

import com.ilku1297.db.hibernate.HibernateSessionFactoryUtil;
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
        try{
            session.save(girl);
            tx1.commit();
        }
        catch (Exception e){
            tx1.rollback();
            throw e;
        }
        session.close();
    }

    public void update(Girl girl) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try{
            session.update(girl);
            tx1.commit();
        }
        catch (Exception e){
            tx1.rollback();
            throw e;
        }
        session.close();
    }

    public void delete(Girl girl) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try{
            session.delete(girl);
            tx1.commit();
        }
        catch (Exception e){
            tx1.rollback();
            throw e;
        }
        session.close();
    }

    public List<Girl> findAll() {
        List<Girl> users = (List<Girl>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From girls").list();
        return users;
    }
}