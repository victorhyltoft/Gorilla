package com.example.gorilla;

import com.example.gorilla.Models.Projectile;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.TextField;

import java.util.Random;

public class GameController {
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    private Point2D throwPosition;
    private int velocity;
    private int angle;

    public int coinFlip() {
        Random random = new Random();
        return random.nextInt(1);
    }

    public void bananaThrow() {
        Trajectory trajectory = new Trajectory();
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
        Projectile banana = new Projectile(throwPosition, angle, velocity);
        trajectory.trajectoryToGroup(banana);

    }


}