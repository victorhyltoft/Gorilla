package com.example.gorilla;

public class Game {
    private double gravity;
    private int height;
    private int width;
    private int turnCounter;
    private double acceptedRange;
    private static final int noOfPlayers = 2;
    public Game() {
        gravity = 0;
        height = 0;
        width = 0;
    }

    public Game(double gravity, int height, int width) {
        this.gravity = gravity;
        this.height = height;
        this.width = width;
        this.acceptedRange = width/50;
    }

    public void setCurrentPlayer() {
        turnCounter += 1;
    }

    public int getCurrentPlayer() {
        return turnCounter % noOfPlayers;
    }

    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    public void setGravity(double newGravity) {
        this.gravity = newGravity;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double getGravity() {
        return gravity;
    }

    public double getAcceptedRange() {
        return acceptedRange;
    }
}
