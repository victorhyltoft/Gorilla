package com.example.gorilla;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;


/**
 * @author Victor Hyltoft
 */

public class Projectile extends AnimationTimer {
    private double velocity;
    private double angle;
    private double gravity;
    private double initialX;
    private double initialY;
    private double xVelocity;
    private double yVelocity;
    private double timeInterval;
    private double time;
    private Game gameSettings;
    private GameController gameController;
    private Circle projectile;
    private Path trajectory;
    private double currentX;
    private double currentY;
    private Parent root;
    private Button player1ThrowButton;

    public Projectile(Circle projectile) {
        this.projectile = projectile;
        this.time = 0.0;
    }


    // The handle is called every frame (which is 60fps)
    @Override
    public void handle(long now) {
        calculateNextPosition();
        updateProjectile();
        checkPlayerCollision();
        checkOutsideGame();
    }

    private void calculateNextPosition() {
        // Calculate next point
        time += timeInterval;
        currentX = getX(time);
        currentY = getY(time);
    }

    private void updateProjectile() {
        // Update projectile
        projectile.setCenterX(currentX);
        projectile.setCenterY(currentY);

        // Update trajectory
        trajectory.getElements().addAll(new LineTo(currentX, currentY));
    }

    private void checkOutsideGame() {
        // Check if outside the screen
        if (currentY >= gameSettings.getHeight()) {
            throwFinished();
            System.out.println("Outside bottom");
        }

        // Check if outside either horizontal edge
        if (currentX >= gameSettings.getWidth() || currentX <= 0) {
            // Remove trajectory
            throwFinished();
            System.out.println("Outside right or left side");
        }
    }

    private void checkPlayerCollision() {
        // Check if we hit an opponent
        for (Player player : gameSettings.getPlayers()) {
            // Disable friendly fire (shooting yourself)
            if (!player.equals(gameSettings.getCurrentPlayer())) {
                // Check to see if X-coordinate of projectile is within range of opponent
                if (Math.abs((currentX - player.getLocation().getX())) < gameSettings.getAcceptedRange()) {
                    // Check to see if Y-coordinate of projectile is within range of opponent
                    if (Math.abs((currentY -player.getLocation().getY())) < gameSettings.getAcceptedRange()) {
                        System.out.println("Boom");
                        gameSettings.getCurrentPlayer().incrementScore();
                        throwFinished();
                    }
                }
            }
        }
    }


    public void throwFinished() {
        // Stop the timer (and thereby the animation)
        stop();
        resetProjectile();
        GameController.updatePlayerTurn();
        throwButton();
    }

    public void resetProjectile() {
        // Reset/Hide trajectory and projectile
        trajectory.getElements().removeAll(trajectory.getElements());
        ((AnchorPane) root).getChildren().removeAll(projectile); // Remove the
    }


    // GETTERS

    // Get y-coordinate
    private double getY(double time) {
        return initialY - (yVelocity * time - (gravity / 2) * time * time);
    }

    // Get x-coordinate
    private double getX(double time) {
        return initialX + xVelocity * time;
    }

    public GameController getGameController() {
        return gameController;
    }


    // SETTERS
    public void setProjectile(Circle projectile) {
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
        this.gameSettings = gameSettings;

        setGravity(gameSettings.getGravity());

    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setStartPosition(Point2D startPosition) {
        setInitialX(startPosition.getX());
        setInitialY(startPosition.getY());
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
}
