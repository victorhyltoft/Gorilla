package com.example.gorilla;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private String name;
    private Point2D location;
    private int score;
    public Image image;
    public ImageView imageView;

    // Constructor 1
    public Player(String name, Point2D location, Image texture) {
        this.name = name;
        this.location = location;
        this.score = 0;
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


    public void setImage(Image texture) {
        this.image = texture;
        this.imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setX(location.getX()-10);
        imageView.setY(location.getY()-25);
    }


    public ImageView getImageView() {
        return imageView;
    }

    public Bounds getBounds() {
        return imageView.getLayoutBounds();
    }


}
