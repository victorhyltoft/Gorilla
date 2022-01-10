package com.example.gorilla;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PlayerCreatorController {


    public TextField PlayerName1;
    public ImageView TextureImageView;
    public Button TextureButtonRight;
    public Button TextureButtonLeft;
    public TextField PlayerName2;
    private Stage stage;
    private Scene scene;

    /**
     * This starts the actual game.
     * This function is called from the "player-creater.fxml"
     */
    public void startGame(ActionEvent event) throws IOException {
        System.out.println("Starting game");
        createPlayers();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Adds the created players to the root
        ((AnchorPane) root).getChildren().addAll(GameController.playerCircle1, GameController.playerCircle2, GameController.scoreText);


        scene = new Scene(root,SettingsController.game.getWidth(),SettingsController.game.getHeight());
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
        GameController.player1 = new Player(GameController.Player1NameT, new Point2D(SettingsController.game.getAcceptedRange(), SettingsController.game.getHeight() - SettingsController.game.getAcceptedRange()));
        GameController.player2 = new Player(GameController.Player2NameT, new Point2D(SettingsController.game.getWidth()-SettingsController.game.getAcceptedRange(), SettingsController.game.getHeight() - SettingsController.game.getAcceptedRange()));

        GameController.playerCircle1 = new Circle(GameController.player1.location.getX(), GameController.player1.location.getY(), GameController.game.getAcceptedRange());
        GameController.playerCircle2 = new Circle(GameController.player2.location.getX(), GameController.player2.location.getY(), GameController.game.getAcceptedRange());

        System.out.println(GameController.player1.name + " " + GameController.player2.name);
    }


    public void displayImage() {
        GameController.TextureImageView.setImage(GameController.myImage);
    }

    public void setPlayerNames() {
        GameController.Player1NameT = PlayerName1.getText();
        GameController.Player2NameT = PlayerName2.getText();
    }
}
