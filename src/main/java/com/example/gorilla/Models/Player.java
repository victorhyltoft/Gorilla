package com.example.gorilla.Models;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Victor Hyltoft & Mikkel Allermand
 */
public class Player {
    private String name;
    private Point2D location;
    private int score;
    public Image image;
    public ImageView imageView;
    private Boolean winner;

    // Constructor 1
    public Player(String name, Point2D location, Image texture) {
        this.name = name;
        this.location = location;
        this.score = 0;
        setImage(texture);
    }


    /**
     * Increments the players score, called when projectile hits opponent.
     */
    public void incrementScore() {
        this.score += 1;
    }

    /**
     * Sets this players winner field to true when called.
     */
    public void isWinner() {
        winner = true;
    }
    // GETTERS
    public String getName() {
        return name;
    }

    public Point2D getLocation() {
        return location;
    }

    public int getScore() {
        return score;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Bounds getBounds() {
        return imageView.getLayoutBounds();
    }

    public Point2D getLocationCenter() {
        return new Point2D(location.getX()+(imageView.getFitWidth()/2),location.getY()+(imageView.getFitHeight()/2));
    }
    // SETTERS
    public void setLocation(Point2D location) {
        this.location = location;
        this.imageView.setX(location.getX());
        this.imageView.setY(location.getY());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public void setImage(Image texture) {
        this.image = texture;
        this.imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setX(location.getX());
        imageView.setY(location.getY());
    }



}
