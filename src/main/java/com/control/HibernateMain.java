package com.control;

import com.gamingpcdatabase.GamingPc;
import com.gamingpcdatabase.GamingPcDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateMain {

    private  SessionFactory sessionFactory;

    public HibernateMain() {
    }

    /**
     * Sets up the session factory and calls this method first*
     */
    public void start(){
        try {
            //Create a builder for the standard service registry
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            //Load configuration from hibernate configuration file
            standardServiceRegistryBuilder.configure("hibernate.cfg.xml");

            //Create the registry that will be used to build the session factory
            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();

            try {
                //Create the session factory - this is the goal of the init method.
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory,
                        but we had trouble building the SessionFactory, so destroy it manually */
                System.err.println("Session Factory build failed.");
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy( registry );
            }

            //Ouput result
            System.out.println("Session factory built.");

        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("SessionFactory creation failed." + ex);
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    /** Closes Hibernate down and stops its threads from running */
    public void shutDown(){
        sessionFactory.close();
    }

    /** Adding new Gaming laptop brand to the database*/
    public void addGamingPc(GamingPc gamingpc){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(gamingpc);
        session.getTransaction().commit();
        session.close();
    }

    /**Adding Gaming Laptop details */

    public void addGamingPcDetails(GamingPcDetails details){
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(details);
            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }
    }



}
