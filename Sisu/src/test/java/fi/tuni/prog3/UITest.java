package fi.tuni.prog3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.base.NodeMatchers;

import javafx.scene.input.KeyCode;

import static org.testfx.api.FxAssert.verifyThat;

import java.util.Calendar;

public class UITest {
    private org.testfx.matcher.control.LabeledMatchers LabeledMatchers;
    private String testStudentID = "TESTING_" + Calendar.getInstance().getTimeInMillis();

    @BeforeEach
    public void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(App.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.cleanupStages();
    }

    private void register(FxRobot robot) {
        robot.clickOn("#registerTabButton");
        robot.clickOn("#firstNameField").write("First");
        robot.clickOn("#lastNameField").write("Last");
        robot.clickOn("#studentNumberSignUpField").write(testStudentID);
        robot.clickOn("#passwordSignUpField").write("password");
        robot.clickOn("#passwordConfirmField").write("password");
        robot.clickOn("#startYearComboBox").type(KeyCode.DOWN, 5).type(KeyCode.ENTER);
        robot.clickOn("#endYearComboBox").type(KeyCode.DOWN, 2).type(KeyCode.ENTER);
        robot.clickOn("#signUpButton");
    }

    @Test
    public void shouldHaveLoginButton() {
        verifyThat("#logInButton", LabeledMatchers.hasText("Log In"));
    }

    @Test
    public void canRegisterAndLogout() {
        FxRobot robot = new FxRobot();

        register(robot);

        verifyThat("Choose degree program", NodeMatchers.isVisible());
        verifyThat("--/--/--  -  --/--/100%", NodeMatchers.isVisible());
        verifyThat("Student: First Last (Student number " + testStudentID + "). Academic years: 2018 - 2021.",
                NodeMatchers.isVisible());

        robot.clickOn("#logoutButton");

        verifyThat("Student login", NodeMatchers.isVisible());
        verifyThat("Register", NodeMatchers.isVisible());
    }

    @Test
    public void showErrorPromptWhenLoginWithoutAccount() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#logInButton");
        verifyThat("Please enter your student number!", NodeMatchers.isVisible());
        verifyThat("OK", NodeMatchers.isVisible());
    }

}
