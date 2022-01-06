package com.example.gorilla;

import com.example.gorilla.Models.Player;
import com.example.gorilla.Models.Projectile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Controller extends Application {
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
    private Label myLabel;
    @FXML
    private TextField PlayerName1;
    @FXML
    private TextField PlayerName2;

    private Point2D throwPosition;
    private int velocity;
    private int angle;
    private int width;
    private int height;
    private double gravity;
    private Stage stage;
    private Scene scene;
    private static String Player1NameT = "player1";
    private static String Player2NameT = "player1";
    private static Game game = new Game();
    private static Player player1;
    private static Player player2;

    Image myImage = new Image(getClass().getResourceAsStream("Cat.png"));

    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-menu.fxml")));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("SIMPGorillas");
        stage.show();
    }

    /**
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
    public void updateGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Group trajectory = Trajectory.trajectoryToGroup(bananaThrow(game));
        ((AnchorPane) root).getChildren().add(trajectory);
        scene = new Scene(root, game.getWidth(), game.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    public int coinFlip() {
        Random random = new Random();
        return random.nextInt(2);
    }

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
    }

    public void displayImage() {
        TextureImageView.setImage(myImage);
    }

    public void SetPlayerNames() {
        Player1NameT = PlayerName1.getText();
        Player2NameT = PlayerName2.getText();
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