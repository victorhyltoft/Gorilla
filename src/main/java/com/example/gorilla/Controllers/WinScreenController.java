package com.example.gorilla.Controllers;

import com.example.gorilla.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WinScreenController {

    @FXML ImageView winnerTexture;

    @FXML Label WinnerLabel;

    /**
     * Switches to the settings.
     * Here the width, height and gravity is set.
     */
    public void switchToSettingsScreen(ActionEvent event) throws IOException {
        GameController.resetScore();
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("views/settings.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit() {
        System.exit(0);
    }


}