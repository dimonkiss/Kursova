package org.example;

import org.example.entities.PostEntity;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        var sf = HibernateUtil.getSessionFactory();

        try(Session session = sf.openSession())
        {
            session.beginTransaction();

            Calendar calendar = Calendar.getInstance();
            PostEntity post = new PostEntity();
            post.setContent("1234");
            post.setTitle("123");
            post.setImageUrl("2.jpg");
            post.setDatePosted(calendar.getTime());

            session.save(post);
            session.getTransaction().commit();
        }
        sf.close();
    }
}