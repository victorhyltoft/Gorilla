package com.example.gorilla;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.util.Collection;

public class TrajectoryAnimation extends Application {

    private final int BUFFER = 50;
    AnchorPane root;
    public Circle projectile;
    public Path trajectory;
    public Circle player1;
    public Circle player2;
    public double height;
    public double width;
    public double acceptedRange;




    @Override
    public void start(Stage stage) {
        initUI(stage);
        height = 400;
        width = 500;
        acceptedRange = width / 50;

    }

    private void initUI(Stage stage) {
        stage.setHeight(400);
        stage.setWidth(500);

        // Create player objects
        player1 = new Circle(BUFFER, stage.getHeight() - BUFFER, 10);
        player2 = new Circle(stage.getWidth() - BUFFER, stage.getHeight() - BUFFER, 10);
        Group players = new Group(player1, player2);

        root = new AnchorPane();

        projectile = new Circle(100, 100, 20);
        trajectory = new Path();
        MoveTo moveto = new MoveTo(player1.getCenterX(), player1.getCenterY());
        trajectory.getElements().add(moveto);

        root.getChildren().add(players);
        root.getChildren().add(projectile);
        root.getChildren().add(trajectory);


        // Start animation timer
        AnimationTimer timer = new MyTimer();
        timer.start();

        var scene = new Scene(root, 300, 250);

        stage.setTitle("AnimationTimer");
        stage.setScene(scene);
        stage.show();
    }



    public class MyTimer extends AnimationTimer {
        private static final double GRAVITY = 9.81;
        private double velocity = 70;
        private double angle = 45;
        private Double initialX = player1.getCenterX();
        private Double initialY = player1.getCenterY();
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
            projectile.setCenterX(getX(time));
            projectile.setCenterY(getY(time));

            // Update trajectory
            trajectory.getElements().addAll(new LineTo(getX(time), getY(time)));

            // Check if we hit a player


            // Check to see if X-coordinate is within range of player
            if (Math.abs((getX(time) - player2.getCenterX())) < acceptedRange) {
                // Check to see if Y-coordinate is within range of player
                if (Math.abs((getY(time) - player2.getCenterY())) < acceptedRange) {
                    System.out.println("Boom");
                    // Stop the timer (and thereby the animation)
                    resetTrajectory();
                    stop();
                }
            }

            // Check if outside the screen
            if (getY(time) >= height) {
                stop();
                resetTrajectory();
                System.out.println("Outside bottom");
            }

            // Check if outside either horizontal edge
            if (getX(time) >= width || getX(time) <= 0) {
                // Remove trajectory
                resetTrajectory();
                stop();
                System.out.println("Outside right or left side");
            }

        }

        private void resetTrajectory() {
            trajectory.getElements().removeAll(trajectory.getElements());
            projectile.setRadius(0);
            projectile.setVisible(false);
        }

        private Double getY(double time) {
            return initialY - (yVelocity * time - (GRAVITY / 2) * time * time);
        }

        private Double getX(double time) {
            return initialX + xVelocity * time;
        }
    }


    public static void main(String[] args) {
        launch();
    }
}