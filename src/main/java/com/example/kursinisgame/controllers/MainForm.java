package com.example.kursinisgame.controllers;

import com.example.kursinisgame.HelloApplication;
import com.example.kursinisgame.helpers.AlertGenerator;
import com.example.kursinisgame.models.Asset;
import com.example.kursinisgame.models.Player;
import com.example.kursinisgame.models.TradingGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Math.round;

public class MainForm {

    @FXML
    private TextField myAssetsTF;
    @FXML
    private TextField assetPriceTf;
    @FXML
    private TextField assetPriceTaxTF;
    @FXML
    private Button buyB;
    @FXML
    private Button holdB;
    @FXML
    private Button sellB;
    @FXML
    private TextField numberOfMovesTF;
    @FXML
    private Button stopGameB;
    @FXML
    private TextField myBalanceTF;
    @FXML
    private TextField smaTF;
    @FXML
    private TextField taxTF;

    private Player player;
    private Asset asset;
    private TradingGame game;

    public void setData(Player player, Asset asset) {
        this.player = player;
        this.asset = asset;
        this.game = new TradingGame(player);

        disableFields();
        fillFields();
    }

    public void fillFields(){
        myBalanceTF.setText(String.valueOf(Math.round(player.getBalance() * 100.00) / 100.00));
        myAssetsTF.setText(String.valueOf(player.getAssetQnt()));
        assetPriceTf.setText(String.valueOf(Math.round(asset.getAssetPrice() * 100.00) / 100.00));
        assetPriceTaxTF.setText(String.valueOf(Math.round(asset.getAssetPriceWithTax() * 100.00) / 100.00));
        numberOfMovesTF.setText(String.valueOf(player.getMovesLeft()));
        smaTF.setText(String.valueOf(asset.getSMA()));
        taxTF.setText(String.valueOf(asset.getAssetTax() * 100.00) + "%");
    }

    public void disableFields(){
        myBalanceTF.setDisable(true);
        myAssetsTF.setDisable(true);
        assetPriceTf.setDisable(true);
        assetPriceTaxTF.setDisable(true);
        numberOfMovesTF.setDisable(true);
        smaTF.setDisable(true);
        taxTF.setDisable(true);
    }

    public void Buy(ActionEvent actionEvent) throws IOException {
        if(player.getBalance() < asset.getAssetPriceWithTax()) {
            AlertGenerator.generateAlert(Alert.AlertType.INFORMATION, "Uh oh!", "Seems like you dont have enough money!", "Sell assests or hold!");
            return;
        }
        player.setBalance(player.getBalance() - asset.getAssetPrice());
        player.setAssetQnt(player.getAssetQnt() + 1);
        afterMoveEvent();
    }

    public void Hold(ActionEvent actionEvent) throws IOException {
        afterMoveEvent();
    }

    public void Sell(ActionEvent actionEvent) throws IOException {

        if(player.getAssetQnt() < 1) {
            AlertGenerator.generateAlert(Alert.AlertType.INFORMATION, "Uh oh!", "Seems like you dont any assets!", "Buy assets or hold!");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("assetqntselect-view.fxml"));
        Parent parent = fxmlLoader.load();

        AssetSellerForm assetSellerForm = (AssetSellerForm) fxmlLoader.getController();
        assetSellerForm.setData(player, asset, this, game);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void FinishGame() throws IOException {
        if (game.isLost()) {
            showWindow(createLoader("lost-view.fxml").load());
        } else {
            FXMLLoader loader = createLoader("won-view.fxml");
            Parent root = loader.load();
            ((WonController) loader.getController()).setData(player, asset);
            showWindow(root);
        }
    }

    private FXMLLoader createLoader(String fxmlName) {
        return new FXMLLoader(HelloApplication.class.getResource(fxmlName));
    }

    private void showWindow(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    void afterMoveEvent() throws IOException {
        player.setMovesLeft(player.getMovesLeft() - 1);
        asset.changePrice();
        fillFields();

        if(game.isGameEnd()) FinishGame();
    }
}
