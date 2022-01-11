package com.example.gorilla;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class Player {
    private String name;
    private Point2D location;
    private int score;
    private Circle circle;
//    public Image image;

    // Constructor 1
    public Player(String name, Point2D location) {
        this.name = name;
        this.location = location;
        this.score = 0;
        this.circle = new Circle(location.getX(), location.getY(), SettingsController.game.getAcceptedRange());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void incrementScore() {
        this.score += 1;
    }

    public String getName() {
        return name;
    }

    public Point2D getLocation() {
        return location;
    }

    public int getScore() {
        return score;
    }

    public Circle getCircle() {
        return new Circle(location.getX(), location.getY(), SettingsController.game.getAcceptedRange());
    }


}
