package com.example.gorilla.Models;

import javafx.geometry.Point2D;
import java.util.ArrayList;

/**
 * @author Victor Hyltoft
 */

public class Projectile {
    // Constants
    private static final double GRAVITY = 9.81;
    private static final double RADIANS = Math.PI/180;
    private static final int MAX_ITERATIONS = 2000;
    private static final Double TIME_INTERVAL = 0.1;

    // Fields
    public Point2D startposition;
    public int angle;
    public int speed;
    public ArrayList<Point2D> trajectory;


    // Constructor 1 (Handles possible textures)
    public Projectile(Point2D startposition, int angle, int speed) {
        this.startposition = startposition;
        this.angle = angle % 90;
        this.speed = Math.max(speed, 0);
        this.trajectory = new ArrayList<>();
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

        double y;
        double time = 0;
        // Minimize computational costs
        final double result1 = Math.tan(angle * RADIANS);
        final double result2 = (2*this.speed*this.speed*Math.pow(Math.cos(angle * RADIANS),2));

        for(int i = 0; i < MAX_ITERATIONS; i++){
            time += TIME_INTERVAL;
            y = this.startposition.getY() + time*result1 - (GRAVITY*time*time)/result2;
            // Stop the calculation if the trajectory is reaches the ground (x,0)
            if (y <= 0) {
                break;
            }
            trajectory.add(new Point2D(time, y));
        }

    }




}
