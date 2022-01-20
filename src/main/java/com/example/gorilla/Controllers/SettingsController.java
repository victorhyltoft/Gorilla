package com.example.gorilla.Controllers;

import com.example.gorilla.Game;
import com.example.gorilla.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SettingsController {
    /**
     * @author Viktor Egesby
     */

    public static Game game;
    private Stage stage;
    private Scene scene;

    // FXML FIELDS
    @FXML
    private TextField heightField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField gravityField;
    @FXML
    private Label errorText;

    public SettingsController() {
        game = new Game();
    }

    /**
     * Switches to the screen allowing players to customize their player
     * Here the name of the player and the texture is set
     */
    public void switchToPlayerCreator(ActionEvent event) throws IOException {
        if (validateTextFields()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("views/player-creator.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public boolean validateTextFields() {
        try {
            if (Integer.parseInt(widthField.getText()) < 300 || Integer.parseInt(heightField.getText()) < 300) {
                errorText.setText("minimum size is 300");
                return false;
            }
            game.setWidth(Integer.parseInt(widthField.getText()));
            game.setHeight(Integer.parseInt(heightField.getText()));
            game.setGravity(Double.parseDouble(gravityField.getText()));
            game.setAcceptedRange(game.getWidth());
            return true;
        } catch (NumberFormatException e) {
            errorText.setText("Only numbers allowed");

            return false;
        }

    } }
