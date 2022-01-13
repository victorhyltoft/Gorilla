package com.example.gorilla;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public Parent root;
    public Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the game
     */
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-menu.fxml")));
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Gorillas");
        stage.show();
    }
}
