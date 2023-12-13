package com.webscrapper;

import com.control.HibernateMain;
import com.gamingpcdatabase.GamingPc;
import com.gamingpcdatabase.GamingPcDetails;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class argosScrapper extends Thread{
    @Override
    public void run() {
        String websiteUrl = "https://www.argos.co.uk/browse/technology/laptops-and-pcs/gaming-laptops/c:30279/?clickOrigin=header:search:menu:gaming+laptops";

        int maxPages = 1;
        ChromeOptions options = new ChromeOptions();


        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver(options);
        HibernateMain hibernate = new HibernateMain();
        hibernate.start();

        int currentPage = 1;

        while (currentPage <= maxPages) {

            String pageUrl = websiteUrl + "&page=" + currentPage;

            driver.get(pageUrl);

            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> elements = driver.findElements(By.cssSelector("[data-test='component-product-card']"));

            // Iterate over the elements
            for (WebElement element : elements) {
                // Extract information for each item
                WebElement headingElement = safeFindElement(element, By.cssSelector("div.PQnCV"));

                WebElement priceElement = safeFindElement(element, By.cssSelector("[data-test='component-product-card-price']"));

                String imageUrl = safeFindElementAttribute(element, By.cssSelector("[data-test='component-image']"), "src");

                WebElement websiteLinkElement = safeFindElement(element, By.cssSelector("[data-test='component-product-card-title-link']"));

                // Check if the elements are not null before accessing text
                String headingText = (headingElement != null) ? headingElement.getText() : "N/A";
                String priceText = (priceElement != null) ? priceElement.getText() : "N/A";
                String imageUrlText = (imageUrl != null) ? imageUrl : "N/A";

                String[] parts = headingText.split(" ");

                // Extract the website link URL
                String websiteLink = (websiteLinkElement != null) ? websiteLinkElement.getAttribute("href") : "N/A";
                String brand = "";
                String model = "";

                if (parts.length > 0) {
                    brand = parts[0]; // First part as brand

                    // Extract the model from the remaining parts
                    for (int i = 1; i < parts.length; i++) {
                        model += parts[i] + (i < parts.length - 1 ? " " : ""); // Add space between parts, but not at the end
                    }
                } else {
                    // Handle the case where the string is empty or doesn't have any parts
                    brand = "N/A";
                    model = "N/A";
                }

                // Print or store the additional information
                GamingPc pc = new GamingPc();

                pc.setWebsiteUrl(websiteLink);
                pc.setPrice(priceText);
                pc.setImageUrl(imageUrlText);

                GamingPcDetails details = new GamingPcDetails();
                details.setName(headingText);
                details.setBrand(brand);
                details.setModel(model);
                details.setDescription("No description available");


                // Save the GamingPcDetails instance
                hibernate.addGamingPcDetails(details);

// Associate GamingPcDetails with GamingPc
                pc.setDetails(details);

// Now save the GamingPc instance
                hibernate.addGamingPc(pc);

            }
            currentPage++;
        }
        // Close the browser
        driver.quit();
        hibernate.shutDown();
    }

    private static WebElement safeFindElement(WebElement parent, By by) {
        try {
            return parent.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    private static WebElement safeFindElemente(WebDriver driver, By by) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                return driver.findElement(by);
            } catch (StaleElementReferenceException e) {
                // Retry in case of StaleElementReferenceException
            }
            attempts++;
        }
        return null;
    }

    private static String safeFindElementAttribute(WebElement parent, By by, String attribute) {
        WebElement element = safeFindElement(parent, by);
        return (element != null) ? element.getAttribute(attribute) : null;
    }
}
