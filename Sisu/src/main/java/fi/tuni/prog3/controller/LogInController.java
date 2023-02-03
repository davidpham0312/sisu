package fi.tuni.prog3.controller;

import fi.tuni.prog3.App;
import fi.tuni.prog3.parser.StudentParser;
import fi.tuni.prog3.model.Student;
import fi.tuni.prog3.utils.AlertTitle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

import java.io.IOException;

import static fi.tuni.prog3.utils.UIUtils.showAlert;

public class LogInController {
    @FXML
    private GridPane logInGridPane;
    @FXML
    private TextField studentNumberLoginField;
    @FXML
    private PasswordField passwordLoginField;
    @FXML
    private Button logInButton;

    @FXML
    private void logIn() throws IOException {
        if (!checkLoginFormValidity()) {
            return;
        }

        App.activeStudent = StudentParser.fromJSON(studentNumberLoginField.getText(),
                "./saved_information/saved_student");

        if (!checkStudentAccountFromDatabase(App.activeStudent)) {
            return;
        }
        App.setRoot("degreeWindowView");
    }

    private boolean checkStudentAccountFromDatabase(Student logInStudent) {
        Window currentWindow = logInGridPane.getScene().getWindow();
        if (logInStudent == null) {
            showAlert(Alert.AlertType.ERROR, currentWindow,
                    AlertTitle.LOGIN_ERROR, "No student with given student number found!");
            return false;
        } else {
            if (!logInStudent.verifyPassword(passwordLoginField.getText())) {
                showAlert(Alert.AlertType.ERROR, currentWindow,
                        AlertTitle.LOGIN_ERROR, "Wrong password!");
                return false;
            }
        }

        return true;
    }

    private boolean checkLoginFormValidity() {
        Window currentWindow = logInGridPane.getScene().getWindow();

        if (studentNumberLoginField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, currentWindow,
                    AlertTitle.LOGIN_ERROR, "Please enter your student number!");
            return false;
        }

        if (passwordLoginField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, currentWindow,
                    AlertTitle.LOGIN_ERROR, "Please enter your password!");
            return false;
        }

        return true;
    }
}
