package org.example.utils;

import lombok.Getter;
import org.example.entities.PostEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration conf = new Configuration().configure();
            conf.addAnnotatedClass(PostEntity.class);
            return conf.buildSessionFactory();
        }catch(Exception ex) {
            System.out.println("Помилка ініціалізації "+ ex.getMessage());
            return null;
        }
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}