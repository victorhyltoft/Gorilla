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
    private Image myImage;
    public static Parent root;

    /**
     * This starts the actual game.
     * This function is called from the "player-creater.fxml"
     */
    public void startGame(ActionEvent event) throws IOException {
        System.out.println("Starting game");
        createPlayers();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Adds the created players to the root
        ((AnchorPane) root).getChildren().addAll(GameController.player1.getCircle(), GameController.player2.getCircle(), GameController.scoreText, GameController.currentPlayerTurn, GameController.trajectory);
        GameController.root = root;

        scene = new Scene(root,SettingsController.game.getWidth(),SettingsController.game.getHeight());
        GameController.scene = scene;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Creates player objects and sets their initial positions
     */
    public void createPlayers() {
        // TODO : Clean up
        GameController.player1 = new Player(playerName1.getText(), new Point2D(game.getAcceptedRange(), game.getHeight() - game.getAcceptedRange()));
        GameController.player2 = new Player(playerName2.getText(), new Point2D(game.getWidth()-game.getAcceptedRange(), game.getHeight() - game.getAcceptedRange()));

        System.out.println(GameController.player1.getName() + " " + GameController.player2.getName());
    }


    public void displayImage() {
        GameController.textureImageView.setImage(myImage);
    }

}
