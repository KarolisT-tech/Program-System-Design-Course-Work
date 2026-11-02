package com.example.kursinisgame.controllers;

import com.example.kursinisgame.helpers.AlertGenerator;
import com.example.kursinisgame.helpers.InputValidator;
import com.example.kursinisgame.helpers.SellInputError;
import com.example.kursinisgame.models.Asset;
import com.example.kursinisgame.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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

    public void setData(Player player, Asset asset, MainForm mainForm) {
        this.player = player;
        this.asset = asset;
        this.main = mainForm;
    }

    @FXML
    private void sell() throws IOException {
        String input = assetQntTF.getText();
        Optional<SellInputError> error = InputValidator.validateSellQuantity(input, player.getAssetQnt());
        if (error.isPresent()) {
            showError(error.get());
            return;
        }
        player.sell(Integer.parseInt(input), asset.getAssetPrice(), asset.getAssetTax());
        closeWindow(confirmB);
        main.afterMoveEvent();
    }

    @FXML
    private void cancel() {
        closeWindow(cancelB);
    }

    private static void showError(SellInputError error) {
        AlertGenerator.generateAlert(error.getAlertType(), AlertGenerator.DEFAULT_TITLE,
                error.getHeader(), error.getMessage());
    }

    private static void closeWindow(Button source) {
        ((Stage) source.getScene().getWindow()).close();
    }
}
