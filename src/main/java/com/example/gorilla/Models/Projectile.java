package com.example.gorilla.Models;

import javafx.geometry.Point2D;
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
    public double sceneHeight;


    // Constructor 1
    public Projectile(Point2D startposition, int angle, int speed) {
        this.startposition = startposition;
        this.angle = angle % 90;
        this.speed = Math.max(speed, 0);
        this.trajectory = new ArrayList<>();
        calculateTrajectory();
        System.out.println(trajectory);
    }

    // Constructor 2
    public Projectile(Point2D startposition, int angle, int speed, double sceneHeight) {
        this.startposition = startposition;
        this.angle = angle % 90;
        this.speed = Math.max(speed, 0);
        this.trajectory = new ArrayList<>();
        this.sceneHeight = sceneHeight;
        calculateTrajectory();
    }

    // TODO : Validate angle, speed and startpositions


    /**
     * Calculates the trajectory for the projectile based on the startposition,
     * angle, velocity and the Gravitational acceleration;
     * using; y=h+x*tan(a)-g*x^2/(2*v0^2*cos(a)^2)
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

            // Don't render if projectile is out of bounds
            if (y >= (sceneHeight)) {
                break;
            }

            trajectory.add(new Point2D(x, y));

        }

    }



}
