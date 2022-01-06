package com.example.gorilla.Models;

import com.example.gorilla.Game;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * @author Victor Hyltoft
 */

public class Projectile {
    // Constants
    private static final double GRAVITY = 9.81;
    private static final int MAX_ITERATIONS = 2000; // Limits the trajectory calculation
    private static final Double TIME_INTERVAL = 0.1;

    // Fields
    public Point2D startposition;
    public int angle;
    public int velocity;
    public ArrayList<Point2D> trajectory;
    public Path trajectoryPath;
    public Game gameSettings;


    // Constructor 1
    public Projectile(Point2D startposition, int angle, int velocity) {
        this.startposition = startposition;
        validator(angle, velocity);
        this.trajectory = new ArrayList<>();
        calculateTrajectory();
        trajectoryToPath();
    }

    // Constructor 2
    public Projectile(Point2D startposition, int angle, int velocity, Game gameSettings) {
        this.startposition = startposition;
        validator(angle, velocity);
        this.trajectory = new ArrayList<>();
        this.gameSettings = gameSettings;
        calculateTrajectory();
        trajectoryToPath();
    }

    // TODO : Validate angle and velocity
    public void validator(int angle, int velocity) {
        // Validate angle (0-90 allowed)
        if (angle > 90) angle = 90;
        else if (angle < 0) angle = 0;
        this.angle = angle;

        // Validate velocity is greater than 0
        if (velocity < 0) velocity = 0;
        this.velocity = velocity;
    }


    /**
     * Calculates the trajectory for the projectile based on the startposition,
     * angle, velocity and the gravitational acceleration.
     * Saves it to the trajectory field in the form of an ArrayList of 2D Points.
     */
    private void calculateTrajectory() {
        double x = this.startposition.getX();
        double y = this.startposition.getY();
        double initialX = x;
        double initialY = y;
        double velocity = this.velocity; // TODO : Add direction
        double xVelocity = velocity * Math.cos(Math.toRadians(angle));
        double yVelocity = velocity * Math.sin(Math.toRadians(angle));
        double time = 0;

        for(int i = 0; i < MAX_ITERATIONS; i++){
            time += TIME_INTERVAL;
            x = initialX + xVelocity * time;
            y = initialY - (yVelocity * time - (gameSettings.getGravity() / 2) * time * time);

            // Don't calculate if projectile is out of bounds
            if (y >= (gameSettings.getHeight())) {
                break;
            }
            trajectory.add(new Point2D(x, y));
        }
    }


    /**
     * Converts the 2D points in the projectile trajectory to a Path object
     */
    private void trajectoryToPath() {
        Path trajectoryPath = new Path();

        // Move path to starting positions of projectile
        MoveTo moveTo = new MoveTo();
        moveTo.setX(trajectory.get(0).getX());
        moveTo.setY(trajectory.get(0).getY());
        trajectoryPath.getElements().add(moveTo);

        // Iterate over each point to add it to the path
        for (Point2D point : trajectory) {
            LineTo lineTo = new LineTo();
            lineTo.setX(point.getX());
            lineTo.setY(point.getY());
            trajectoryPath.getElements().add(lineTo);
        }

        // Properties for the path
        trajectoryPath.setStroke(Color.RED);
        trajectoryPath.setStrokeWidth(5);

        this.trajectoryPath = trajectoryPath;
    }

    /**
     * Projectile animation
     * @param node is the object which follows the trajectory, fx the bullet.
     * @return a PathTransition object which can be used to play the trajectory animation
     */
    public PathTransition trajectoryAnimation(Node node) {
        // Creating the path transition
        PathTransition pathTransition = new PathTransition();

        // Setting the duration of the transition
        // The duration depends on the TIME_INTERVAL used in the trajectory calculation;
        pathTransition.setDuration(Duration.millis(this.trajectory.size()*(1000*TIME_INTERVAL)));

        // Linear interpolation
        pathTransition.setInterpolator(Interpolator.LINEAR);

        // Setting the node for the transition
        pathTransition.setNode(node);

        // Setting the path for the transition
        pathTransition.setPath(this.trajectoryPath);

        // Setting the orientation of the path
        pathTransition.setOrientation(
                PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        // Setting the cycle count for the transition
        pathTransition.setCycleCount(1);

        // Setting auto reverse value to true
        pathTransition.setAutoReverse(false);

        // Make projectile invisible when animation is finished
        pathTransition.setOnFinished(event -> {
            System.out.println("Animation finished");
            node.setVisible(false);
        });
        return pathTransition;
    }

    /**
     * Checks to see if the projectile hits a player
     * @param player the player we want to see if the trajectory hits
     * @return true if trajectory hits the targeted player
     */
    public boolean doesTrajectoryHit(Player player) {
        // Iterate over each point
        for (Point2D point : trajectory) {
            // Check to see if X-coordinate is within range of player
            if (Math.abs((point.getX() - player.location.getX())) < gameSettings.getAcceptedRange()) {
                // Check to see if Y-coordinate is within range of player
                if (Math.abs((point.getY() - player.location.getY())) < gameSettings.getAcceptedRange()) {
                    // System.out.println("Boom");
                    return true;
                }
            }

        }
        return false;
    }





}
