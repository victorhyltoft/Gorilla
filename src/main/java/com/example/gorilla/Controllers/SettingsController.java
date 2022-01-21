package com.example.gorilla.Controllers;

import com.example.gorilla.Models.Game;
import com.example.gorilla.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Viktor Egesby
 */
public class SettingsController {

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
        // Creating a new game object to store the settings for the game
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

        // maximum is 1920 x 1080 pixel
        // minimum is 800 x 500 pixel
        errorText.setAlignment(Pos.CENTER);
        try {
            if (Integer.parseInt(widthField.getText()) < 800 ){
                errorText.setText("minimum width is 800");
                return false;
            }
            if (Integer.parseInt(widthField.getText()) > 1920 ){
                errorText.setText("maximum width is 1920");
                return false;
            }
            if (Integer.parseInt(heightField.getText()) < 500) {
                errorText.setText("minimum height is 500");
                return false;
            }
            if (Integer.parseInt(heightField.getText()) > 1080 ){
                errorText.setText("maximum height is 1080");
                return false;
            }
            if (Double.parseDouble(gravityField.getText()) <= 0) {
                errorText.setText("Invalid gravity");
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
