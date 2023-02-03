package fi.tuni.prog3.controller;

import fi.tuni.prog3.App;
import fi.tuni.prog3.model.Student;
import fi.tuni.prog3.utils.AlertTitle;
import fi.tuni.prog3.utils.JSONUtils;
import fi.tuni.prog3.utils.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.stream.IntStream;

import static fi.tuni.prog3.utils.UIUtils.showAlert;

public class SignUpController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField studentNumberSignUpField;
    @FXML
    private ComboBox<Integer> startYearComboBox;
    @FXML
    private ComboBox<Integer> endYearComboBox;
    @FXML
    private PasswordField passwordSignUpField;
    @FXML
    private PasswordField passwordConfirmField;

    public void initialize() {
        buildStudentDialog();
    }

    private void buildStudentDialog() {
        buildStartYearComboBox();
        buildEndYearComboBox();
    }

    @FXML
    private void signUp() throws IOException {
        if (!checkSignUpFormValidity()) {
            return;
        }

        if (!checkStudentNumberFromDatabase(studentNumberSignUpField.getText())) {
            return;
        }

        createStudentInformation();
        App.setRoot("degreeWindowView");
    }

    private boolean checkSignUpFormValidity() {
        if (firstNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.FORM_ERROR, "Please enter your first name!");
            return false;
        }

        if (lastNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.FORM_ERROR, "Please enter your last name!");
            return false;
        }

        if (studentNumberSignUpField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.FORM_ERROR, "Please enter your student number!");
            return false;
        }

        Object startYear = startYearComboBox.getSelectionModel().getSelectedItem();
        Object endYear = endYearComboBox.getSelectionModel().getSelectedItem();
        if (startYear == null) {
            showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.SIGNUP_ERROR, "Please enter your start year!");
            return false;
        } else if (endYear == null) {
            showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.SIGNUP_ERROR, "Please enter your end year!");
            return false;
        } else {
            if ((int) startYear > (int) endYear) {
                showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                        AlertTitle.SIGNUP_ERROR, "End year cannot be before start year!");
                return false;
            }
        }

        if (passwordSignUpField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.SIGNUP_ERROR, "Please enter a new password!");
            return false;
        } else if (passwordConfirmField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.SIGNUP_ERROR, "Please confirm new password!");
            return false;
        } else {
            if (!passwordSignUpField.getText().equals(passwordConfirmField.getText())) {
                showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                        AlertTitle.SIGNUP_ERROR, "Passwords didn't match. Try again.\n");
                return false;
            }
        }

        return true;
    }

    private boolean checkStudentNumberFromDatabase(String studentNumber) {

        if (Student.isStudentExisting(studentNumber, "./saved_information/saved_student")) {
            showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.SIGNUP_ERROR, "This student number already has an account!");
            return false;
        }

        return true;
    }

    private void createStudentInformation() {
        App.activeStudent = new Student(firstNameField.getText(),
                lastNameField.getText(),
                studentNumberSignUpField.getText(),
                startYearComboBox.getValue(),
                endYearComboBox.getValue(),
                passwordSignUpField.getText());
        try {
            JSONUtils.saveToJSONFile(App.activeStudent,
                    "./saved_information/saved_student/" +
                            App.activeStudent.getStudentNumber() + "_student.json");
        } catch (Exception e) {
            UIUtils.showAlert(AlertType.ERROR, App.scene.getWindow(), AlertTitle.SIGNUP_ERROR, e.getMessage());
        }
    }

    private void buildStartYearComboBox() {
        startYearComboBox.getItems().addAll(yearGenerator());
    }

    private void buildEndYearComboBox() {
        endYearComboBox.getItems().addAll(yearGenerator());
    }

    private Integer[] yearGenerator() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        final int FOUNDATION_YEAR = 1925;
        Integer[] yearsList = IntStream.range(FOUNDATION_YEAR, currentYear + 1).boxed().toArray(Integer[]::new);
        Collections.reverse(Arrays.asList(yearsList));
        return yearsList;
    }
}
