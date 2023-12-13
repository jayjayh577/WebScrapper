package com.webscrapper;

import com.control.HibernateMain;
import com.gamingpcdatabase.GamingPc;
import com.gamingpcdatabase.GamingPcDetails;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class cyberPowerScrapper extends Thread{

    @Override
    public void run() {
        String websiteUrl = "https://www.cyberpowersystem.co.uk/category/gaming-laptops/";
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
            boolean isFirstRun = true;
            if (isFirstRun) {
                // Inside your loop
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Use Duration.ofSeconds for timeout
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("systemlist-allBtn")));

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

                try {
                    button.click();
                } catch (ElementClickInterceptedException e) {
                    // If standard click fails, use JavaScript click
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                }
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
                isFirstRun = false;
            }

            try {
                Thread.sleep(1000);  // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            List<WebElement> elements = driver.findElements(By.cssSelector("div.card"));

            // Iterate over the elements
            for (WebElement element : elements) {

                // Extract information for each item
                WebElement headingElement = safeFindElement(element, By.cssSelector("h3.card-title"));

                WebElement priceElement = safeFindElement(element, By.cssSelector("div.system__price-wrap"));

                String imageUrl = safeFindElementAttribute(element, By.cssSelector("img.mw-100.h-auto[style='aspect-ratio: 1;']"), "src");


                WebElement websiteLinkElement = safeFindElement(element, By.cssSelector("div.card-header a"));


                // Check if the elements are not null before accessing text
                String headingText = (headingElement != null) ? headingElement.getText() : "N/A";
                String priceText = (priceElement != null) ? priceElement.getText() : "N/A";
                String imageUrlText = (imageUrl != null) ? imageUrl : "N/A";

                String[] parts = headingText.split(" ");

                // Extract the website link URL
                String websiteLink = (websiteLinkElement != null) ? websiteLinkElement.getAttribute("href") : "N/A";

                String brand = "";
                String model = "";

                if (parts.length >= 2) {
                    brand = parts[0] + " " + parts[1]; // First two words as brand
                    model = parts.length > 2 ? parts[2] : ""; // Rest of the string as model if it exists
                } else if (parts.length == 1) {
                    brand = parts[0]; // Only one word, so it's the brand
                    model = ""; // No model information
                } else {
                    // Handle the case where the string is empty or doesn't have any spaces
                    brand = "N/A";
                    model = "N/A";
                }

                //                System.out.println(brand);
//                System.out.println(model);
//                System.out.println(brand);
//                System.out.println(headingText);
//                System.out.println(priceText);
//                System.out.println(imageUrlText);
//                System.out.println(websiteLink);

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

    public static String safeFindElementAttribute(WebElement parentElement, By selector, String attribute) {
        try {
            WebElement element = parentElement.findElement(selector);
            return element.getAttribute(attribute);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}

