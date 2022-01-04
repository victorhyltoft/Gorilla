package com.example.gorilla;

import com.example.gorilla.Models.Player;
import com.example.gorilla.Models.Projectile;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Trajectory extends Application {
    static final int RADIUS = 3;
    @Override
    public void start(Stage stage) {
        stage.setHeight(500);
        stage.setWidth(500);

        // Initialize objects
        Player Player = new Player("Some name", new Point2D(100, 300));
        Projectile projectile = new Projectile(Player.location,45,40, stage.getHeight());

        // Trajectory Group object
        Group trajectory = trajectoryToGroup(projectile);

        //Creating a Group object for the root
        Group root = new Group(trajectory);

        //Creating a scene object with the root object
        Scene scene = new Scene(root, 500, 500);

        //Setting title to the Stage
        stage.setTitle("Drawing a trajectory");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }

    /**
     * Groups the trajectory points from the projectile class, so it is possible to display
     * @return Group containing the trajectory in terms of QuadCurves
     */
    public Group trajectoryToGroup(Projectile projectile) {
        Group trajectoryGroup = new Group();
        // Iterate over each point
        for (int i = 0; i < projectile.trajectory.size() - 2; i++) {
            QuadCurve quadCurve = new QuadCurve();
            quadCurve.setStartX(projectile.trajectory.get(i).getX());
            quadCurve.setStartY(projectile.trajectory.get(i).getY());
            quadCurve.setControlX(projectile.trajectory.get(i + 1).getX());
            quadCurve.setControlY(projectile.trajectory.get(i + 1).getY());
            quadCurve.setEndX(projectile.trajectory.get(i + 2).getX());
            quadCurve.setEndY(projectile.trajectory.get(i + 2).getY());
            quadCurve.setStroke(Color.rgb(0,0,0));
            quadCurve.setStrokeWidth(RADIUS);
            quadCurve.setFill(null);
            // Add current curve to the group
            trajectoryGroup.getChildren().add(quadCurve);
        }
        // Return the full trajectory group
        return trajectoryGroup;
    }

    // Alternative rendering
//    public Group displayTrajectory2(Projectile projectile) {
//        Group trajectory = new Group();
//        for (Point2D point : projectile.trajectory) {
//            // Add new circle-point to trajectory group
//            trajectory.getChildren().add(new Circle(point.getX(), point.getY(), RADIUS));
//        }
//        return trajectory;
//    }


    public static void main(String args[]){
        launch(args);
    }
}  