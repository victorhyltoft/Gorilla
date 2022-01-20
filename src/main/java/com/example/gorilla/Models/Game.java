package com.example.gorilla.Models;

import com.example.gorilla.Controllers.GameController;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**
 * @author Mikkel Allermand & Victor Hyltoft
 */
public class Game {
    private double gravity;
    private double height;
    private double width;
    private int turnCounter = 0;
    private double acceptedRange;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private Parent root;
    private final int TARGET_SCORE = 3;
    private Player winner;

    /**
     * Calculates whose turn it is.
     */
    public void nextPlayer() {
        turnCounter = (turnCounter + 1) % players.size();
    }

    // GETTERS
    public Parent getRoot() {
        return root;
    }

    public int getCurrentTurn() {
        return turnCounter;
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

    public String getCurrentPlayerName() {
        return players.get(turnCounter).getName();
    }

    public Player getCurrentPlayer() {
        return players.get(turnCounter);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    public ArrayList<Building> getBuildings() {
        return buildings;
    }
    // SETTERS
    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void setRoot(Parent root) {
        this.root = root;
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

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Check if any of the players have won by hitting the target score
     * @return true if a player has won
     */
    public boolean isWon() {
        for (Player player : players) {
            if (player.getScore() == TARGET_SCORE) {
                player.isWinner();
                winner = player;

                return true;
            }
        }
        return false;
    }

    public Player getWinner() {
        return winner;
    }

    /**
     * Creates the buildings for the view
     */
    public void createBuildings() {
        double totalBuildingWidth = 0;
        while (totalBuildingWidth < getWidth()) {
            Building b = new Building(totalBuildingWidth);
            buildings.add(b);
            totalBuildingWidth += b.getWidth();
        }
        setBuildings(buildings);

    }

    /**
     * Takes care of all the requirements for regenerating the map;
     * New buildings, updating player position, setting the nodes at the correct layer
     */
    public void regenerateMap() {
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
        // Make sure the buildings are behind the text and players
        players.get(0).getImageView().toFront();
        players.get(1).getImageView().toFront();
        GameController.scoreText.toFront();

        // Update the player locations
        players.get(0).setLocation(generatePlayerLocation(0));
        players.get(1).setLocation(generatePlayerLocation(1));
    }

    public void createPlayers(String name, Image image) {
        Point2D location = generatePlayerLocation(players.size());
        addPlayer(new Player(name, location, image));
    }


    /**
     * Generates the location for the players
     * @param playerIdx the idx of the player in the player arraylist
     */
    public Point2D generatePlayerLocation(int playerIdx) {
        // Buffer to align player textures with the roof
        int xBuffer = 20;
        int yBuffer = 50;
        // Using a ternary expression to determine where to place to players
        // 1st player will be placed on the second building's roof, the 2nd on the second last's roof
        double x = getBuildings().get(playerIdx == 0 ? 1 : buildings.size() - 2).getBuildingRoof().getX()-xBuffer;
        double y = getBuildings().get(playerIdx == 0 ? 1 : buildings.size() - 2).getBuildingRoof().getY()-yBuffer;
        return new Point2D(x, y);

    }


    public void resetAll() {
        // Remove all buildings
        getBuildings().clear();
        players.clear();




    }


}
