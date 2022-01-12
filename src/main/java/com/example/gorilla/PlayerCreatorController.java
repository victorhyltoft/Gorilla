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
import java.util.AbstractList;
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


    /**
     * This starts the actual game.
     * This function is called from the "player-creater.fxml"
     */
    public void startGame(ActionEvent event) throws IOException {
        // TODO : REVAMP!!!
        // TODO : Get the instance of the GameController and set the appropriate fields required
//        GameController gameController = new GameController();

        System.out.println("Starting game");
        createPlayers();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Adds the created players to the root
        ((AnchorPane) root).getChildren().addAll(GameController.player1.getImageView(), GameController.player2.getImageView(), GameController.scoreText, GameController.currentPlayerTurn, GameController.trajectory);
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
        Player player1 = new Player(playerName1.getText(), new Point2D(game.getAcceptedRange(), game.getHeight() - game.getAcceptedRange()));
        Player player2 = new Player(playerName2.getText(), new Point2D(game.getWidth()-game.getAcceptedRange(), game.getHeight() - game.getAcceptedRange()));

        game.addPlayer(player1);
        game.addPlayer(player2);

        GameController.player1 = player1;
        GameController.player2 = player2;


        System.out.println(GameController.player1.getName() + " " + GameController.player2.getName());
    }


    public void displayImage() {
        GameController.textureImageView.setImage(myImage);
    }

}
