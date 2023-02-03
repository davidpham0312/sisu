package fi.tuni.prog3;

import fi.tuni.prog3.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

class StudentTest {

    Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student("Quoc",
                "Nguyen",
                "H123456",
                2019,
                2022,
                "98765432");
    }

    @Test
    void getFirstNameTest() {
        assertEquals("Quoc", testStudent.getFirstName());
    }

    @Test
    void setFirstNameTest() {
        String expFirstName = "Thinh";
        testStudent.setFirstName(expFirstName);
        assertEquals(expFirstName, testStudent.getFirstName());
    }

    @Test
    void getLastNameTest() {
        assertEquals("Nguyen", testStudent.getLastName());
    }

    @Test
    void setLastNameTest() {
        String expLastName = "Tran";
        testStudent.setLastName(expLastName);
        assertEquals(expLastName, testStudent.getLastName());
    }

    @Test
    void getStudentNumberTest() {
        assertEquals("H123456", testStudent.getStudentNumber());
    }

    @Test
    void setStudentNumberTest() {
        String expStudentNumber = "H987654";
        testStudent.setStudentNumber(expStudentNumber);
        assertEquals(expStudentNumber, testStudent.getStudentNumber());
    }

    @Test
    void getStartYearTest() {
        assertEquals(2019, testStudent.getStartYear());
    }

    @Test
    void setStartYearTest() {
        int expStartYear = 2018;
        testStudent.setStartYear(expStartYear);
        assertEquals(expStartYear, testStudent.getStartYear());
    }

    @Test
    void getEndYearTest() {
        assertEquals(2022, testStudent.getEndYear());
    }

    @Test
    void setEndYearTest() {
        int expEndYear = 2021;
        testStudent.setEndYear(expEndYear);
        assertEquals(expEndYear, testStudent.getEndYear());
    }

    @Test
    void verifyPasswordTest() {
        assertTrue(testStudent.verifyPassword("98765432"));
    }

    @Test
    void findStudentByStudentNumberTest() {
        assertAll(() -> Assertions.assertTrue(Student.isStudentExisting("test_parser",
                "src/test/resources")),
                () -> assertFalse(Student.isStudentExisting("not_exist_student_number",
                        "src/test/resources")));

    }
}