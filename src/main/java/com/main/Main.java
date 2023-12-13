package com.main;

import com.control.AppConfig;
import com.control.ScrapperControl;
import com.webscrapper.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * This is the main file for the java application
 * Uses Spring's ApplicationContext to configure the application and initiate the scraper threads.
 *
 * @author JayJay Okeiyi-Anyim
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                AppConfig.class
        );
        ScrapperControl handler = (ScrapperControl) context.getBean("scrapperControl");
        handler.startThreads();
        handler.joinThreads();
    }
}
