package com.example.kursinisgame.controllers;

import com.example.kursinisgame.HelloApplication;
import com.example.kursinisgame.helpers.AlertGenerator;
import com.example.kursinisgame.helpers.InputValidator;
import com.example.kursinisgame.models.Asset;
import com.example.kursinisgame.models.Models;
import com.example.kursinisgame.models.Player;
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
    public TextField startBalanceTF;
    @FXML
    public TextField startAssetPriceTF;
    @FXML
    public Button startGameB;
    @FXML
    public ComboBox<Models> modelCB;

    public void StartGame(ActionEvent actionEvent) throws IOException {

        if(startBalanceTF.getText().isBlank() || startAssetPriceTF.getText().isBlank() || modelCB.getSelectionModel().getSelectedItem() == null) {
            AlertGenerator.generateAlert(Alert.AlertType.INFORMATION, "Error Starting Game", "Missing Data", "Please Fill In All Fields To Start!");
            return;
        }
        if(!InputValidator.isDouble(startBalanceTF.getText()) || !InputValidator.isDouble(startAssetPriceTF.getText())) {
            AlertGenerator.generateAlert(Alert.AlertType.INFORMATION, "Error Starting Game", "Wrong Data", "Please Input Numbers!");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Parent parent = fxmlLoader.load();

        Player player = new Player(Double.parseDouble(startBalanceTF.getText()));
        Asset asset = new Asset(Double.parseDouble(startAssetPriceTF.getText()), modelCB.getValue());

        MainForm mainForm = (MainForm) fxmlLoader.getController();
        mainForm.setData(player, asset);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) startGameB.getScene().getWindow();
        currentStage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modelCB.getItems().addAll(Models.values());
    }
}
