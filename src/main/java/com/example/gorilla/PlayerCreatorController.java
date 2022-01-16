package com.example.gorilla;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


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
    public ImageView player3TextureImage;
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

    public Button textureButtonRight;
    public Button textureButtonLeft;

    public static int count = 0;
    public static int count2 = 0;
    public static int levelCount = 0;
    public static int totalTextures = 0;
    public static int totalLevelTextures = 0;
    private Stage stage;
    private Scene scene;
    private final Game game = SettingsController.game;
    public static Image myImage;
    public static Parent root;
    public static ArrayList<Image> images;
    public static ArrayList<Image> levelTexture;
    // TODO : TEST
    private final ArrayList<Building> buildings = new ArrayList<>();
    private static Obstacle bird;


    /**
     * This starts the actual game.
     * This function is called from the "player-creater.fxml"
     */
    public void startGame(ActionEvent event) throws IOException {
        System.out.println("Starting game");
        // TODO : REVAMP!!!
        // TODO : Get the instance of the GameController and set the appropriate fields required
//        GameController gameController = new GameController();

        createBuildings();
        createPlayers();
        SetBackground();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();


        for (Building building : buildings) {
            ((AnchorPane) root).getChildren().addAll(building.getRectangle());
            ((AnchorPane) root).getChildren().addAll(building.getWindows());
        }


        //
        ((AnchorPane) root).getChildren().addAll(GameController.scoreText, GameController.currentPlayerTurn, GameController.trajectory);
        // Add players
        ((AnchorPane) root).getChildren().addAll(GameController.player1.getImageView(), GameController.player2.getImageView());

        // TODO : TEST
        bird = new Obstacle();
        bird.animatePath();
        ((AnchorPane) root).getChildren().addAll(bird.getImageView());

        GameController.root = root;

        Scene scene = new Scene(root, game.getWidth(), game.getHeight());
        GameController.scene = scene;
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Creates player objects and sets their initial positions
     */
    public void createPlayers() {
        // TODO : Clean up

        double x1 = buildings.get(1).getBuildingRoof().getX() - 12;
        double y1 = buildings.get(1).getBuildingRoof().getY() - 24;
        Point2D location1 = new Point2D(x1, y1);

        double x2 = buildings.get(buildings.size() - 2).getBuildingRoof().getX() - 12;
        double y2 = buildings.get(buildings.size() - 2).getBuildingRoof().getY() - 24;
        Point2D location2 = new Point2D(x2, y2);

        Player player1 = new Player(playerName1.getText(), location1, player1TextureImage.getImage());
        Player player2 = new Player(playerName2.getText(), location2, player2TextureImage.getImage());


        game.addPlayer(player1);
        game.addPlayer(player2);

        GameController.player1 = player1;
        GameController.player2 = player2;


        System.out.println(GameController.player1.getName() + " " + GameController.player2.getName());
    }



    // TODO : TEST
    public void createBuildings() {
        double totalBuildingWidth = 0;
        while (totalBuildingWidth < game.getWidth()) {
            Building b = new Building(totalBuildingWidth);
            buildings.add(b);
            totalBuildingWidth += b.getWidth();
        }
        game.setBuildings(buildings);

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
        levelTextureImageView.setImage(levelTexture.get(levelCount));
    }
    public void levelLeftArrow(ActionEvent event) {
        levelCount = Math.floorMod((levelCount - 1), totalLevelTextures);
        levelTextureImageView.setImage(levelTexture.get(levelCount));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images = new ArrayList<>();
        images.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("player-textures/Gorilla.png"))));
        images.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("player-textures/1636748461118.jpg"))));
        images.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("player-textures/bue.jpg"))));
        images.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("player-textures/1636967318329.png"))));

        this.levelTexture = new ArrayList<Image>();
        this.levelTexture.add(new Image(getClass().getResourceAsStream("level-textures/7bohtjox51461.png")));
        this.levelTexture.add(new Image(getClass().getResourceAsStream("level-textures/Among-Us-A.jpg")));
        this.levelTexture.add(new Image(getClass().getResourceAsStream("level-textures/bob-omb-battlefield.jpg")));
        System.out.println("loaded");
        System.out.println(this.images.get(1));
        this.totalTextures = images.size();
        this.totalLevelTextures = levelTexture.size();
        System.out.println(totalTextures);
    }
}
