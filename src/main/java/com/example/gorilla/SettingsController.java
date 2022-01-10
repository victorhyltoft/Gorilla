package com.example.gorilla;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SettingsController {

    public static Game game = new Game(0,0,0);
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
    private Label UseNumbersPlz;

    /**
     * Switches to the screen allowing players to customize their player
     * Here the name of the player and the texture is set
     */
    public void switchToPlayerCreator(ActionEvent event) throws IOException {
        if (gameSettings() == true){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("player-creator.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public boolean gameSettings() {
        boolean CheckForNumbers  = false;
        try {
            game.setWidth(Integer.parseInt(widthField.getText()));
            game.setHeight(Integer.parseInt(heightField.getText()));
            game.setGravity(Double.parseDouble(gravityField.getText()));
            game.setAcceptedRange(game.getWidth());
            CheckForNumbers = true;
            return CheckForNumbers;
        } catch (NumberFormatException e) {
            System.out.println("only numbers");
            UseNumbersPlz.setText("enter only numbers plz");
            UseNumbersPlz.setTextAlignment(TextAlignment.CENTER);
            return CheckForNumbers;
        }

    } }
