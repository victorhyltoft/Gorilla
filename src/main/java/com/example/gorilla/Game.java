package com.example.gorilla;

public class Game {
    private double gravity;
    private double height;
    private double width;
    private int turnCounter;
    private double acceptedRange;
    private static final int noOfPlayers = 2;

    public Game(double gravity, int height, int width) {
        this.gravity = gravity;
        this.height = height;
        this.width = width;
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

    public void setAcceptedRange(double width) {
        this.acceptedRange = width/50;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getGravity() {
        return gravity;
    }

    public double getAcceptedRange() {
        return acceptedRange;
    }
}
