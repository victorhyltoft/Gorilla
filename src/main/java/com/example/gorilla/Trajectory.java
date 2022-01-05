package com.example.gorilla;

import com.example.gorilla.Models.Player;
import com.example.gorilla.Models.Projectile;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Trajectory extends Application {
    static final int RADIUS = 3;
    @Override
    public void start(Stage stage) {
        stage.setHeight(500);
        stage.setWidth(500);

        // Initialize objects
        Player Player = new Player("Some name", new Point2D(100, 300));
        Projectile projectile = new Projectile(Player.location,45,40, stage.getHeight() - 10);

        //Setting title to the Stage
        Button button = new Button("Throw");

        Group root = new Group(button);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Drawing a trajectory");
        stage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                updateGameAlternative(stage, projectile);
            }
        });
    }

    public void updateGame(Stage stage, Group root, Projectile projectile) {
        System.out.println("Updating view");
        Group trajectory = trajectoryToGroup(projectile);
        root.getChildren().add(trajectory);
        Scene scene = root.getScene();
        stage.setScene(scene);
        stage.show();
    }

    public void updateGameAlternative(Stage stage, Projectile projectile) {
        System.out.println("Getting path");

        pathTest(stage, projectile);

//        root.getChildren().add(trajectoryPath);
//        Scene scene = root.getScene();
//        stage.setScene(scene);
//        stage.show();
    }

    public void pathTest(Stage stage, Projectile projectile) {
        //Drawing a Circle
        Circle circle = new Circle();

        //Setting the position of the circle
        circle.setCenterX(300.0f);
        circle.setCenterY(135.0f);

        //Setting the radius of the circle
        circle.setRadius(10.0);

        //Setting the color of the circle
        circle.setFill(Color.RED);

        //Setting the stroke width of the circle
        circle.setStrokeWidth(20);

        PathTransition pathTransition = projectile.trajectoryAnimation(circle);

//        pathTransition.setOnFinished(event -> {
//            System.out.println("Animation finished (2)");
//            // TODO : Reset the view and switch player
//        });

        //Playing the animation
        pathTransition.play();
        System.out.println("Animation beginning");

        //Creating a Group object
        Group root = new Group(circle);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 300);

        //Setting title to the Stage
        stage.setTitle("Path transition example");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }




    /**
     * @deprecated
     * Groups the trajectory points from the projectile class, so it is possible to display
     * @return Group containing the trajectory in terms of QuadCurves
     */
    public static Group trajectoryToGroup(Projectile projectile) {
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


    public static void main(String[] args){
        launch(args);
    }
}  