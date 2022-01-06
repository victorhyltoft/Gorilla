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
        SetPlayerNames();
        System.out.println(Player1NameT);
        Point2D Player1Pos = new Point2D(height, 0);
        Point2D Player2Pos = new Point2D(height, width);
        Player Player1 = new Player(Player1NameT, Player1Pos);
        Player Player2 = new Player(Player2NameT, Player2Pos);
        System.out.println(Player1.name + " " + Player2.name);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,game.getWidth(),game.getHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        myLabel.setText(Player1NameT);
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
        System.out.println(game.getHeight()+" "+game.getWidth());
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
        velocity = Integer.parseInt(velocityField.getText());
        angle = Integer.parseInt(angleField.getText());
        if (game.currentPlayer() == 1) {
            throwPosition = new Point2D(50,gameSettings.getHeight()-100 );
        }
        else {
            System.out.println(gameSettings.getWidth()+" "+ gameSettings.getHeight());
            throwPosition = new Point2D(gameSettings.getWidth()-50,gameSettings.getHeight()-100);
        }

        // Initialize objects
        System.out.println(throwPosition+","+angle+","+velocity);
        // Trajectory Group object
        return new Projectile(throwPosition,angle,velocity,gameSettings);
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