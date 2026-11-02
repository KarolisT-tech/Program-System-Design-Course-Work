package com.example.kursinisgame.helpers;

import javafx.scene.control.Alert;

public class AlertGenerator {

    public static final String DEFAULT_TITLE = "Uh oh!";

    public static void generateAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
