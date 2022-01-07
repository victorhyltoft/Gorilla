package com.example.gorilla;

import com.example.gorilla.Models.Player;
import com.example.gorilla.Models.Projectile;
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
    public void updateGame(ActionEvent event) throws IOException {
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