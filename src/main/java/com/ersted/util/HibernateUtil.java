package com.ersted.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil(){
    }

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null || sessionFactory.isClosed())
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

    public static Session getSession(){
        return getSessionFactory().openSession();
    }
}
