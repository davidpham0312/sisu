package fi.tuni.prog3.utils;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class UIUtils {
    private UIUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
