package com.example.gorilla;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
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
import java.util.ArrayList;
import java.util.Objects;


public class PlayerCreatorController {


    @FXML
    public TextField playerName1;
    @FXML
    public TextField playerName2;
    public ImageView textureImageView;
    public Button textureButtonRight;
    public Button textureButtonLeft;

    private Stage stage;
    private Scene scene;
    private final Game game = SettingsController.game;
    public Image myImage;
    public static Parent root;

    // TODO : TEST
    private ArrayList<Building> buildings = new ArrayList<>();


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

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();


        for (Building building : buildings) {
            ((AnchorPane) root).getChildren().addAll(building.getBuildingShape());
            ((AnchorPane) root).getChildren().addAll(building.getWindows());
        }


        //
        ((AnchorPane) root).getChildren().addAll(GameController.scoreText, GameController.currentPlayerTurn, GameController.trajectory);
        // Add players
        ((AnchorPane) root).getChildren().addAll(GameController.player1.getImageView(), GameController.player2.getImageView());
        GameController.root = root;

        Scene scene = new Scene(root, game.getWidth(), game.getHeight());
        GameController.scene = scene;
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Creates player objects and sets their initial positions
     */
    public void createPlayers() {
        // TODO : Clean up

        double x1 = buildings.get(1).getBuildingRoof().getX();
        double y1 = buildings.get(1).getBuildingRoof().getY() - 19;
        Point2D location1 = new Point2D(x1, y1);

        double x2 = buildings.get(buildings.size() - 2).getBuildingRoof().getX();
        double y2 = buildings.get(buildings.size() - 2).getBuildingRoof().getY() - 19;
        Point2D location2 = new Point2D(x2, y2);

        Player player1 = new Player(playerName1.getText(), location1);
        Player player2 = new Player(playerName2.getText(), location2);

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


    public void displayImage() {
        GameController.textureImageView.setImage(myImage);
    }

}
