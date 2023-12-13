package com.control;
import com.gamingpcdatabase.GamingPc;
import com.gamingpcdatabase.GamingPcDetails;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HibernateMainTest {
    private HibernateMain hibernateMain;

    @BeforeEach
    void setUp() {
        hibernateMain = new HibernateMain();
        // Configure and start hibernate with in-memory database settings
        hibernateMain.start(); // This should be modified to use in-memory DB for testing
    }

    @Test
    void testAddGamingPc() {
        GamingPc gamingPc = new GamingPc();
        // Set fields for gamingPc...

        assertDoesNotThrow(() -> hibernateMain.addGamingPc(gamingPc));
    }

    @Test
    void testAddGamingPcDetails() {
        GamingPcDetails details = new GamingPcDetails();
        // Set fields for details...

        assertDoesNotThrow(() -> hibernateMain.addGamingPcDetails(details));
    }

    @AfterEach
    void tearDown() {
        hibernateMain.shutDown();
    }
}