/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fi.tuni.prog3.model.Credits;
import fi.tuni.prog3.model.DegreeProgramme;
import fi.tuni.prog3.model.LanguageStringTuple;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author anhpham
 */
public class DegreeProgrammeTest {

    DegreeProgramme instance = new DegreeProgramme("id", "groupId", new LanguageStringTuple("en", "fi"), "code",
            new Credits(3, 5));

    @BeforeEach
    public void setUp() {
        instance = new DegreeProgramme("id", "groupId", new LanguageStringTuple("en", "fi"), "code", new Credits(3, 5));
    }

    /**
     * Test of getCode method, of class DegreeProgramme.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        String expResult = "code";
        String result = instance.getCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCode method, of class DegreeProgramme.
     */
    @Test
    public void testSetCode() {
        System.out.println("setCode");
        String code = "newCode";
        instance.setCode(code);
        assertEquals(code, instance.getCode());
    }

    /**
     * Test of getCredits method, of class DegreeProgramme.
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
     * Test of setCredits method, of class DegreeProgramme.
     */
    @Test
    public void testSetCredits() {
        System.out.println("setCredits");
        Credits expCredits = new Credits(3, 5);
        instance.setCredits(expCredits);
        Credits result = instance.getCredits();
        assertEquals(expCredits.getMinCredits(), result.getMinCredits());
        assertEquals(expCredits.getMaxCredits(), result.getMaxCredits());
    }

}
