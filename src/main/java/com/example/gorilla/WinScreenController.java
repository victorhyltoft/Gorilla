package com.example.gorilla;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class WinScreenController implements Initializable {

    @FXML ImageView winnerTexture;

    @FXML Label WinnerLabel;

    /**
     * Switches to the settings.
     * Here the width, height and gravity is set.
     */
    public void switchToSettingsScreen(ActionEvent event) throws IOException {
        GameController.resetScore();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("settings.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(GameController.player1.getScore() == 3) {
            winnerTexture.setImage(GameController.player1.getImageView().getImage());
            WinnerLabel.setText(GameController.player1.getName() + " has won");

        } else {
            winnerTexture.setImage(GameController.player2.getImageView().getImage());
            WinnerLabel.setText(GameController.player2.getName() + " has won");
        }
    }

}