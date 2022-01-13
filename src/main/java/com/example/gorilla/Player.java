package com.example.gorilla;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.FileInputStream;
import java.util.AbstractList;

public class Player {
    private String name;
    private Point2D location;
    private int score;
    private Circle circle;
    public Image image;
    public ImageView imageView;

    // Constructor 1
    public Player(String name, Point2D location, Image texture) {
        this.name = name;
        this.location = location;
        this.score = 0;
        this.circle = new Circle(location.getX(), location.getY(), SettingsController.game.getAcceptedRange());
        setImage(texture);
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
        return circle;
    }

    public void setImage(Image texture) {
        this.image = texture;
        this.imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setX(location.getX()-10);
        imageView.setY(location.getY()-25);
    }

    /**
     * @deprecated
     * @return
     */
    public Circle getNewCircle() {
        return new Circle(location.getX(), location.getY(), SettingsController.game.getAcceptedRange());
    }

    public ImageView getImageView() {
        System.out.println(image.getUrl());
        return imageView;
    }


}
