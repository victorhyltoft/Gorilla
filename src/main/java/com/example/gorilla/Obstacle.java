package com.example.gorilla;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
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
import java.util.concurrent.ThreadLocalRandom;

public class Obstacle {
    private double time;
    private double timeInterval;
    private double currentX;
    private Point2D startPosition;
    private Point2D endPosition;
    private double height;
    private double maxHeight = SettingsController.game.getBuildings().get(1).getMaxHeight();
    private Image image = new Image(getClass().getResourceAsStream("textures/Bird.png"));
    private ImageView imageView;

    public Obstacle() {
        this.imageView = new ImageView(image);
        this.time = 0.0;
        this.height = ThreadLocalRandom.current().nextDouble(maxHeight+100, maxHeight+300);
        determinePosition();
        imageView.setScaleX(0.1);
        imageView.setScaleY(0.1);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void determinePosition() {
        double random = ThreadLocalRandom.current().nextDouble(0,1);
        if (random == 0) {
            startPosition = new Point2D(-200,height);
            endPosition = new Point2D(SettingsController.game.getWidth()+200,height);
        }
        else if (random == 1) {
            startPosition = new Point2D(SettingsController.game.getWidth()+200,height);
            endPosition = new Point2D(-200,height);
        }
    }


    public void animatePath() {
        PathTransition transition = new PathTransition();
        Line line = new Line(-100, 100, 1380, 100);
        transition.setNode(imageView);
        transition.setDuration(Duration.seconds(10));
        transition.setPath(line);
        transition.setCycleCount(1);
        transition.play();
    }






}
