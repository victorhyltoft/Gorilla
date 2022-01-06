package com.example.gorilla.Models;

import javafx.geometry.Point2D;

public class Player {
    public String name;
    public Point2D location;
    public int score = 0;

    // Constructor 1
    public Player(String name, Point2D location) {
        this.name = name;
        this.location = location;
        this.score = 0;
    }

    public void incrementScore() {
        this.score += 1;
    }
}
