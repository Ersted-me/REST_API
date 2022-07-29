package com.ersted.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil(){
    }

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null || sessionFactory.isClosed()){
            Map<String,String> jdbcUrlSettings = new HashMap<>();
            String jdbcDbUrl = System.getenv("JDBC_DATABASE_URL");
            if (null != jdbcDbUrl) {
                jdbcUrlSettings.put("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
            }

            StandardServiceRegistry build = new StandardServiceRegistryBuilder().
                    configure("hibernate.cfg.xml").
                    applySettings(jdbcUrlSettings).
                    build();
            sessionFactory = new Configuration().configure().buildSessionFactory(build);
        }
        return sessionFactory;
    }

    public static Session getSession(){
        return getSessionFactory().openSession();
    }
}
