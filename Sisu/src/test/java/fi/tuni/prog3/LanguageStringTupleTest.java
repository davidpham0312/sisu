package fi.tuni.prog3;

import org.junit.jupiter.api.Test;

import fi.tuni.prog3.model.LanguageStringTuple;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageStringTupleTest {
    @Test
    public void testGetStringDefaultEnglish() {
        LanguageStringTuple instance = new LanguageStringTuple("fi", "en");
        String expResult = "en";
        String result = instance.getString();
        assertEquals(expResult, result);
    }

    @Test
    public void testFinnishIfEnglishIsNotAvailable() {
        LanguageStringTuple instance = new LanguageStringTuple("fi", null);
        String expResult = "fi";
        String result = instance.getString();
        assertEquals(expResult, result);
    }
}
