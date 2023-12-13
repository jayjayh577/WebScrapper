package com.webscrapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class amazonScrapperTest {
    private WebDriver mockDriver;
    private WebElement mockElement;
    private amazonScrapper scrapper;

    @BeforeEach
    void setUp() {
        mockDriver = mock(WebDriver.class);
        mockElement = mock(WebElement.class);
        scrapper = new amazonScrapper();

    }

    @Test
    void testSafeFindElement() {
        when(mockDriver.findElement(any())).thenReturn(mockElement);
        assertNotNull(scrapper.safeFindElemente(mockDriver, By.id("testId")));
    }
}