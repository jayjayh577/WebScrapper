package com.control;

import com.webscrapper.OverclockersScrapper;
import com.webscrapper.amazonScrapper;
import com.webscrapper.argosScrapper;
import com.webscrapper.veryScrapper;
import com.webscrapper.cyberPowerScrapper;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
    SessionFactory sessionFactory;

    /**
     * Bean to initialize ScraperControl.
     *
     * @return scrapercontrol
     */
    @Bean
    public ScrapperControl scrapperControl() {
        ScrapperControl scrapperControl = new ScrapperControl();

        List<Thread> scraperList = new ArrayList<>();
        scraperList.add(overClockers());
        scraperList.add(cyber());
        scraperList.add(argos());
        scraperList.add(very());
        scraperList.add(amazon());
        ScrapperControl.setScraperList(scraperList);

        // Return Scraper Handler object
        return scrapperControl;
    }

    /**
     * Bean to initialize Overclockers scraper.
     *
     * @return overclockers
     */
    @Bean
    public OverclockersScrapper overClockers() {
        return new OverclockersScrapper();
    }

    /**
     * Bean to initialize AmazonScraper.
     *
     * @return amazonScraper
     */
    @Bean
    public amazonScrapper amazon() {
        return new amazonScrapper();
    }

    /**
     * Bean to initialize ArgosScraper.
     *
     * @return argosScraper
     */
    @Bean
    public argosScrapper argos() {
        return new argosScrapper();
    }


    /**
     * Bean to initialize NewGGScraper.
     *
     * @return newGGScraper
     */
    @Bean
    public cyberPowerScrapper cyber() {
        return new cyberPowerScrapper();
    }

    /**
     * Bean to initialize EbayScrapper.
     *
     * @return ebayScrapper
     */
    @Bean
    public veryScrapper very() {
        return new veryScrapper();
    }

    /**
     * Bean to initialize HibernateMapping.
     *
     * @return hibernate
     */
    @Bean
    public HibernateMain hibernate() {
        HibernateMain hibernate = new HibernateMain();
        hibernate.setSessionFactory(sessionFactory());
        return hibernate;
    }

    /**
     * Bean to create SessionFactory.
     *
     * @return sessionFactory
     */
    @Bean
    public SessionFactory sessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
                standardServiceRegistryBuilder.configure("hibernate.cfg.xml");
                StandardServiceRegistry registry = standardServiceRegistryBuilder.build();

                try {
                    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
                } catch (Exception e) {
                    System.err.println("Session Factory build failed.");
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                System.out.println("Session factory built.");
            } catch (Throwable ex) {
                System.err.println("SessionFactory creation failed." + ex);
            }
        }
        return sessionFactory;
    }
}
