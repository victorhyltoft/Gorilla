package com.example.gorilla;

import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Obstacle {
    private final Game gameSettings = SettingsController.game;
    private Point2D startPosition;
    private Point2D endPosition;
    private double height;
    private final double buffer = 50;
    private final double maxHeight = gameSettings.getBuildings().get(1).getMaxHeight() + buffer;
    private Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("textures/Bird.png")));
    private ImageView imageView;

    public Obstacle() {
        this.imageView = new ImageView(image);
        this.height = ThreadLocalRandom.current().nextDouble(buffer, gameSettings.getHeight() - maxHeight);
        imageView.setScaleX(0.1);
        imageView.setScaleY(0.1);
        determinePosition();

    }

    public ImageView getImageView() {
        return imageView;
    }

    private boolean rightSide() {
        return new Random().nextBoolean();
    }

    public void determinePosition() {
        if (rightSide()) {
            startPosition = new Point2D(-200,height);
            endPosition = new Point2D(SettingsController.game.getWidth()+200,height);
            imageView.setScaleX(-0.1);
        }
        else {
            startPosition = new Point2D(SettingsController.game.getWidth()+200,height);
            endPosition = new Point2D(-200,height);
        }
    }


    public void animatePath() {
        PathTransition transition = new PathTransition();
        Line line = new Line(startPosition.getX(), startPosition.getY(), endPosition.getX(), endPosition.getY());
        transition.setNode(imageView);
        transition.setDuration(Duration.seconds(10));
        transition.setPath(line);
        transition.setCycleCount(1);
        transition.play();
    }






}
