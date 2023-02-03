package fi.tuni.prog3;

import fi.tuni.prog3.parser.StudentParser;
import fi.tuni.prog3.model.Student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentParserTest {
    Student expStudent;

    @BeforeEach
    void setUp() {
        expStudent = new Student("Quoc",
                "Nguyen",
                "test_parser",
                2019,
                2022,
                "123456");
    }

    @Test
    void fetchStudentFromJSONFileWrongPath() {
        try {
            Student resultStudent = StudentParser.fromJSON("test_parser",
                    "src/test/wrong_path/");
            assertNull(resultStudent);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void fetchStudentFromJSONFileWrongStudentNumber() {
        try {
            Student resultStudent = StudentParser.fromJSON("wrong_student_number",
                    "src/test/resources/");
            assertNull(resultStudent);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void fetchStudentFromJSONFileTest() {
        try {
            Student resultStudent = StudentParser.fromJSON("test_parser",
                    "src/test/resources/");
            assertAll(() -> assertEquals(expStudent.getFirstName(), resultStudent.getFirstName()),
                    () -> assertEquals(expStudent.getLastName(), resultStudent.getLastName()),
                    () -> assertEquals(expStudent.getStudentNumber(),
                            resultStudent.getStudentNumber()),
                    () -> assertEquals(expStudent.getStartYear(), resultStudent.getStartYear()),
                    () -> assertEquals(expStudent.getEndYear(), resultStudent.getEndYear()),
                    () -> assertTrue(resultStudent.verifyPassword("123456")));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}