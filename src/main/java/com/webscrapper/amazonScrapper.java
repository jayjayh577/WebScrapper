package com.webscrapper;

import org.hibernate.SessionFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

//PUSH TO DATABASE
public class amazonScrapper {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        String websiteUrl = "https://www.amazon.co.uk/s?k=gaming+laptops&i=computers&rh=n%3A429886031&dc&qid=1700586971&rnid=27387795031&ref=sr_nr_p_n_feature_twenty-two_browse-bin_3&ds=v1%3AElLkY%2FuQnAszlDSMeK9%2B1SDI740T3SMZpwcR9GBCkDc";
        int currentPage = 1;
        int maxPages = 5; // Set the maximum number of pages to scrape

        ChromeOptions options = new ChromeOptions();

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        while (currentPage <= maxPages) {
            String pageUrl = websiteUrl + "&page=" + currentPage;

            driver.get(pageUrl);


           // Re-find the elements after navigating back
            List<WebElement> elements = driver.findElements(By.cssSelector("[data-action='puis-card-container-declarative']"));

            // Iterate over the elements
            for (WebElement element : elements) {
                // Extract information for each item
                WebElement headingElement = safeFindElement(element, By.cssSelector("span.a-size-medium.a-text-normal"));
                String priceText = safeFindElementText(element, By.cssSelector("span.a-price-whole"));
                String imageUrl = safeFindElementAttribute(element, By.cssSelector("img.s-image"), "src");

                // Check if the elements are not null before accessing text
                String headingText = (headingElement != null) ? headingElement.getText() : "N/A";

                System.out.println("Heading: " + headingText);
                System.out.println("Price: " + priceText);
                System.out.println("Image: " + imageUrl);

                // Find the link element
                WebElement linkElement = element.findElement(By.cssSelector("a.a-link-normal.s-underline-text.s-underline-link-text.s-link-style.a-text-normal"));

// Extract the href attribute from the link
                String linkUrl = linkElement.getAttribute("href");

// Click on the link using JavaScriptExecutor to avoid "element not clickable" issues
                clickLinkWithJavaScript(driver, linkElement);
                // \Print the URL of the new page
                System.out.println("Current URL: " + driver.getCurrentUrl());

// Wait for some time to ensure the new page loads (you may need to adjust the sleep duration)
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Now you're on the new page, try to find the element using CSS selector
                try {
                    // Find the more details element on the new page
                    WebElement moreDetailsElement = driver.findElement(By.cssSelector("[data-csa-c-content-id='productOverview']"));

                    // Extract information from the new page
                    String moreDetailsText = (moreDetailsElement != null) ? moreDetailsElement.getText() : "N/A";
                    System.out.println( moreDetailsText);

                } catch (NoSuchElementException e) {
                    // Print a message if the element is not found
                    System.out.println("Element not found on the new page!");
                }

                System.out.println("---------------------");
                // Re-find the elements after navigating back
                // Navigate back to the previous page to continue scraping other elements
                driver.navigate().back();
                // If you want to save to the database, call your saveToDatabase method here
                // saveToDatabase(new gamingPc(headingText, priceText, imageUrl));
            }
// Re-find the elements after navigating back
            elements = driver.findElements(By.cssSelector("[data-action='puis-card-container-declarative']"));
                currentPage++;
            // Navigate back to the previous page to continue scraping other elements
            driver.navigate().back();

        }
        // Close the browser
        driver.quit();
    }

    private static WebElement safeFindElement(WebElement parent, By by) {
        try {
            return parent.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    public static void clickLinkWithJavaScript(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }


    private static String safeFindElementText(WebElement parent, By by) {
        WebElement element = safeFindElement(parent, by);
        return (element != null) ? element.getText() : null;
    }

    private static String safeFindElementAttribute(WebElement parent, By by, String attribute) {
        WebElement element = safeFindElement(parent, by);
        return (element != null) ? element.getAttribute(attribute) : null;
    }

    private static void saveToDatabase(gamingPc laptop) {


    }
}
