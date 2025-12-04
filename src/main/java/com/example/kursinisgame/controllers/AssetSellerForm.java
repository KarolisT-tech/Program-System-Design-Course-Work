package com.example.kursinisgame.controllers;

import com.example.kursinisgame.helpers.AlertGenerator;
import com.example.kursinisgame.helpers.InputValidator;
import com.example.kursinisgame.models.Asset;
import com.example.kursinisgame.models.Player;
import com.example.kursinisgame.models.TradingGame;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AssetSellerForm {
    @FXML
    private TextField assetQntTF;
    @FXML
    private Button confirmB;
    @FXML
    private Button cancelB;

    private Player player;
    private Asset asset;
    private MainForm main;
    private TradingGame game;

    public void setData(Player player, Asset asset, MainForm mainForm, TradingGame game) {
        this.player = player;
        this.asset = asset;
        this.main = mainForm;
        this.game = game;
    }

    public void Sell(ActionEvent actionEvent) throws IOException {
        if(assetQntTF.getText().isEmpty()){
            AlertGenerator.generateAlert(Alert.AlertType.ERROR, "Uh oh!", "No data provided!", "Fill in required field!");
            return;
        }
        if(!InputValidator.isInteger(assetQntTF.getText())){
            AlertGenerator.generateAlert(Alert.AlertType.ERROR, "Uh oh!", "Wrong data!", "Input a number!");
            return;
        }
        if(Integer.parseInt(assetQntTF.getText()) > player.getAssetQnt()){
            AlertGenerator.generateAlert(Alert.AlertType.INFORMATION, "Uh oh!", "Seems like you dont have enough!", "Sell less!");
            return;
        };
        double totalProfit = Integer.parseInt(assetQntTF.getText()) * asset.getAssetPrice();
        player.setAssetQnt(player.getAssetQnt() - Integer.parseInt(assetQntTF.getText()));
        player.setBalance(player.getBalance() + totalProfit - (totalProfit * asset.getAssetTax()));
        player.setMovesLeft(player.getMovesLeft() - 1);

        Stage stage = (Stage) confirmB.getScene().getWindow();
        stage.close();

        asset.changePrice();
        main.fillFields();

        if(game.isGameEnd()) main.FinishGame();
    }

    public void Cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelB.getScene().getWindow();
        stage.close();
    }
}
