package fi.tuni.prog3;

import fi.tuni.prog3.model.DegreeProgramme;
import fi.tuni.prog3.parser.ProgressParser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ProgressParserTest {
    @Test
    public void testParsingStudentProgress() {
        try {
            ArrayList<DegreeProgramme> progress = ProgressParser.fromJSON("./src/test/resources/studentProgress.json");
            assertEquals(1, progress.size());
            assertEquals(2, progress.get(0).getModules(false).size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}