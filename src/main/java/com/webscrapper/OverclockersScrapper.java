package com.webscrapper;

import com.control.HibernateMain;
import com.gamingpcdatabase.GamingPc;
import com.gamingpcdatabase.GamingPcDetails;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class OverclockersScrapper extends Thread{
    @Override
    public void run() {
        String websiteUrl = "https://www.overclockers.co.uk/laptops/gaming-laptops?label%5B0%5D=SALE";
        int maxPages = 3;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver(options);
        HibernateMain hibernate = new HibernateMain();
        hibernate.start();

        int currentPage = 1;

        while (currentPage <= maxPages) {
            String pageUrl = websiteUrl + "&page=" + currentPage;

            driver.get(pageUrl);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Re-find the elements after navigating back
            List<WebElement> elements = driver.findElements(By.tagName("ck-product-box"));

            // Iterate over the elements
            for (WebElement element : elements) {
                // Extract information for each item
                WebElement headingElement = safeFindElement(element, By.cssSelector("h6.h5.mb-0.text-break"));
                WebElement priceElement = safeFindElement(element, By.cssSelector("span.price__amount"));
                String imageUrl = safeFindElementAttribute(element, By.cssSelector("[data-qa ='component ck-img']"), "src");
                WebElement websiteLinkElement = safeFindElement(element, By.cssSelector("a[data-qa='ck-product-box--title-link']"));

                // Check if the elements are not null before accessing text
                String headingText = (headingElement != null) ? headingElement.getText() : "N/A";
                String priceText = (priceElement != null) ? priceElement.getText() : "N/A";
                String imageUrlText = (imageUrl != null) ? imageUrl : "N/A";

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
//                System.out.println(brand);
//                System.out.println(model);
//                System.out.println(headingText);
//                System.out.println(priceText);
//                System.out.println(imageUrlText);


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

    private static String safeFindElementAttribute(WebElement parent, By by, String attribute) {
        WebElement element = safeFindElement(parent, by);
        return (element != null) ? element.getAttribute(attribute) : null;
    }
}

