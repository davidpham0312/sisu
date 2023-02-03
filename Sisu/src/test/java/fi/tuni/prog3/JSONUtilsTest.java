package fi.tuni.prog3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import fi.tuni.prog3.utils.JSONUtils;

public class JSONUtilsTest {
    @Test
    public void testReadJSONFromFile() {
        try {
            String json = JSONUtils.readJSONFromFile("src/test/resources/basic.json");
            assertEquals("{\"name\":\"test\"}", json);
        } catch (IOException e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void testFetchJSONFromURL() {
        try {
            String json = JSONUtils.fetchJSONfromURL(
                    "https://sis-tuni.funidata.fi/kori/api/modules/otm-1d25ee85-df98-4c03-b4ff-6cad7b09618b");
            assertTrue(json.contains("Bachelor's Programme in Computer Sciences"));
        } catch (IOException e) {
            fail("Exception thrown. Are you connected to the Internet?");
        }
    }
}
