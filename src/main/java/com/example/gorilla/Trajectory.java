//package com.example.gorilla;
//
//import com.example.gorilla.Models.Player;
//import com.example.gorilla.Models.Projectile;
//import javafx.animation.PathTransition;
//import javafx.application.Application;
//import javafx.geometry.Point2D;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.*;
//import javafx.stage.Stage;
//
//public class Trajectory extends Application {
//    static final int RADIUS = 3;
//
//    @Override
//    public void start(Stage stage) {
//        stage.setHeight(500);
//        stage.setWidth(500);
//
//        // Initialize objects
//        Player player1 = new Player("player1", new Point2D(0,stage.getHeight() - 50));
//        Player player2 = new Player("player2", new Point2D(stage.getWidth() - 51,stage.getHeight() - 50));
//        Game gameTest = new Game(20,20,20);
//        Projectile projectile = new Projectile(player1.location,45,66, gameTest);
//
//        //Setting title to the Stage
//        Button button = new Button("Throw");
//
//        Group root = new Group(button);
//        Scene scene = new Scene(root, 500, 500);
//        stage.setScene(scene);
//        stage.setTitle("Drawing a trajectory");
//        stage.show();
//
////        button.setOnAction(event -> updateGameAlternative(stage, projectile));
//        button.setOnAction(event -> trajectoryHitOpponent(stage, projectile, new Player[]{player1, player2}));
//    }
//
//    public void updateGame(Stage stage, Group root, Projectile projectile) {
//        System.out.println("Updating view");
//        Group trajectory = projectile.trajectoryToGroup();
//        root.getChildren().add(trajectory);
//        Scene scene = root.getScene();
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void updateGameAlternative(Stage stage, Projectile projectile) {
//        System.out.println("Getting path");
//
//        pathTest(stage, projectile);
//
////        root.getChildren().add(trajectoryPath);
////        Scene scene = root.getScene();
////        stage.setScene(scene);
////        stage.show();
//    }
//
//    public void pathTest(Stage stage, Projectile projectile) {
//        //Drawing a Circle
//        Circle circle = new Circle();
//
//        //Setting the position of the circle
//        circle.setCenterX(300.0f);
//        circle.setCenterY(135.0f);
//
//        //Setting the radius of the circle
//        circle.setRadius(10.0);
//
//        //Setting the color of the circle
//        circle.setFill(Color.RED);
//
//        //Setting the stroke width of the circle
//        circle.setStrokeWidth(20);
//
//        PathTransition pathTransition = projectile.trajectoryAnimation(circle);
//
////        pathTransition.setOnFinished(event -> {
////            System.out.println("Animation finished (2)");
////            // TODO : Reset the view and switch player
////        });
//
//        //Playing the animation
//        pathTransition.play();
//        System.out.println("Animation beginning");
//
//        //Creating a Group object
//        Group root = new Group(circle);
//
//        //Creating a scene object
//        Scene scene = new Scene(root, 600, 300);
//
//        //Setting title to the Stage
//        stage.setTitle("Path transition example");
//
//        //Adding scene to the stage
//        stage.setScene(scene);
//
//        //Displaying the contents of the stage
//        stage.show();
//    }
//
//
//    public void trajectoryHitOpponent(Stage stage, Projectile projectile, Player[] players) {
//        // Iterate over the trajectory points
//        Circle p1 = new Circle(players[0].location.getX(),players[0].location.getY(),10);
//        Circle p2 = new Circle(players[1].location.getX(),players[1].location.getY(),10);
//        Group playersGroup = new Group(p1, p2);
//
//
//        double acceptedRange = stage.getWidth() / 50;
//        System.out.println(acceptedRange);
//        System.out.println(players[0].location);
//        System.out.println(players[1].location);
//
//        Path projectilePath = projectile.trajectoryPath;
//
//        //Creating a Group object
//        Group root = new Group(playersGroup, projectilePath);
//
//        //Creating a scene object
//        Scene scene = new Scene(root, 600, 300);
//
//        int currentPlayer = 0;
//        int opponnetPlayer = (currentPlayer + 1) % players.length;
//
//
//        if (projectile.doesTrajectoryHit(players[0])) {
//            System.out.println(players[currentPlayer].name + " hit " + players[1].name);
//            players[currentPlayer].incrementScore();
//            System.out.println(players[currentPlayer].name + " now has: " + players[currentPlayer].score + " points");
//        }
//        //Setting title to the Stage
//        stage.setTitle("TrajectoryHit");
//
//        //Adding scene to the stage
//        stage.setScene(scene);
//
//        //Displaying the contents of the stage
//        stage.show();
//        // If point is within n/50 (n=width) of opponent player, increment score
//    }
//
//
//
//    public static void main(String[] args){
//        launch(args);
//    }
//}