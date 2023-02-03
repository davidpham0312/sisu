package fi.tuni.prog3.parser;

import java.io.IOException;

import com.google.gson.*;

import fi.tuni.prog3.model.CourseUnit;
import fi.tuni.prog3.utils.JSONUtils;

public class CourseUnitParser {
    private CourseUnitParser() {
        throw new IllegalStateException("Static class");
    }

    public static CourseUnit fromJSON(String courseUnitFile) throws IOException {
        try {
            Gson gson = new Gson();
            CourseUnit course = gson.fromJson(JSONUtils.readJSONFromFile(courseUnitFile), CourseUnit.class);
            course.setIsCompleted(false);
            return course;
        } catch (IOException e) {
            throw e;
        }
    }

    public static CourseUnit fromURL(String url) throws IOException {
        try {
            Gson gson = new Gson();
            String jsonString = JSONUtils.fetchJSONfromURL(url);
            if (jsonString.charAt(0) == '[') {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
            }
            CourseUnit course = gson.fromJson(jsonString, CourseUnit.class);
            course.setIsCompleted(false);
            course.setIsRegistered(false);
            return course;
        } catch (IOException e) {
            throw e;
        }
    }
}
