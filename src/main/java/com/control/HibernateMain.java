package com.control;

import com.webscrapper.gamingPc;
import com.webscrapper.gamingPcDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateMain {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(gamingPc.class);
            configuration.addAnnotatedClass(gamingPcDetails.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

            // Build the SessionFactory
            sessionFactory = configuration.buildSessionFactory(builder.build());

            // Open a session
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            // Commit the transaction
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
}
