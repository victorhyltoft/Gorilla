package com.example.gorilla;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Obstacle {
    private ImageView obstacle;
    private double time;
    private double timeInterval;
    private double currentX;
    private double VELOCITY = 5;
    public Obstacle(ImageView obstacle) {
        this.obstacle = obstacle;
        this.time = 0.0;
        obstacle.setScaleX(0.1);
        obstacle.setScaleY(0.1);
    }

    public ImageView getImageView() {
        return obstacle;
    }



    public void animatePath() {
        PathTransition transition = new PathTransition();
        Line line = new Line(-100, 100, 1380, 100);
        transition.setNode(obstacle);
        transition.setDuration(Duration.seconds(10));
        transition.setPath(line);
        transition.setCycleCount(1);
        transition.play();
    }






}
