package fi.tuni.prog3.parser;

import com.google.gson.Gson;

import fi.tuni.prog3.model.Student;

import java.io.*;

public class StudentParser {
    private StudentParser() {
        throw new IllegalStateException("Static class");
    }

    public static Student fromJSON(String studentNumber, String studentInfoPath)
            throws IOException {
        if (Student.isStudentExisting(studentNumber, studentInfoPath)) {
            Gson gson = new Gson();
            File studentInfoFolder = new File(studentInfoPath);
            String loadFileName = studentNumber + "_student.json";
            try (Reader reader = new FileReader(studentInfoFolder + "/" + loadFileName)) {
                return gson.fromJson(reader, Student.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace(); // this function is only run when file exists
            } catch (IOException e) {
                throw e;
            }
        }
        return null;
    }
}
