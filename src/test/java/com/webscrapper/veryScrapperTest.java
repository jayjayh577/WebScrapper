package com.webscrapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

class veryScrapperTest {
    @Mock
    private WebDriver mockDriver;
    @Mock
    private WebElement mockElement;
    private veryScrapper scrapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scrapper = new veryScrapper();
        // Inject mock WebDriver into your scrapper, if possible
    }

    @Test
    void testSafeFindElement() {
        when(mockDriver.findElement(any(By.class))).thenReturn(mockElement);
        assertNotNull(scrapper.safeFindElement(mockElement, By.cssSelector("someSelector")));
    }

    @Test
    void testSafeFindElementAttribute() {
        when(mockElement.findElement(any(By.class))).thenReturn(mockElement);
        when(mockElement.getAttribute(anyString())).thenReturn("testAttribute");
        assertNotNull(scrapper.safeFindElementAttribute(mockElement, By.cssSelector("img[srcset]")));
    }

    // Additional tests...
}