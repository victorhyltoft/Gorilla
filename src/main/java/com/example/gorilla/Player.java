package com.example.gorilla;

import javafx.geometry.Point2D;

public class Player {
    public String name;
    public Point2D location;
    public int score;

    // Constructor 1
    public Player(String name, Point2D location) {
        this.name = name;
        this.location = location;
        this.score = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void incrementScore() {
        this.score += 1;
    }


}
