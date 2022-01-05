package com.example.gorilla;

import com.example.gorilla.Models.Player;
import com.example.gorilla.Models.Projectile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class GameController {
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField gravityField;
    private Point2D throwPosition;
    private int velocity;
    private int angle;
    private Stage stage;
    private Scene scene;

    public int coinFlip() {
        Random random = new Random();
        return random.nextInt(2);
    }

    public void bananaThrow() {
        velocity = Integer.parseInt(velocityField.getText());
        angle = Integer.parseInt(angleField.getText());
        int turn = coinFlip();
        if (angle > 90) {
            angle = 90;
        }
        else if (angle < 0) {
            angle = 0;
        }
        if (turn == 0) {
            throwPosition = new Point2D(0,300); //placeholder numbers for now, set to center of gorilla
        }
        else {
            throwPosition = new Point2D(400,300);
        }

        // Initialize objects

        Projectile projectile = new Projectile(throwPosition,angle,velocity, stage.getHeight());

        // Trajectory Group object
        Group trajectory = Trajectory.trajectoryToGroup(projectile);
    }



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

    public void startGame(ActionEvent event) throws IOException {
        System.out.println(getWidth()+"x"+getHeight());
        System.out.println(getGravity());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("baseLevel.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,getWidth(),getHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public void switchToPlayGameScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playGameScreen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playGameScreen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}