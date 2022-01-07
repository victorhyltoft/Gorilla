package com.example.gorilla;

import com.example.gorilla.Models.Projectile;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.LineTo;


public class TrajectoryTimer extends AnimationTimer {
    private static final double GRAVITY = 9.81;
    private double velocity = Controller.getVelocity();
    private double angle = Controller.getAngle();
    private Double initialX = Controller.playerCircle1.getCenterX();
    private Double initialY = Controller.playerCircle1.getCenterY();
    private double xVelocity = velocity * Math.cos(Math.toRadians(angle));
    private double yVelocity = velocity * Math.sin(Math.toRadians(angle));
    public double time =  0.0;
//        private double TIME_INTERVAL = 0.1;

    @Override
    public void handle(long now) {
        // The handle is called every frame (which is 60fps)
        doHandle();

    }

    private void doHandle() {
        // Calculate next point

        // JavaFX renders 60fps. To make the projectile fly "velocity" pixel/sec we need to adjust the time between each render
        double TIME_INTERVAL = (1.0 / (60.0 * velocity)) * velocity;
        time += TIME_INTERVAL;

        // Update projectile
        Controller.projectile.setCenterX(getX(time));
        Controller.projectile.setCenterY(getY(time));

        // Update trajectory
        Controller.trajectory.getElements().addAll(new LineTo(getX(time), getY(time)));

        // Check if we hit a player


        // Check to see if X-coordinate is within range of player
        if (Math.abs((getX(time) - Controller.playerCircle2.getCenterX())) < Controller.game.getAcceptedRange()) {
            // Check to see if Y-coordinate is within range of player
            if (Math.abs((getY(time) - Controller.playerCircle2.getCenterY())) < Controller.game.getAcceptedRange()) {
                System.out.println("Boom");
                // Stop the timer (and thereby the animation)
                resetTrajectory();
                stop();
            }
        }

        // Check if outside the screen
        if (getY(time) >= Controller.game.getHeight()) {
            stop();
            resetTrajectory();
            System.out.println("Outside bottom");
        }

        // Check if outside either horizontal edge
        if (getX(time) >= Controller.game.getWidth() || getX(time) <= 0) {
            // Remove trajectory
            resetTrajectory();
            stop();
            System.out.println("Outside right or left side");
        }

    }

    private void resetTrajectory() {
        Controller.trajectory.getElements().removeAll(Controller.trajectory.getElements());
        Controller.projectile.setRadius(0);
        Controller.projectile.setVisible(false);
    }

    private Double getY(double time) {
        return initialY - (yVelocity * time - (GRAVITY / 2) * time * time);
    }

    private Double getX(double time) {
        return initialX + xVelocity * time;
    }
}