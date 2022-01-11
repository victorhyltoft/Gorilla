package com.example.gorilla;

public class Game {
    private double gravity;
    private double height;
    private double width;
    private int turnCounter = 1;
    private double acceptedRange;
    private static final int noOfPlayers = 2;

    public void nextPlayer() {
        turnCounter = (turnCounter + 1) % noOfPlayers;
    }

    public int getCurrentTurn() {
        return turnCounter;
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

    public String getCurrentPlayerName() {
        if (getCurrentTurn() == 1) {
            return GameController.player1.getName();
        }
        else {
            return GameController.player2.getName();
        }
    }

    public Player getCurrentPlayer() {
        if (getCurrentTurn() == 1) {
            return GameController.player1;
        }
        else {
            return GameController.player2;
        }
    }
}
