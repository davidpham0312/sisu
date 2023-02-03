package fi.tuni.prog3.utils;

public class AlertTitle {
    private AlertTitle() {
        throw new IllegalStateException("Static class");
    }

    public static final String LOGIN_ERROR = "Login error";
    public static final String SIGNUP_ERROR = "Sign up error";
    public static final String FORM_ERROR = "Form error";
    public static final String PARSE_ERROR = "Parse error";
    public static final String LOADING_ERROR = "Loading error";
    public static final String SAVE_SUCCESS = "Saved success";
    public static final String SAVE_ERROR = "Saving error";
}
