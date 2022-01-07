package com.example.gorilla;

import com.example.gorilla.Models.Player;
import com.example.gorilla.Models.Projectile;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField widthField;
    @FXML
    ImageView TextureImageView;
    @FXML
    Button TextureButtonRight;
    @FXML
    private TextField gravityField;
    @FXML
    private Label PlayerName1Text;
    @FXML
    private Label PlayerName2Text;
    @FXML
    private TextField PlayerName1;
    @FXML
    private TextField PlayerName2;

    private Point2D throwPosition;
    private static int velocity;

    public static int getVelocity() {
        return velocity;
    }

    public static int getAngle() {
        return angle;
    }

    private static int angle;
    private Stage stage;
    private Scene scene;
    private static String Player1NameT = "player1";
    private static String Player2NameT = "player1";
    public static Game game = new Game(0,0,0);
    public static Player player1;
    public static Player player2;
    public static Circle playerCircle1;
    public static Circle playerCircle2;
    public static Circle projectile;
    public static Path trajectory;

    Image myImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Cat.png")));


    /**ww
     *
     */
    public void startGame2(ActionEvent event) throws IOException {
        createPlayers();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,game.getWidth(),game.getHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     *
     */
    public void switchToPlayGameScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("settings.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPlayerCreator(ActionEvent event) throws IOException {
        gameSettings();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("player-creator.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     */
    /*public void updateGame(ActionEvent event) throws IOException {
        Projectile projectile = bananaThrow(game);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Group trajectory = projectile.trajectoryToGroup();
        if (game.getCurrentPlayer() == 1) {
            System.out.println(projectile.doesTrajectoryHit(player2));
            if (projectile.doesTrajectoryHit(player2)) {
                player1.incrementScore();
                System.out.println(player1.score);
            }
        }
        else if (game.getCurrentPlayer() == 0) {
            if (projectile.doesTrajectoryHit(player1)) {
                player2.incrementScore();
                System.out.println(player2.score);
            }
        }
        ((AnchorPane) root).getChildren().add(trajectory);
        scene = new Scene(root, game.getWidth(), game.getHeight());
        stage.setScene(scene);
        stage.show();
    }*/

    public int coinFlip() {
        Random random = new Random();
        return random.nextInt(2);
    }

    public void initUI(ActionEvent event) throws IOException {
        // Create player objects
        game.setCurrentPlayer();
        System.out.println(game.getCurrentPlayer());
        velocity = Integer.parseInt(velocityField.getText());
        angle = Integer.parseInt(angleField.getText());

        playerCircle1 = new Circle(player1.location.getX(), player1.location.getY(), 10);
        playerCircle2 = new Circle(player2.location.getX(), player2.location.getY(), 10);
        Group players = new Group(playerCircle1, playerCircle2);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trajectory = new Path();
        MoveTo moveTo;

        if (game.getCurrentPlayer() == 1) {
            System.out.println("Setting projectile start (p1):");
            moveTo = new MoveTo(playerCircle1.getCenterX(), playerCircle1.getCenterY());
            projectile = new Circle(playerCircle1.getCenterX(), playerCircle1.getCenterY(), game.getAcceptedRange());
            System.out.println(projectile.getCenterX());
            System.out.println(projectile.getCenterY());
        }
        else {
            System.out.println("Setting projectile start (p2):");
            moveTo = new MoveTo(playerCircle2.getCenterX(), playerCircle2.getCenterY());
            projectile = new Circle(playerCircle2.getCenterX(), playerCircle2.getCenterY(), game.getAcceptedRange());
            angle = (angle * -1) + 180;
            System.out.println(projectile.getCenterX());
            System.out.println(projectile.getCenterY());
        }

        trajectory.getElements().add(moveTo);
        System.out.println(trajectory.getElements());

        ((AnchorPane) root).getChildren().add(players);
        ((AnchorPane) root).getChildren().add(projectile);
        ((AnchorPane) root).getChildren().add(trajectory);


        // Start animation timer
        TrajectoryTimer timer = new TrajectoryTimer();
        timer.start();

        scene = new Scene(root, game.getWidth(), game.getHeight());

        stage.setTitle("AnimationTimer");
        stage.setScene(scene);
        stage.show();
    }
/*
    public Projectile bananaThrow(Game gameSettings) {
        gameSettings.setCurrentPlayer();
        velocity = Integer.parseInt(velocityField.getText());
        angle = Integer.parseInt(angleField.getText());
        if (game.getCurrentPlayer() == 1) {
            throwPosition = player1.location;
        }
        else {
            throwPosition = player2.location;
        }

        // Initialize objects
        // Trajectory Group object
        return new Projectile(throwPosition,angle,velocity,gameSettings);
    }
*/


    public void createPlayers() {
        SetPlayerNames();
        Point2D player1Pos = new Point2D(50, game.getHeight()-100);
        Point2D player2Pos = new Point2D(game.getWidth()-50, game.getHeight()-100);
        player1 = new Player(Player1NameT, player1Pos);
        player2 = new Player(Player2NameT, player2Pos);
        System.out.println(player1.name + " " + player2.name);
    }
    public void exit() {
        System.exit(0);
    }


    public void gameSettings() {
        game.setWidth(Integer.parseInt(widthField.getText()));
        game.setHeight(Integer.parseInt(heightField.getText()));
        game.setGravity(Double.parseDouble(gravityField.getText()));
        game.setAcceptedRange(game.getWidth());
    }

    public void displayImage() {
        TextureImageView.setImage(myImage);
    }

    public void SetPlayerNames() {
        Player1NameT = PlayerName1.getText();
        Player2NameT = PlayerName2.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (url.toString().endsWith("game.fxml")) {
            PlayerName1Text.setText(Player1NameT);
            PlayerName2Text.setText(Player2NameT);
        }
    }

   /* public void updateGame(Stage stage, Group root, Projectile projectile) {
        System.out.println("Updating view");
        Group trajectory = Trajectory.trajectoryToGroup(projectile);
        root.getChildren().add(trajectory);
        Scene scene = root.getScene();
        stage.setScene(scene);
        stage.show();
    }*/


}