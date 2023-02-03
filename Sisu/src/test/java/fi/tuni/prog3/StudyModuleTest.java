/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fi.tuni.prog3.model.Credits;
import fi.tuni.prog3.model.LanguageStringTuple;
import fi.tuni.prog3.model.StudyModule;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author anhpham
 */
public class StudyModuleTest {

    StudyModule instance = new StudyModule("id", "groupId", new LanguageStringTuple("en", "fi"),
            new Credits(3, 5));

    @BeforeEach
    public void setUp() {
        instance = new StudyModule("id", "groupId", new LanguageStringTuple("en", "fi"),
                new Credits(3, 5));

    }

    /**
     * Test of getCredits method, of class StudyModule.
     */
    @Test
    public void testGetCredits() {
        System.out.println("getCredits");
        Credits expCredits = new Credits(3, 5);
        Credits result = instance.getCredits();
        assertEquals(expCredits.getMinCredits(), result.getMinCredits());
        assertEquals(expCredits.getMaxCredits(), result.getMaxCredits());
    }

    /**
     * Test of setCredits method, of class StudyModule.
     */
    @Test
    public void testSetCredits() {
        System.out.println("setCredits");
        Credits expCredits = new Credits(3, 5);
        instance.setCredits(new Credits(3, 5));
        Credits result = instance.getCredits();
        assertEquals(expCredits.getMinCredits(), result.getMinCredits());
        assertEquals(expCredits.getMaxCredits(), result.getMaxCredits());
    }

    /**
     * Test of getCompletedCredits method, of class StudyModule.
     */
    @Test
    public void testGetCompletedCredits() {
        System.out.println("getCompletedCredits");
        int expResult = 0;
        int result = instance.getCompletedCredits();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCompletedCredits method, of class StudyModule.
     */
    @Test
    public void testSetCompletedCredits() {
        System.out.println("setCompletedCredits");
        int expResult = 5;
        instance.setCompletedCredits(5);
        int result = instance.getCompletedCredits();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRegisteredCredits method, of class StudyModule.
     */
    @Test
    public void testGetRegisteredCredits() {
        System.out.println("getRegisteredCredits");
        int expResult = 0;
        int result = instance.getRegisteredCredits();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRegisteredCredits method, of class StudyModule.
     */
    @Test
    public void testSetRegisteredCredits() {
        System.out.println("setRegisteredCredits");
        int expResult = 5;
        instance.setRegisteredCredits(5);
        int result = instance.getRegisteredCredits();
        assertEquals(expResult, result);
    }
}
