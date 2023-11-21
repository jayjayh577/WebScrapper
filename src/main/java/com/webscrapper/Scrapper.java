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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

//PUSH TO DATABASE
public class Scrapper {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        String websiteUrl = "https://www.amazon.co.uk/s?bbn=429886031&rh=n%3A429886031%2Cp_n_feature_twenty-two_browse-bin%3A27387843031&dc&qid=1700498597&rnid=27387795031&ref=lp_429886031_nr_p_n_feature_twenty-two_browse-bin_0";


        ChromeOptions options = new ChromeOptions();

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        // Navigate to the website
        driver.get(websiteUrl);

        try {
            Thread.sleep(3000);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        System.out.println(driver.getPageSource());
// Find all elements with the specified class
        List<WebElement> elements = driver.findElements(By.cssSelector("[data-action='puis-card-container-declarative']"));

        System.out.println(elements);

        // Iterate over the elements
        for (WebElement element : elements) {
            // Extract information for each item
            WebElement headingElement = element.findElement(By.cssSelector("span.a-size-base-plus"));
            String priceText = null;

            try {
                WebElement priceElement = element.findElement(By.cssSelector("span.a-price-whole"));
                priceText = priceElement.getText();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // Price element not found, set priceText to null or handle as needed
            }
            WebElement ImageElement = element.findElement(By.cssSelector("img.s-image"));

            // Check if the elements are not null before accessing text
            String headingText = (headingElement != null) ? headingElement.getText() : "N/A";
            String ImageUrl = (ImageElement != null) ? ImageElement.getText() : "N/A";

            System.out.println("Heading: " + headingText);
            System.out.println("Price: " + priceText);
            System.out.println("Image: " + ImageUrl);
            System.out.println("---------------------");
        }

        // Close the browser
        driver.quit();

    }

    private static void saveToDatabase(gamingPc laptop) {

    }
}
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
//            // Create a Hibernate session factory
//            // Create the SessionFactory from hibernate.cfg.xml
//            Configuration configuration = new Configuration().configure();
//            configuration.addAnnotatedClass(com.webscrapper.gamingPc.class);
//            configuration.addAnnotatedClass(com.webscrapper.gamingPcDetails.class);
//
//            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//
//            // Build the SessionFactory
//            sessionFactory = configuration.buildSessionFactory(builder.build());
//
//            try (Session session = sessionFactory.openSession()) {
//                // Begin a transaction
//                Transaction transaction = session.beginTransaction();
//
//                for (Element element : elements) {
////                    String headingText = element.selectFirst(".pr-1kvtvok").text();
//                    String dataNowText = element.selectFirst(".pr-yp1q6p").text();
////                    String ratingsText = element.selectFirst(".pr-1mnigsh").text();
//
//                    gamingPc gamingLaptop = new gamingPc();
//                    gamingLaptop.setWebsite_url(websiteurl);
//                    gamingLaptop.setPrice(dataNowText);
//
//                    // Save the gaming laptop to the database
//                    session.persist(gamingLaptop);
//                }
//
//                // Commit the transaction
//                transaction.commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//
////            for (Element element : elements) {
////                // Extract information for each item
////                Element headingElement = element.selectFirst(".pr-1kvtvok");
////                Element dataNowElement = element.selectFirst(".pr-yp1q6p");
////                Element ratings = element.selectFirst(".pr-1mnigsh");
////
////                // Check if the elements are not null before accessing text
////                String headingText = (headingElement != null) ? headingElement.text() : "N/A";
////                String dataNowText = (dataNowElement != null) ? dataNowElement.text() : "N/A";
////                String ratingsText = (ratings != null) ? ratings.text() : "N/A";
////
////                System.out.println(websiteurl);
////                System.out.println("Heading: " + headingText);
////                System.out.println("Price: " + dataNowText);
////                System.out.println("Ratings: " + ratingsText);
////                System.out.println("---------------------");
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//}


//MULTIPLE SCRAPE FROM PRICERUNNER

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

//MULTIPLE SCRAPE FROM EE

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


//SINGLE SCRAPE FROM EE

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
