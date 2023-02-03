package fi.tuni.prog3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fi.tuni.prog3.model.Credits;

import static org.junit.jupiter.api.Assertions.*;

public class CreditsTest {
    Credits instance = new Credits(3, 5);

    @BeforeEach
    public void setUp() {
        instance = new Credits(3, 5);
    }

    @Test
    public void testGetMinCredits() {
        System.out.println("getMinCredits");
        int expResult = 3;
        int result = instance.getMinCredits();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMaxCredits() {
        System.out.println("getMaxCredits");
        int expResult = 5;
        int result = instance.getMaxCredits();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetMinCredits() {
        System.out.println("setMinCredits");
        int minCredits = 4;
        instance.setMinCredits(minCredits);
        assertEquals(minCredits, instance.getMinCredits());
    }

    @Test
    public void testSetMaxCredits() {
        System.out.println("setMaxCredits");
        int maxCredits = 6;
        instance.setMaxCredits(maxCredits);
        assertEquals(maxCredits, instance.getMaxCredits());
    }
}
