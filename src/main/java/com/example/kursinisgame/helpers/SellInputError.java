package com.example.kursinisgame.helpers;

import javafx.scene.control.Alert;

public enum SellInputError {
    EMPTY(Alert.AlertType.ERROR, "No data provided!", "Fill in required field!"),
    NOT_A_NUMBER(Alert.AlertType.ERROR, "Wrong data!", "Input a number!"),
    NOT_ENOUGH_ASSETS(Alert.AlertType.INFORMATION, "Seems like you dont have enough!", "Sell less!");

    private final Alert.AlertType alertType;
    private final String header;
    private final String message;

    SellInputError(Alert.AlertType alertType, String header, String message) {
        this.alertType = alertType;
        this.header = header;
        this.message = message;
    }

    public Alert.AlertType getAlertType() {
        return alertType;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }
}
