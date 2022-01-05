package com.example.gorilla.Models;

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
    private static final int MAX_ITERATIONS = 2000;
    private static final Double TIME_INTERVAL = 0.1;

    // Fields
    public Point2D startposition;
    public int angle;
    public int speed;
    public ArrayList<Point2D> trajectory;
    public double sceneHeight; // This will limit the projectile calculation to only the scene's height.


    // Constructor 1
    public Projectile(Point2D startposition, int angle, int speed) {
        this.startposition = startposition;
        this.angle = angle;
        this.speed = Math.max(speed, 0);
        this.trajectory = new ArrayList<>();
        this.sceneHeight = 0;
        calculateTrajectory();
    }

    // Constructor 2
    public Projectile(Point2D startposition, int angle, int speed, double sceneHeight) {
        this.startposition = startposition;
        this.angle = angle;
        this.speed = Math.max(speed, 0);
        this.trajectory = new ArrayList<>();
        this.sceneHeight = sceneHeight;
        calculateTrajectory();
    }

    // TODO : Validate angle, speed and startpositions


    /**
     * Calculates the trajectory for the projectile based on the startposition,
     * angle, velocity and the gravitational acceleration.
     * Saves it to the trajectory field in the form of an ArrayList of 2D Points.
     */
    public void calculateTrajectory() {

        final double GRAVITY = 9.81;

        double x = this.startposition.getX();
        double y = this.startposition.getY();
        double initialX = x;
        double initialY = y;
        double velocity = this.speed; // TODO : Add direction
        double xVelocity = velocity * Math.cos(Math.toRadians(angle));
        double yVelocity = velocity * Math.sin(Math.toRadians(angle));
        double time = 0;

        for(int i = 0; i < MAX_ITERATIONS; i++){
            time += TIME_INTERVAL;
            x = initialX + xVelocity * time;
            y = initialY - (yVelocity * time - (GRAVITY / 2) * time * time);

//            // Don't render if projectile is out of bounds
            if (sceneHeight != 0 && y >= (sceneHeight)) {
                break;
            }

//            if (y >= (sceneHeight)) {
//                break;
//            }

            trajectory.add(new Point2D(x, y));
        }
    }


    /**
     * Converts the 2D points in the projectile trajectory to a full path
     */
    // TODO : Maybe add a private field to store the calculcated path?!
    public Path trajectoryPath() {
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

        return trajectoryPath;
    }


    public PathTransition trajectoryAnimation(Node node) {
        //Creating a Path
        Path trajectoryPath = trajectoryPath();

        //Creating the path transition
        PathTransition pathTransition = new PathTransition();

        //Setting the duration of the transition
        //The duration depends on the TIME_INTERVAL used in the trajectory calculation;
        pathTransition.setDuration(Duration.millis(trajectory.size()*(1000*TIME_INTERVAL)));

        // Linear interpolation
        pathTransition.setInterpolator(Interpolator.LINEAR);

        //Setting the node for the transition
        pathTransition.setNode(node);

        //Setting the path for the transition
        pathTransition.setPath(trajectoryPath);

        //Setting the orientation of the path
        pathTransition.setOrientation(
                PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        //Setting the cycle count for the transition
        pathTransition.setCycleCount(1);

        //Setting auto reverse value to true
        pathTransition.setAutoReverse(false);

        // Make projectile invisible when animation is finished
        pathTransition.setOnFinished(event -> {
            System.out.println("Animation finished");
            node.setVisible(false);
        });
        return pathTransition;
    }





}
