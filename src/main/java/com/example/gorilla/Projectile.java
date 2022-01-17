package com.example.gorilla;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.Objects;


/**
 * @author Victor Hyltoft
 */

public class Projectile extends AnimationTimer {
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

    private Game gameSettings;
    private ImageView projectile;
    private ImageView explosion = new ImageView();
    private Path trajectory;
    private Parent root;
    private Button player1ThrowButton;

    private ArrayList<Building> buildings;

    // Crater
    private final double craterRadius = 40;
    private final Circle crater = new Circle(100,100, craterRadius);


    // Constructor
    public Projectile(ImageView projectile, Game gameSettings) {
        this.root = gameSettings.getRoot();


        this.projectile = projectile;
        this.gameSettings = gameSettings;
        this.projectile.setScaleX(1);
        this.projectile.setScaleY(1);
        setGameSettings(gameSettings);

        this.time = 0.0;

        explosion.setVisible(false);
        ((AnchorPane) root).getChildren().add(explosion);
    }


    // The handle is called every frame (which is 60fps)
    @Override
    public void handle(long now) {
        calculateNextPosition();
        updateProjectile();
        if (!checkBuildingCollision()) {
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
        projectile.setX(currentX);
        projectile.setY(currentY);

        projectile.setRotate(projectile.getRotate() + 2);

        // Update trajectory
        trajectory.getElements().addAll(new LineTo(currentX, currentY));
    }

    private void checkOutsideGame() {
        // Check if outside the screen
        if (currentY >= gameSettings.getHeight()) {
            System.out.println("Outside bottom");
            throwFinished();
        }

        // Check if outside either horizontal edge
        if (currentX >= gameSettings.getWidth() || currentX <= 0) {
            // Remove trajectory
            System.out.println("Outside right or left side");
            throwFinished();
        }
    }


    /**
     *
     * @return true if projectile collides with buildings
     */
    private boolean checkBuildingCollision() {

        // Iterate over each building
        for (int i = 0, totalBuildings = buildings.size(); i < totalBuildings; i++) {
            Building currentBuilding = buildings.get(i);
            // Check if projectile intersects with a building
            if (!projectile.intersects(currentBuilding.getShapeBounds())) {
                continue;
            }

            // Check if projectile intersects a crater from a previous shot
            for (Shape crater : currentBuilding.getCraters()) {
                if (projectile.intersects(crater.getLayoutBounds())) {
                    return false;
                }
            }

            // Create a crater
            crater.setCenterX(currentX);
            crater.setCenterY(currentY);

            // Check if explosion affects any of the players
            for (Player player : gameSettings.getPlayers()) {
                if (crater.intersects(player.getBounds())) {
                    // Give the other player a point
                    for (Player p : gameSettings.getPlayers()) {
                        if (!p.equals(player)) {
                            p.incrementScore();
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
            return true;
        }
        return false;
    }

    private void checkObstacleCollision() {
        if (projectile.intersects(PlayerCreatorController.getObstacle().getImageView().getBoundsInParent())) {
            PlayerCreatorController.getObstacle().reset();
            throwFinished();

        }
    }



    private void checkPlayerCollision() {
        // Check if we hit an opponent
        for (Player player : gameSettings.getPlayers()) {
            // Disable friendly fire (shooting yourself)
            if (!player.equals(gameSettings.getCurrentPlayer())) {
                // Check to see if X-coordinate of projectile is within range of opponent
                if (projectile.intersects(player.getBounds())) {
                    System.out.println("Boom");
                    gameSettings.getCurrentPlayer().incrementScore();
                    gameSettings.isWon();
                    throwFinished();
                }
            }
        }
    }

    private void createExplosion() {
        // Set the imageview (in a final field)
        // When throwFinished(), render the explosion
        explosion.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("textures/explosion.gif"))));
        explosion.setScaleX(0.25);
        explosion.setScaleY(0.25);
        System.out.println(explosion.getFitWidth());
        explosion.setX(currentX - explosion.getImage().getWidth() / 2);
        explosion.setY(currentY - (explosion.getImage().getHeight() / 2));
        explosion.setVisible(true);

    }

    public void throwFinished() {
        // Stop the timer (and thereby the animation)
        stop();
        createExplosion();
        resetProjectile();
        GameController.updatePlayerTurn();
        throwButton();

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
    public void setProjectile(ImageView projectile) {
        this.projectile = projectile;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
        this.xVelocity = velocity * Math.cos(Math.toRadians(angle));
        this.yVelocity = velocity * Math.sin(Math.toRadians(angle));
        setTimeInterval();
    }

    public void setTimeInterval() {
    // TODO : Verify this is the correct time unit
        // JavaFX renders 60fps. To make the projectile fly "velocity" pixel/sec we need to adjust the time between each render
        this.timeInterval = (1.0 / (60.0 * velocity)) * velocity;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setGameSettings(Game gameSettings) {
        setGravity(gameSettings.getGravity());
        setStartPosition(gameSettings.getCurrentPlayer().getLocationCenter());
        setBuildings(gameSettings.getBuildings());
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setStartPosition(Point2D location) {
        setInitialX(location.getX());
        setInitialY(location.getY());
    }

    public void setInitialX(double initialX) {
        this.initialX = initialX;
    }

    public void setInitialY(double initialY) {
        this.initialY = initialY;
    }


    public void setTrajectory(Path trajectory) {
        this.trajectory = trajectory;
        this.trajectory.getElements().addAll(new MoveTo(initialX, initialY));
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void throwButton() {
        this.player1ThrowButton.setDisable(false);
    }

    public void setPlayer1ThrowButton(Button player1ThrowButton) {
        this.player1ThrowButton = player1ThrowButton;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
}
