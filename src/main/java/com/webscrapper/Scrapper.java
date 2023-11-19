package com.webscrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
//PUSH TO DATABASE
public class Scrapper {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        try {
            String websiteurl = "https://www.pricerunner.com/results?q=gaming%20laptops&suggestionsActive=true&suggestionClicked=false&suggestionReverted=false";

            Document document = Jsoup
                    .connect("https://www.pricerunner.com/results?q=gaming%20laptops&suggestionsActive=true&suggestionClicked=false&suggestionReverted=false")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                    .header("Accept-Language", "*")
                    .get();

            Elements elements = document.select(".pr-1r40pci-Card-root");

            // Create a Hibernate session factory
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(com.webscrapper.gamingPc.class);
            configuration.addAnnotatedClass(com.webscrapper.gamingPcDetails.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

            // Build the SessionFactory
            sessionFactory = configuration.buildSessionFactory(builder.build());

            try (Session session = sessionFactory.openSession()) {
                // Begin a transaction
                Transaction transaction = session.beginTransaction();

                for (Element element : elements) {
//                    String headingText = element.selectFirst(".pr-1kvtvok").text();
                    String dataNowText = element.selectFirst(".pr-yp1q6p").text();
//                    String ratingsText = element.selectFirst(".pr-1mnigsh").text();

                    gamingPc gamingLaptop = new gamingPc();
                    gamingLaptop.setWebsite_url(websiteurl);
                    gamingLaptop.setPrice(dataNowText);

                    // Save the gaming laptop to the database
                    session.persist(gamingLaptop);
                }

                // Commit the transaction
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

//            for (Element element : elements) {
//                // Extract information for each item
//                Element headingElement = element.selectFirst(".pr-1kvtvok");
//                Element dataNowElement = element.selectFirst(".pr-yp1q6p");
//                Element ratings = element.selectFirst(".pr-1mnigsh");
//
//                // Check if the elements are not null before accessing text
//                String headingText = (headingElement != null) ? headingElement.text() : "N/A";
//                String dataNowText = (dataNowElement != null) ? dataNowElement.text() : "N/A";
//                String ratingsText = (ratings != null) ? ratings.text() : "N/A";
//
//                System.out.println(websiteurl);
//                System.out.println("Heading: " + headingText);
//                System.out.println("Price: " + dataNowText);
//                System.out.println("Ratings: " + ratingsText);
//                System.out.println("---------------------");
//            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}


//MULTIPLE SCRAP FROM PRICERUNNER

//public class Scrapper {
//    public static void main(String[] args) {
//        try {
//            String websiteurl = "https://www.pricerunner.com/results?q=gaming%20laptops&suggestionsActive=true&suggestionClicked=false&suggestionReverted=false";
//
//            Document document = Jsoup
//                    .connect("https://www.pricerunner.com/results?q=gaming%20laptops&suggestionsActive=true&suggestionClicked=false&suggestionReverted=false")
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
//                    .header("Accept-Language", "*")
//                    .get();
//
//            Elements elements = document.select(".pr-1r40pci-Card-root");
//
//            for (Element element : elements) {
//                // Extract information for each item
//                Element headingElement = element.selectFirst(".pr-1kvtvok");
//                Element dataNowElement = element.selectFirst(".pr-yp1q6p");
//                Element ratings = element.selectFirst(".pr-1mnigsh");
//
//                // Check if the elements are not null before accessing text
//                String headingText = (headingElement != null) ? headingElement.text() : "N/A";
//                String dataNowText = (dataNowElement != null) ? dataNowElement.text() : "N/A";
//                String ratingsText = (ratings != null) ? ratings.text() : "N/A";
//
//                System.out.println(websiteurl);
//                System.out.println("Heading: " + headingText);
//                System.out.println("Price: " + dataNowText);
//                System.out.println("Ratings: " + ratingsText);
//                System.out.println("---------------------");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//}

//MULTIPLE SCRAP FROM EE

//public class Scrapper {
//    public static void main(String[] args) {
//        try {
//            Document document = Jsoup
//                    .connect("https://www.argos.co.uk/browse/technology/laptops-and-pcs/gaming-laptops/c:30279/")
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
//                    .header("Accept-Language", "*")
//                    .get();
//
//            Elements elements = document.select(".kyWCsu.styles__ProductList-sc-1rzb1sn-1");
//
//            for (Element element : elements) {
//                // Extract information for each item
//                Element headingElement = element.selectFirst(".PQnCV.ProductCardstyles__Title-h52kot-12");
//                Element dataNowElement = element.selectFirst(".kyWCsu.styles__ProductList-sc-1rzb1sn-1 > div.sm-4.xs-6--none.gZxiHo.styles__LazyHydrateCard-sc-1rzb1sn-0 > .fOIrbR.StyledProductCard-sc-1o1topz-0.dWoMVd.ProductCardstyles__Wrapper-h52kot-1 > .byQQSe.ProductCardstyles__ContentBlock-h52kot-5 > .dedmXK.ProductCardstyles__TextContainer-h52kot-6 > a.cnmosm.ProductCardstyles__Link-h52kot-13 > .ejTbAz.ProductCardstyles__ValueMessagingContainer-h52kot-14 > .dcBFwd.ProductCardstyles__PriceContainer-h52kot-15");
//                Element ratings = element.selectFirst(".djYnaM.Ratingsstyles__Wrapper-pi51c-0");
//
//                // Check if the elements are not null before accessing text
//                String headingText = (headingElement != null) ? headingElement.text() : "N/A";
//                String dataNowText = (dataNowElement != null) ? dataNowElement.text() : "N/A";
//                String ratingsText = (ratings != null) ? ratings.text() : "N/A";
//
//                System.out.println("Heading: " + headingText);
//                System.out.println("Data Now: " + dataNowText);
//                System.out.println("Ratings: " + ratingsText);
//                System.out.println("---------------------");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//}


//SINGLE SCRAP FROM EE

//public class Scrapper {
//    public static void main(String[] args) {
//        try {
//            Document document = Jsoup
//                    .connect("https://ee.co.uk/accessories/pay-monthly-gallery/atp-lenovo-legion-5-15inch-gaming-laptop-details")
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
//                    .header("Accept-Language", "*")
//                    .get();
//
//            Elements elements = document.select(".styles_headingContent__4Bh7q");
//            Element firstHeading = document.selectFirst(".plan-item__data-now");
//
//
//            for (Element element : elements) {
//                System.out.println(element.text());
//                System.out.println(firstHeading.text());
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//
//    }
//}
