package com.webscrapper;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class technoWorldScrapper {
    public static void scrapeEeData(String websiteUrl, int maxPages) {
        ChromeOptions options = new ChromeOptions();

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver(options);

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
            List<WebElement> elements = driver.findElements(By.cssSelector("li.klevuProduct"));

            // Iterate over the elements
            for (WebElement element : elements) {
                // Extract information for each item
                WebElement headingElement = safeFindElement(element, By.cssSelector(".kuClippedOne.kuName > .kuTrackRecentView.klevuProductClick"));
                WebElement priceElement = safeFindElement(element, By.cssSelector("span.kuSpecialPrice.kuSalePrice"));
                String imageUrl = safeFindElementAttribute(element, By.cssSelector("img.kuProdImg"), "src");
                WebElement websiteLinkElement = safeFindElement(element, By.cssSelector("a.kuTrackRecentView.klevuProductClick"));

                // Check if the elements are not null before accessing text
                String headingText = (headingElement != null) ? headingElement.getText() : "N/A";
                String priceText = (priceElement != null) ? priceElement.getText() : "N/A";
                String imageUrlText = (imageUrl != null) ? imageUrl : "N/A";

                // Extract the website link URL
                String websiteLink = (websiteLinkElement != null) ? websiteLinkElement.getAttribute("href") : "N/A";

                System.out.println("Heading: " + headingText);
                System.out.println("Price: " + priceText);
                System.out.println("Image: " + imageUrlText);
                System.out.println("Website Link: " + websiteLink);
            }
            currentPage++;
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

    private static String safeFindElementAttribute(WebElement parent, By by, String attribute) {
        WebElement element = safeFindElement(parent, by);
        return (element != null) ? element.getAttribute(attribute) : null;
    }
}

