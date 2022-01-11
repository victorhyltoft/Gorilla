package com.example.gorilla;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.LineTo;


public class Projectile extends AnimationTimer {
    // Constant gravitational acceleration for now
    private static final double GRAVITY = 9.81;
//    private static final double GRAVITY = GameController.game.getGravity();


    private final double velocity = GameController.getVelocity();
    private final double angle = GameController.getAngle();
    private final double initialX = GameController.projectile.getCenterX();
    private final double initialY = GameController.projectile.getCenterY();
    private final double xVelocity = velocity * Math.cos(Math.toRadians(angle));
    private final double yVelocity = velocity * Math.sin(Math.toRadians(angle));
    private double time =  0.0;

    // TODO : Verify this is the correct time unit
    private final double TIME_INTERVAL = (1.0 / (60.0 * velocity)) * velocity;


    @Override
    public void handle(long now) {
        // The handle is called every frame (which is 60fps)
        doHandle();

    }

    private void doHandle() {
        // Calculate next point

        // JavaFX renders 60fps. To make the projectile fly "velocity" pixel/sec we need to adjust the time between each render
        time += TIME_INTERVAL;
        double currentX = getX(time);
        double currentY = getY(time);

        // Update projectile
        GameController.projectile.setCenterX(currentX);
        GameController.projectile.setCenterY(currentY);

        // Update trajectory
        GameController.trajectory.getElements().addAll(new LineTo(currentX, currentY));

        // Check if we hit a player
        // Check to see if X-coordinate is within range of player
        if (GameController.game.getCurrentTurn() != 0) {
            if (Math.abs((currentX - GameController.player2.getLocation().getX())) < GameController.game.getAcceptedRange()) {
                // Check to see if Y-coordinate is within range of player
                if (Math.abs((currentY - GameController.player2.getLocation().getY())) < GameController.game.getAcceptedRange()) {
                    System.out.println("Boom");
                    // Stop the timer (and thereby the animation)
                    GameController.player1.incrementScore();
                    GameController.throwFinished();
                    stop();
                }
            }
        }


        else {
            if (Math.abs((currentX - GameController.player1.getLocation().getX())) < GameController.game.getAcceptedRange()) {
                // Check to see if Y-coordinate is within range of player
                if (Math.abs((currentY - GameController.player1.getLocation().getY())) < GameController.game.getAcceptedRange()) {
                    System.out.println("Boom");
                    // Stop the timer (and thereby the animation)
                    GameController.player2.incrementScore();
                    GameController.throwFinished();
                    stop();
                }
            }
        }


        // Check if outside the screen
        if (currentY >= GameController.game.getHeight()) {
            stop();
            GameController.throwFinished();
            System.out.println("Outside bottom");
        }

        // Check if outside either horizontal edge
        if (currentX >= GameController.game.getWidth() || currentX <= 0) {
            // Remove trajectory
            GameController.throwFinished();
            stop();
            System.out.println("Outside right or left side");
        }
    }


    // Get y-coordinate
    private double getY(double time) {
        return initialY - (yVelocity * time - (GRAVITY / 2) * time * time);
    }

    // Get x-coordinate
    private double getX(double time) {
        return initialX + xVelocity * time;
    }
}