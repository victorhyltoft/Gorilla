package com.example.gorilla;

import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

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
    private int[] score;
    private final int SCORE = 5;

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


    public boolean isWon() {
        for (Player player : players) {
            if (player.getScore() == SCORE) {
                player.isWinner();
                return true;
            }
        }
        return false;
    }


    public void createBuildings() {
        double totalBuildingWidth = 0;
        while (totalBuildingWidth < getWidth()) {
            Building b = new Building(totalBuildingWidth);
            buildings.add(b);
            totalBuildingWidth += b.getWidth();
        }
        setBuildings(buildings);

    }

    public void regenerateMap() {
        System.out.println("Should regenerate map");

        // Remove all previous buildings
        for (Building building : getBuildings()) {
            ((AnchorPane) root).getChildren().removeAll(building.getBuildingShape());
            ((AnchorPane) root).getChildren().removeAll(building.getWindows());
        }
        getBuildings().clear();

        // Create new buildings and display them
        createBuildings();
        for (Building building : getBuildings()) {
            ((AnchorPane) root).getChildren().addAll(building.getBuildingShape());
            ((AnchorPane) root).getChildren().addAll(building.getWindows());

        }
        players.get(0).getImageView().toFront();
        players.get(1).getImageView().toFront();
        GameController.scoreText.toFront();


        // Update the player locations
        players.get(0).setLocation(generatePlayerLocation(0));
        players.get(1).setLocation(generatePlayerLocation(1));


    }

    public void createPlayers(String name, Image image) {
        System.out.println(image);
        Point2D location = generatePlayerLocation(players.size());
        addPlayer(new Player(name, location, image));
    }


    /**
     * Generates the location for the players
     * @param playerIdx the idx of the player in the player arraylist
     */
    public Point2D generatePlayerLocation(int playerIdx) {
        int xBuffer = 22;
        int yBuffer = 52;
        // Using a ternary expression to determine where to place to players
        // 1st player will be placed on the second building's roof, the 2nd on the second last's roof
        double x = getBuildings().get(playerIdx == 0 ? 1 : buildings.size() - 2).getBuildingRoof().getX()-xBuffer;
        double y = getBuildings().get(playerIdx == 0 ? 1 : buildings.size() - 2).getBuildingRoof().getY()-yBuffer;
        return new Point2D(x, y);

    }

}
