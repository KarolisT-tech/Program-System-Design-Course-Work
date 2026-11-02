package com.example.kursinisgame.controllers;

import com.example.kursinisgame.HelloApplication;
import com.example.kursinisgame.helpers.AlertGenerator;
import com.example.kursinisgame.helpers.GameInputValidator;
import com.example.kursinisgame.helpers.InputValidator;
import com.example.kursinisgame.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetUpController implements Initializable {
    @FXML
    private ComboBox<AssetType> assetTypeCB;
    @FXML
    private TextField startBalanceTF;
    @FXML
    private TextField startAssetPriceTF;
    @FXML
    private Button startGameB;
    @FXML
    private ComboBox<Models> modelCB;

    public void StartGame(ActionEvent actionEvent) throws IOException {

        String validationResult = GameInputValidator.validate(
                startBalanceTF.getText(),
                startAssetPriceTF.getText(),
                modelCB.getValue(),
                assetTypeCB.getValue()
        );

        if (validationResult != null) {
            showValidationError(validationResult);
            return;
        }

        launchGame();
    }

    private void launchGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Parent parent = fxmlLoader.load();

        MainForm mainForm = (MainForm) fxmlLoader.getController();
        setupGameData(mainForm);

        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();

        Stage currentStage = (Stage) startGameB.getScene().getWindow();
        currentStage.close();
    }

    private void setupGameData(MainForm mainForm) {
        Player player = new Player(Double.parseDouble(startBalanceTF.getText()));
        Asset asset = AssetFactory.createAsset(
                Double.parseDouble(startAssetPriceTF.getText()),
                modelCB.getValue(),
                assetTypeCB.getValue()
        );
        mainForm.setData(player, asset);
    }

    private void showValidationError(String error) {
        if ("MISSING_DATA".equals(error)) {
            AlertGenerator.generateAlert(
                    Alert.AlertType.INFORMATION,
                    "Error Starting Game",
                    "Missing Data",
                    "Please Fill In All Fields To Start!"
            );
        } else {
            AlertGenerator.generateAlert(
                    Alert.AlertType.INFORMATION,
                    "Error Starting Game",
                    "Wrong Data",
                    "Please Input Numbers!"
            );
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modelCB.getItems().addAll(Models.values());
        assetTypeCB.getItems().addAll(AssetType.values());
    }
}
