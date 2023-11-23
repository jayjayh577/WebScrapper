package com.webscrapper;

public class Main {
    public static void main(String[] args) {
        String amazonwebsiteUrl = "https://www.amazon.co.uk/s?k=gaming+laptops&i=computers&rh=n%3A429886031&dc&qid=1700586971&rnid=27387795031&ref=sr_nr_p_n_feature_twenty-two_browse-bin_3&ds=v1%3AElLkY%2FuQnAszlDSMeK9%2B1SDI740T3SMZpwcR9GBCkDc";
        String technoworldscrapperwebsiteurl = "https://www.technoworld.com/search/?q=gaming+laptops";
        String argoswebsiteurl = "https://www.argos.co.uk/search/gaming-laptop/category:50000084/";
        int maxPages = 5;

        amazonScrapper.scrapeAmazonData(argoswebsiteurl, 2);
        technoWorldScrapper.scrapeEeData(technoworldscrapperwebsiteurl, 1);
        amazonScrapper.scrapeAmazonData(amazonwebsiteUrl, maxPages);

    }
}
