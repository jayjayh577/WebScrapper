package com.webscrapper;
import com.control.HibernateMain;
import com.gamingpcdatabase.GamingPc;
import com.gamingpcdatabase.GamingPcDetails;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;


public class veryScrapper extends Thread{
    @Override
    public void run() {
        String websiteUrl = "https://www.very.co.uk/gaming-dvd/gaming-laptops-pcs/e/b/117124.end?pageNumber=";

        int maxPages = 4;
    ChromeOptions options = new ChromeOptions();
    options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

    // Initialize ChromeDriver
        WebDriver mover = new ChromeDriver(options);
    HibernateMain hibernate = new HibernateMain();
   hibernate.start();

    int currentPage = 1;

        while (currentPage <= maxPages) {
        String pageUrl = websiteUrl + currentPage;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        WebDriver driver = new ChromeDriver(options);
        driver.get(pageUrl);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> elements = driver.findElements(By.cssSelector("li.item.product"));
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        // Iterate over the elements
        for (WebElement element : elements) {
            // Extract information for each item
            WebElement headingElement = safeFindElement(element, By.cssSelector("span.productBrandDesc"));
            WebElement priceElement = safeFindElement(element, By.cssSelector("a.productPrice"));
            String imageUrl = safeFindElementAttribute(element, By.cssSelector("source[type='image/jpg']"));
           WebElement websiteLinkElement = safeFindElement(element, By.cssSelector("a.productTitle"));
            WebElement brandeElement = safeFindElement(element, By.cssSelector(".productBrand"));

            // Check if the elements are not null before accessing text
            String headingText = (headingElement != null) ? headingElement.getText() : "N/A";
            String priceText = (priceElement != null) ? priceElement.getText() : "N/A";
            String imageUrlText = (imageUrl != null) ? imageUrl : "N/A";
            String brandeText = (brandeElement != null) ? brandeElement.getText() : "N/A";

            String[] parts1 = headingText.split(" ", 3);

            // Extract the website link URL
            String websiteLink = (websiteLinkElement != null) ? websiteLinkElement.getAttribute("href") : "N/A";

            String brand = "";
            String model = "";

            if (parts1.length >= 2) {
                brand = parts1[0] + " " + parts1[1]; // First two words as brand
                model = parts1.length > 2 ? parts1[2] : ""; // Rest of the string as model if it exists
            } else if (parts1.length == 1) {
                brand = parts1[0]; // Only one word, so it's the brand
                model = ""; // No model information
            } else {
                // Handle the case where the string is empty or doesn't have any spaces
                brand = "N/A";
                model = "N/A";
            }
            String modelo = brand + model;
//                System.out.println(brand);
//                System.out.println(model);
//                System.out.println(headingText);
//                System.out.println(priceText);
//                System.out.println(imageUrlText);
//            System.out.println(websiteLink);
//            System.out.println(brandeText);


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
        driver.close();

        currentPage++;
    }
    // Close the browser
        mover.quit();
//        hibernate.shutDown();
}

static WebElement safeFindElement(WebElement parent, By by) {
    try {
        return parent.findElement(by);
    } catch (NoSuchElementException e) {
        return null;
    }
}

    public static String safeFindElementAttribute(WebElement parentElement, By selector) {
        try {
            WebElement element = parentElement.findElement(selector);
            String attributeValue = element.getAttribute("srcset");
            if (attributeValue == null || attributeValue.isEmpty()) {
                // Fallback to data-srcset if srcset is not present or empty
                attributeValue = element.getAttribute("data-srcset");
            }
            return attributeValue;
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}

