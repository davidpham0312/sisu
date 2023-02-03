/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fi.tuni.prog3.model.CourseUnit;
import fi.tuni.prog3.model.Credits;
import fi.tuni.prog3.model.LanguageStringTuple;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author anhpham
 */
public class CourseUnitTest {

    CourseUnit instance = new CourseUnit("code", new LanguageStringTuple("en", "fi"), "id", "groupId",
            new Credits(3, 5),
            new LanguageStringTuple("en", "fi"));

    @BeforeEach
    public void setUp() {
        instance = new CourseUnit("code", new LanguageStringTuple("en", "fi"), "id", "groupId", new Credits(3, 5),
                new LanguageStringTuple("en", "fi"));
    }

    /**
     * Test of getCode method, of class CourseUnit.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        String expResult = "code";
        String result = instance.getCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class CourseUnit.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        String expResult = "id";
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class CourseUnit.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "newID";
        instance.setId(id);
        assertEquals(id, instance.getId());
    }

    /**
     * Test of getGroupId method, of class CourseUnit.
     */
    @Test
    public void testGetGroupId() {
        System.out.println("getGroupId");
        String expResult = "groupId";
        String result = instance.getGroupId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGroupId method, of class CourseUnit.
     */
    @Test
    public void testSetGroupId() {
        System.out.println("setGroupId");
        String groupId = "newGroupId";
        instance.setGroupId(groupId);
        assertEquals(groupId, instance.getGroupId());
    }

    /**
     * Test of getDescription method, of class CourseUnit.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        LanguageStringTuple languageStringObj = new LanguageStringTuple("en", "fi");
        String result = instance.getDescription();
        assertEquals(languageStringObj.getString(), result);
    }

    /**
     * Test of setDescription method, of class CourseUnit.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        LanguageStringTuple description = new LanguageStringTuple("en", "fi");
        instance.setDescription(description);
        assertEquals(description.getString(), instance.getDescription());
    }

    /**
     * Test of getCredits method, of class CourseUnit.
     */
    @Test
    public void testGetCredits() {
        System.out.println("getCredits");
        Credits expResult = new Credits(3, 5);
        Credits result = instance.getCredits();
        assertEquals(expResult.getMinCredits(), result.getMinCredits());
        assertEquals(expResult.getMaxCredits(), result.getMaxCredits());
    }

    /**
     * Test of setCredits method, of class CourseUnit.
     */
    @Test
    public void testSetCredits() {
        System.out.println("setCredits");
        Credits expCredits = new Credits(3, 5);
        instance.setCredits(expCredits);
        assertEquals(expCredits, instance.getCredits());
    }

    @Test
    public void testSetIsCompleted() {
        System.out.println("setIsCompleted");
        boolean expResult = true;
        instance.setIsCompleted(expResult);
        assertEquals(expResult, instance.getIsCompleted());
    }

    @Test
    public void testGetIsCompleted() {
        System.out.println("getIsCompleted");
        boolean expResult = true;
        instance.setIsCompleted(expResult);
        assertEquals(expResult, instance.getIsCompleted());
    }

    @Test
    public void testSetIsRegistered() {
        System.out.println("setIsRegistered");
        boolean expResult = true;
        instance.setIsRegistered(expResult);
        assertEquals(expResult, instance.getIsRegistered());
    }

    @Test
    public void testGetIsRegistered() {
        System.out.println("getIsRegistered");
        boolean expResult = true;
        instance.setIsRegistered(expResult);
        assertEquals(expResult, instance.getIsRegistered());
    }

    /**
     * Test of getName method, of class CourseUnit.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        LanguageStringTuple languageStringObj = new LanguageStringTuple("en", "fi");
        String result = instance.getName();
        assertEquals(languageStringObj.getString(), result);
    }

    /**
     * Test of setName method, of class CourseUnit.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        LanguageStringTuple name = new LanguageStringTuple("en", "fi");
        instance.setName(name);
        assertEquals(name.getString(), instance.getName());
    }

    /**
     * Test of setCode method, of class CourseUnit.
     */
    @Test
    public void testSetCode() {
        System.out.println("setCode");
        String code = "newCode";
        instance.setCode(code);
        assertEquals(code, instance.getCode());
    }
}
