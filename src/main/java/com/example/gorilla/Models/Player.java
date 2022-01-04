package com.example.gorilla.Models;

import javafx.geometry.Point2D;

public class Player {
    public String name;
    public Point2D location;
    public int wins;
    public String texture;

    // Constructor 1 (Multiple parameters)
    public Player(String name, Point2D location, String texture) {
        this.name = name;
        this.location = location;
        this.texture = texture;
        this.wins = 0;
    }

    // Constructor 2
    public Player(String name, Point2D location) {
        this.name = name;
        this.location = location;
        this.wins = 0;
        this.texture = "default";
    }



    // Winning animation
    public void playWinAnimation() {
        System.out.println(name + " has won the round");
        // TODO
    }
}
