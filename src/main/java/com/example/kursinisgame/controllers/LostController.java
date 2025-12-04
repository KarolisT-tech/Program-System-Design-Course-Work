package com.example.kursinisgame.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LostController {

    @FXML
    private Button endGameB;

    public void closeAll(ActionEvent actionEvent) {

        Stage thisStage = (Stage) endGameB.getScene().getWindow();
        thisStage.close();

        Platform.exit();
        System.exit(0);
    }
}
