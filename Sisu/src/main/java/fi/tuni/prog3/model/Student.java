package fi.tuni.prog3.model;

import java.io.File;

import fi.tuni.prog3.utils.JSONUtils;

public class Student extends User {
    private String studentNumber;
    private int startYear;
    private int endYear;

    public Student(String firstName,
            String lastName,
            String studentNumber,
            int startYear,
            int endYear,
            String password) {
        super(firstName, lastName, password);
        this.studentNumber = studentNumber;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public static boolean isStudentExisting(String studentNumber, String studentInfoPath) {
        File studentInfoFolder = new File(studentInfoPath);
        String loadFileName = studentInfoFolder + "/" + studentNumber + "_student.json";
        return JSONUtils.isFileExisting(loadFileName);
    }
}
