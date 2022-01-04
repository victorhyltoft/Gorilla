package com.example.gorilla;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private TextField heightField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField gravityField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void exit(ActionEvent e) {
        System.exit(0);
    }

    public int getWidth() {
        return Integer.parseInt(widthField.getText());
    }

    public int getHeight() {
        return Integer.parseInt(heightField.getText());
    }

    public double getGravity() {
        return Double.parseDouble(gravityField.getText());
    }

    public void startGame(ActionEvent e) {
        System.out.println(getWidth()+"x"+getHeight());
        System.out.println(getGravity());

    }

    public void switchToPlayGameScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("playGameScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
