package com.example.gorilla.Controllers;

import com.example.gorilla.Models.Building;
import com.example.gorilla.Models.Game;
import com.example.gorilla.Main;
import com.example.gorilla.Models.Obstacle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * @author Viktor Egesby
 */
public class PlayerCreatorController implements Initializable {

    @FXML
    public TextField playerName1;
    @FXML
    public TextField playerName2;
    @FXML
    public ImageView player1TextureImage;
    @FXML
    public ImageView player2TextureImage;
    @FXML
    public Button player1RightArrowButton;
    @FXML
    public Button player2RightArrowButton;
    @FXML
    public Button player1LeftArrowButton;
    @FXML
    public Button player2LeftArrowButton;
    @FXML
    public ImageView levelTextureImageView;

    public static int count = 0;
    public static int count2 = 0;
    public static int levelCount = 0;
    public static int totalTextures = 0;
    public static int totalLevelTextures = 0;
    private Stage stage;
    private Scene scene;
    private final Game game = SettingsController.game;
    public static Parent root;
    public static ArrayList<Image> images;
    public static ArrayList<Image> levelTexture;
    private static Obstacle bird;


    /**
     * This starts the actual game.
     * This function is called from the "player-creater.fxml"
     */
    public void startGame(ActionEvent event) throws IOException {
        game.createBuildings();
        createPlayers();
        SetBackground();

        root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("views/game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        game.setRoot(root);

        for (Building building : game.getBuildings()) {
            ((AnchorPane) root).getChildren().addAll(building.getBuildingShape());
            ((AnchorPane) root).getChildren().addAll(building.getWindows());
        }

        ((AnchorPane) root).getChildren().addAll(GameController.scoreText, GameController.currentPlayerTurn, GameController.trajectory);
        // Add players
        ((AnchorPane) root).getChildren().addAll(GameController.player1.getImageView(), GameController.player2.getImageView());

        bird = new Obstacle();
        bird.animatePath();
        ((AnchorPane) root).getChildren().addAll(bird.getImageView());

        GameController.root = root;

        Scene scene = new Scene(root, game.getWidth(), game.getHeight());

        GameController.scene = scene;
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("application.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Creates player objects and sets their initial positions
     */
    public void createPlayers() {
        game.createPlayers(playerName1.getText(), player1TextureImage.getImage());
        game.createPlayers(playerName2.getText(), player2TextureImage.getImage());

        GameController.player1 = game.getPlayers().get(0);
        GameController.player2 = game.getPlayers().get(1);
    }

    public void SetBackground() {
        GameController.testBackground = levelTextureImageView.getImage();
    }

    public static Obstacle getObstacle() {
        return bird;
    }


    public void player1RightArrow(ActionEvent event) {
        count = Math.floorMod((count + 1), totalTextures);
        player1TextureImage.setImage(images.get(count));

    }

    public void player2RightArrow(ActionEvent event) {
        count2 = Math.floorMod((count2 + 1), totalTextures);
        player2TextureImage.setImage(images.get(count2));
    }
    public void player2LeftArrow(ActionEvent event) {
        count2 = Math.floorMod((count2 - 1), totalTextures);
        player2TextureImage.setImage(images.get(count2));
    }
    public void player1LeftArrow(ActionEvent event) {
        count = Math.floorMod((count - 1), totalTextures);
        player1TextureImage.setImage(images.get(count));
    }

    public void levelRightArrow(ActionEvent event) {
        levelCount = Math.floorMod((levelCount + 1), totalLevelTextures);
        GameController.controllerCount = levelCount;
        levelTextureImageView.setImage(levelTexture.get(levelCount));
    }
    public void levelLeftArrow(ActionEvent event) {
        levelCount = Math.floorMod((levelCount - 1), totalLevelTextures);
        GameController.controllerCount = levelCount;
        levelTextureImageView.setImage(levelTexture.get(levelCount));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images = new ArrayList<>();
        images.add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("player-textures/Gorilla.png"))));
        images.add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("player-textures/1636748461118.jpg"))));
        images.add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("player-textures/bue.jpg"))));
        images.add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("player-textures/1636967318329.png"))));

        levelTexture = new ArrayList<>();
        levelTexture.add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("level-textures/blankbackground.png"))));
        levelTexture.add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("level-textures/junglebackground.png"))));
        levelTexture.add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("level-textures/mario-level.png"))));
        levelTexture.add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("level-textures/space.jpg"))));
        totalTextures = images.size();
        totalLevelTextures = levelTexture.size();
    }
}
