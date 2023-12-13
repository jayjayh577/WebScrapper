package com.webscrapper;

import com.control.HibernateMain;
import com.gamingpcdatabase.GamingPc;
import com.gamingpcdatabase.GamingPcDetails;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.time.Duration;

import static java.lang.Thread.sleep;

public class amazonScrapper extends Thread{
    @Override
    public void run() {
        String websiteUrl = "https://www.amazon.co.uk/s?k=gaming+laptops&i=computers&rh=n%3A429886031&dc&qid=1700586971&rnid=27387795031&ref=sr_nr_p_n_feature_twenty-two_browse-bin_3&ds=v1%3AElLkY%2FuQnAszlDSMeK9%2B1SDI740T3SMZpwcR9GBCkDc";
        int maxPages = 60 ;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        HibernateMain hibernate = new HibernateMain();
        hibernate.start();

        int currentPage = 1;

        while (currentPage <= maxPages) {
            String pageUrl = websiteUrl + "&page=" + currentPage;

            driver.get(pageUrl);

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            List<WebElement> elements = driver.findElements(By.cssSelector("[data-action='puis-card-container-declarative']"));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-action='puis-card-container-declarative']")));

            for (WebElement element : elements) {
                // Extract information for each item
                WebElement headingElement = safeFindElement(element, By.cssSelector("span.a-size-medium.a-text-normal"));
                WebElement priceSymbolElement = safeFindElement(element, By.cssSelector("span.a-price-symbol"));
                WebElement priceElement = safeFindElement(element, By.cssSelector("span.a-price-whole"));
                String imageUrl = safeFindElementAttribute(element, By.cssSelector("img.s-image"), "src");
                WebElement websiteLinkElement = safeFindElement(element, By.cssSelector("a.a-link-normal.s-no-outline"));

                // Check if the elements are not null before accessing text
                String headingText = (headingElement != null) ? headingElement.getText() : "N/A";
                String priceText = (priceElement != null) ? priceElement.getText() : "N/A";
                String imageUrlText = (imageUrl != null) ? imageUrl : "N/A";
                String priceSymbolText = (priceSymbolElement != null) ? priceSymbolElement.getText() : "N/A";

                // Extract the website link URL
                String websiteLink = (websiteLinkElement != null) ? websiteLinkElement.getAttribute("href") : "N/A";

//                System.out.println(priceText);
//                System.out.println(websiteLink);
//                System.out.println(headingText);



                if (!websiteLink.equals("N/A")) {
                    driver.get(websiteLink);

                    // Wait for the page to load (you may need to adjust the sleep duration)
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String brandText = null;
                    String nameText = null;
                    String modelText = null;
                    String descriptionText = null;
                    try {
                        // Extract additional information from the product page
                        WebElement brandElement = safeFindElemente(driver, By.cssSelector("tr.a-spacing-small.po-brand"));
                        brandText = (brandElement != null) ? brandElement.getText() : "N/A";

                        WebElement nameElement = safeFindElemente(driver, By.cssSelector("span.product-title-word-break"));
                        nameText = (nameElement != null) ? nameElement.getText() : "N/A";
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        WebElement modelElement = safeFindElemente(driver, By.cssSelector("tr.a-spacing-small.po-model_name"));
                        modelText = (modelElement != null) ? modelElement.getText() : "N/A";

                        WebElement descriptionElement = safeFindElemente(driver, By.cssSelector("li.a-spacing-mini"));
                        descriptionText = (descriptionElement != null) ? descriptionElement.getText() : "N/A";

                    } catch (NoSuchElementException e) {
                        // Handle the case where the element is not found
                        System.out.println("Brand element not found on the page.");
                    }catch (StaleElementReferenceException e) {
                        System.out.println("StaleElementReferenceException: Retrying operation...");
                        // You can choose to retry the same operation or move on to the next element
                        // For example, you might want to continue with the next iteration of the loop
                        continue;
                    }


                    // Print or store the additional information
                    GamingPc pc = new GamingPc();

                    pc.setWebsiteUrl(websiteLink);
                    pc.setPrice(priceText);
                    pc.setImageUrl(imageUrlText);

                    GamingPcDetails details = new GamingPcDetails();
                    details.setName(headingText);
                    details.setBrand(brandText);
                    details.setModel(modelText);
                    details.setDescription(descriptionText);



                        hibernate.addGamingPcDetails(details);


// Associate GamingPcDetails with GamingPc
                    pc.setDetails(details);

// Now save the GamingPc instance
                    hibernate.addGamingPc(pc);

                    // Return to the main loop
                    driver.navigate().back();
                }
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
        }catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException: Retrying operation...");
            // You can choose to retry the same operation or move on to the next element
            // For example, you might want to continue with the next iteration of the loop
            return null;
        }
    }
    static WebElement safeFindElemente(WebDriver driver, By by) {
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
