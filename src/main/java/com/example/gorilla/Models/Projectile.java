package com.example.gorilla.Models;

import com.example.gorilla.Controllers.GameController;
import com.example.gorilla.Controllers.PlayerCreatorController;
import com.example.gorilla.Main;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


/**
 * @author Victor Hyltoft
 */

public class Projectile extends AnimationTimer {
    // Root object to be able to add/delete nodes to the view
    private Parent root;

    // Fields for calculation of projectile trajectory
    private double velocity;
    private double angle;
    private double gravity;
    private double initialX;
    private double initialY;
    private double xVelocity;
    private double yVelocity;
    private double timeInterval;
    private double time;
    private double currentX;
    private double currentY;

    // Game field to easily acces the game settings (gravity, height etc.)
    private final Game game;

    private boolean playerHit;

    // Projectile and its trajectory
    private ImageView projectile;
    private Rectangle projectileShape;
    private Path trajectory;
    private final double imageCenterX;
    private final double imageCenterY;

    // Access to manipulating the fields
    private Button player1ThrowButton;
    private TextField angleField;
    private TextField velocityField;


    private ArrayList<Building> buildings;

    // Fields for the explosion and crater
    private ImageView explosion = new ImageView();
    private final double craterRadius = 40;
    private final Circle crater = new Circle(100,100, craterRadius);

    // Constructor
    public Projectile(ImageView projectile, Game game) {
        this.root = game.getRoot();

        this.game = game;
        loadGameSettings(game);

        this.projectile = projectile;
        this.projectile.setScaleX(1);
        this.projectile.setScaleY(1);

        this.projectileShape = new Rectangle();
        this.projectileShape.setHeight(projectile.getImage().getHeight());
        this.projectileShape.setWidth(projectile.getImage().getWidth());

        this.time = 0.0;

        explosion.setVisible(false);
        ((AnchorPane) root).getChildren().addAll(explosion);

        imageCenterX = projectile.getImage().getWidth() / 2;
        imageCenterY = projectile.getImage().getHeight() / 2;
    }


    // The handle is called every frame (which is 60fps)
    @Override
    public void handle(long now) {
        calculateNextPosition();
        updateProjectile();
        if (checkBuildingCollision()) {
            handleBuildingCollision();
        }
        else {
            checkPlayerCollision();
            checkOutsideGame();
            checkObstacleCollision();
        }

    }


    private void calculateNextPosition() {
        // Calculate next point
        time += timeInterval;
        currentX = getX(time);
        currentY = getY(time);
    }

    private void updateProjectile() {
        // Update projectile
        projectile.setX(currentX - imageCenterX);
        projectile.setY(currentY - imageCenterY);
        projectile.setRotate(projectile.getRotate() + 3);

        projectileShape.setX(currentX - imageCenterX);
        projectileShape.setY(currentY - imageCenterY);
        projectileShape.setRotate(projectile.getRotate() + 3);


        // Update trajectory
        trajectory.getElements().addAll(new LineTo(currentX, currentY));
    }

    private void checkOutsideGame() {
        // Check if outside the screen
        if (currentY >= game.getHeight()) {
            throwFinished();
        }

        // Check if outside either horizontal edge
        if (currentX >= game.getWidth() || currentX <= 0) {
            // Remove trajectory
            throwFinished();
        }
    }


    /**
     *
     * @return true if projectile collides with buildings
     */
    private boolean checkBuildingCollision() {

        // Iterate over each building
        for (Building currentBuilding : buildings) {
            // Check collision with building, more information;
            // https://stackoverflow.com/questions/15013913/checking-collision-of-shapes-with-javafx
            Shape buildingIntersect = Shape.intersect(projectileShape, currentBuilding.getBuildingShape());
            if (buildingIntersect.getBoundsInLocal().getWidth() != -1) {
                return true;
            }
        }
        return false;
    }

    private void handleBuildingCollision() {
        // Create a crater
        crater.setCenterX(currentX);
        crater.setCenterY(currentY);

        // Check if explosion affects any of the players
        for (Player player : game.getPlayers()) {
            if (crater.intersects(player.getBounds())) {
                // Give the other player(s) a point
                for (Player p : game.getPlayers()) {
                    if (!p.equals(player)) {
                        p.incrementScore();
                        playerHit = true;
                    }
                }
            }
        }

        // Check if any of the buildings are affected by the explosion
        for (Building building : buildings) {
            if (crater.intersects(building.getShapeBounds())) {
                ((AnchorPane) root).getChildren().removeAll(building.getBuildingShape());
                ((AnchorPane) root).getChildren().removeAll(building.getWindows());

                building.addCrater(crater);

                ((AnchorPane) root).getChildren().addAll(building.getBuildingShape());
                ((AnchorPane) root).getChildren().addAll(building.getWindows());
            }
        }
        throwFinished();
    }


    private void checkObstacleCollision() {
        if (projectile.intersects(PlayerCreatorController.getObstacle().getImageView().getBoundsInParent())) {
            PlayerCreatorController.getObstacle().reset();
            throwFinished();

        }
    }



    private void checkPlayerCollision() {
        // Check if we hit a player
        for (Player player : game.getPlayers()) {
            // Disable friendly fire (shooting yourself directly)
            if (!player.equals(game.getCurrentPlayer())) {
                if (projectile.intersects(player.getBounds())) {
                    game.getCurrentPlayer().incrementScore();
                    playerHit = true;
                    throwFinished();

                }
            }
        }
    }


    private void createExplosion() {
        explosion.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("textures/explosion.gif"))));
        explosion.setScaleX(0.25);
        explosion.setScaleY(0.25);
        explosion.setX(currentX - explosion.getImage().getWidth() / 2);
        explosion.setY(currentY - explosion.getImage().getHeight() / 2);
        explosion.setVisible(true);
    }


    public void throwFinished() {
        // Stop the timer (and thereby the animation and calculation)
        stop();
        resetProjectile();

        // Update the current player turn text label
        GameController.updatePlayerTurn();

        createExplosion();

        // Let the explosion finish before updating the game
        PauseTransition PT = new PauseTransition(Duration.millis(1500));
        PT.setOnFinished(event -> updateGame());
        PT.play();
    }

    public void updateGame() {
        throwFields();
        boolean isWon = game.isWon();
        if (!isWon && playerHit) {
            // Reset boolean
            playerHit = false;
            game.regenerateMap();
        }
        else if (isWon) {
            try {
                GameController.switchToGameOver();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetProjectile() {
        // Reset/Hide trajectory and projectile
        trajectory.getElements().removeAll(trajectory.getElements());
        ((AnchorPane) root).getChildren().removeAll(projectile);
    }

    // GETTERS
    // Get y-coordinate of the trajectory
    private double getY(double time) {
        return initialY - (yVelocity * time - (gravity / 2) * time * time);
    }

    // Get x-coordinate of the trajectory
    private double getX(double time) {
        return initialX + xVelocity * time;
    }

    // SETTERS
    public void setVelocity(double velocity) {
        this.velocity = velocity;
        this.xVelocity = velocity * Math.cos(Math.toRadians(angle));
        this.yVelocity = velocity * Math.sin(Math.toRadians(angle));
        setTimeInterval();
    }

    public void setTimeInterval() {
        // Sets the time interval between each calculation
        this.timeInterval = (1.0 / 60.0);
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void loadGameSettings(Game game) {
        setGravity(game.getGravity());
        setStartPosition(game.getCurrentPlayer().getLocationCenter());
        setBuildings(game.getBuildings());
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setStartPosition(Point2D location) {
        this.initialX = location.getX();
        this.initialY = location.getY();
    }

    public void setTrajectory(Path trajectory) {
        this.trajectory = trajectory;
        this.trajectory.getElements().addAll(new MoveTo(initialX, initialY));
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void throwFields() {
        this.player1ThrowButton.setDisable(false);
        this.angleField.setDisable(false);
        this.velocityField.setDisable(false);
    }

    public void setThrowFields(Button player1ThrowButton, TextField velocityField, TextField angleField) {
        this.player1ThrowButton = player1ThrowButton;
        this.angleField = angleField;
        this.velocityField = velocityField;
    }

}
