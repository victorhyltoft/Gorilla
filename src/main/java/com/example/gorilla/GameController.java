package com.example.gorilla;

import com.example.gorilla.Models.Player;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    @FXML
    ImageView TextureImageView;
    @FXML
    Button TextureButtonRight;
    @FXML
    private Label PlayerName1Text;
    @FXML
    private Label PlayerName2Text;
    @FXML
    private TextField PlayerName1;
    @FXML
    private TextField PlayerName2;
    @FXML
    private Text CurrentPlayerTurn;

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
    public static Game game = SettingsController.game;
    public static Player player1;
    public static Player player2;
    public static Circle playerCircle1;
    public static Circle playerCircle2;
    public static Circle projectile;
    public static Path trajectory;

    Image myImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Cat.png")));


    /**
     * This starts the actual game.
     */
    public void startGame(ActionEvent event) throws IOException {
        createPlayers();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,game.getWidth(),game.getHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Creates player objects and sets their initial positions
     */
    public void createPlayers() {
        setPlayerNames();
        // TODO : Clean up
        player1 = new Player(Player1NameT, new Point2D(game.getAcceptedRange(), game.getHeight() - game.getAcceptedRange()));
        player2 = new Player(Player2NameT, new Point2D(game.getWidth()-game.getAcceptedRange(), game.getHeight() - game.getAcceptedRange()));
        System.out.println(player1.name + " " + player2.name);
    }


    public void displayImage() {
        TextureImageView.setImage(myImage);
    }

    public void setPlayerNames() {
        Player1NameT = PlayerName1.getText();
        Player2NameT = PlayerName2.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (url.toString().endsWith("game.fxml")) {
            PlayerName1Text.setText(Player1NameT);
            PlayerName2Text.setText(Player2NameT);
            CurrentPlayerTurn.setX(game.getWidth() / 2 -70);
            CurrentPlayerTurn.setY(0 + 20);
            if (game.getCurrentPlayer() == 0) {
                CurrentPlayerTurn.setText("Its " + Player1NameT + "'s turn");
            } else { CurrentPlayerTurn.setText("Its " + Player2NameT + "'s turn"); }
        }
    }


    /**
     * This function gets the angle and velocity and animates the projectile and trajectory until
     * a miss or hit is registered.
     */
    public void throwProjectile(ActionEvent event) throws IOException {
        // Create player objects
        game.setCurrentPlayer();
        System.out.println(game.getCurrentPlayer());
        velocity = Integer.parseInt(velocityField.getText());
        angle = Integer.parseInt(angleField.getText());

        playerCircle1 = new Circle(player1.location.getX(), player1.location.getY(), game.getAcceptedRange());
        playerCircle2 = new Circle(player2.location.getX(), player2.location.getY(), game.getAcceptedRange());
        Group players = new Group(playerCircle1, playerCircle2);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trajectory = new Path();
        MoveTo moveTo;

        if (game.getCurrentPlayer() == 1) {
            System.out.println("Setting projectile start (p1):");
            moveTo = new MoveTo(playerCircle1.getCenterX(), playerCircle1.getCenterY());
            projectile = new Circle(playerCircle1.getCenterX(), playerCircle1.getCenterY(), game.getAcceptedRange());
        }
        else {
            System.out.println("Setting projectile start (p2):");
            moveTo = new MoveTo(playerCircle2.getCenterX(), playerCircle2.getCenterY());
            projectile = new Circle(playerCircle2.getCenterX(), playerCircle2.getCenterY(), game.getAcceptedRange());
            angle = (angle * -1) + 180;
        }

        trajectory.getElements().add(moveTo);
        System.out.println(trajectory.getElements());

        // Add the players, projectile and trajectory to the window
        ((AnchorPane) root).getChildren().addAll(players, projectile, trajectory);

        // Start animation timer
        TrajectoryTimer timer = new TrajectoryTimer();
        timer.start();

        scene = new Scene(root, game.getWidth(), game.getHeight());

        stage.setTitle("AnimationTimer");
        stage.setScene(scene);
        stage.show();
    }


    }
