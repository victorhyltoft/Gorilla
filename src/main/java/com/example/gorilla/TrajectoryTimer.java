package com.example.gorilla;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.LineTo;


public class TrajectoryTimer extends AnimationTimer {
    private static final double GRAVITY = 9.81;
    private double velocity = GameController.getVelocity();
    private double angle = GameController.getAngle();
    private Double initialX = GameController.projectile.getCenterX();
    private Double initialY = GameController.projectile.getCenterY();
    private double xVelocity = velocity * Math.cos(Math.toRadians(angle));
    private double yVelocity = velocity * Math.sin(Math.toRadians(angle));
    public double time =  0.0;
    private final double TIME_INTERVAL = (1.0 / (60.0 * velocity)) * velocity;
//        private double TIME_INTERVAL = 0.1;


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
        if (GameController.game.getCurrentPlayer() != 0) {
            if (Math.abs((currentX - GameController.playerCircle2.getCenterX())) < GameController.game.getAcceptedRange()) {
                // Check to see if Y-coordinate is within range of player
                if (Math.abs((currentY - GameController.playerCircle2.getCenterY())) < GameController.game.getAcceptedRange()) {
                    System.out.println("Boom");
                    // Stop the timer (and thereby the animation)
                    resetTrajectory();
                    GameController.player1.incrementScore();
                    System.out.println(GameController.player1.score);
                    stop();
                }
            }
        }


        else {
            if (Math.abs((currentX - GameController.playerCircle1.getCenterX())) < GameController.game.getAcceptedRange()) {
                // Check to see if Y-coordinate is within range of player
                if (Math.abs((currentY - GameController.playerCircle1.getCenterY())) < GameController.game.getAcceptedRange()) {
                    System.out.println("Boom");
                    // Stop the timer (and thereby the animation)
                    resetTrajectory();
                    GameController.player2.incrementScore();
                    System.out.println(GameController.player2.score);
                    stop();
                }
            }
        }


        // Check if outside the screen
        if (currentY >= GameController.game.getHeight()) {
            stop();
            resetTrajectory();
            System.out.println("Outside bottom");
        }

        // Check if outside either horizontal edge
        if (currentX >= GameController.game.getWidth() || currentX <= 0) {
            // Remove trajectory
            resetTrajectory();
            stop();
            System.out.println("Outside right or left side");
        }
    }


    private void resetTrajectory() {
        GameController.trajectory.getElements().removeAll(GameController.trajectory.getElements());
        GameController.projectile.setRadius(0);
        GameController.projectile.setVisible(false);
        GameController.throwFinished();
    }


    private Double getY(double time) {
        return initialY - (yVelocity * time - (GRAVITY / 2) * time * time);
    }


    private Double getX(double time) {
        return initialX + xVelocity * time;
    }
}