package com.example.kursinisgame.controllers;

import com.example.kursinisgame.models.Asset;
import com.example.kursinisgame.models.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WonController {

    @FXML
    public TextField finishBalanceTF;
    @FXML
    public Button endB;

    Player winner;
    Asset assets;

    public void setData(Player winner, Asset assets) {
        this.winner = winner;
        this.assets = assets;

        fillData();
        disableField();
    }

    public void fillData() {
        double profit = assets.getAssetPrice() * winner.getAssetQnt();
        finishBalanceTF.setText(String.valueOf(Math.round((winner.getBalance() + profit - profit * assets.getAssetTax()) * 100.0) / 100.0));
    }

    public void disableField(){
        finishBalanceTF.setDisable(true);
    }

    public void endGame(ActionEvent actionEvent) {
        Stage thisStage = (Stage) endB.getScene().getWindow();
        thisStage.close();

        Platform.exit();
        System.exit(0);
    }
}
