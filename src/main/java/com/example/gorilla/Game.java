package com.example.gorilla;

import javafx.scene.Parent;

import java.util.ArrayList;

public class Game {
    private double gravity;
    private double height;
    private double width;
    private int turnCounter = 0;
    private double acceptedRange;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private Parent root;

    public void nextPlayer() {
        turnCounter = (turnCounter + 1) % players.size();
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
        return players.get(turnCounter).getName();
    }

    public Player getCurrentPlayer() {
        return players.get(turnCounter);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Parent getRoot() {
        return root;
    }
}
