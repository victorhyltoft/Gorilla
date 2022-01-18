package com.example.gorilla;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    // FXML ANNOTATION
    @FXML
    public Button throwButton;
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    @FXML
    public ImageView gameBackground;

    // FIELDS
    public static Parent root;
    public static Scene scene;

    private int velocity;
    private int angle;

    public static Image testBackground;
    public static int controllerCount;
    public static Game game;
    public static Player player1;
    public static Player player2;
    private Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("textures/banan3.png")));
    private Image background1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("level-textures/blank1.png")));
    private ImageView projectile;
    public static Path trajectory = new Path();
    public static Text scoreText;
    public static Text currentPlayerTurn;

    public GameController() {
        game = SettingsController.game;
    }

    // GETTERS
    public int getVelocity() {
        return velocity;
    }

    public int getAngle() {
        return angle;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background();
        projectile = new ImageView(image);
        setScoreText();
        setCurrentPlayerTurnText();
    }


    // Creates the text object for the score and styles it
    private void setScoreText() {
        scoreText = new Text(player1.getScore() + " | " + player2.getScore());
        scoreText.setStyle("-fx-text-fill: white; -fx-font-size: 24; -fx-font-family: Fipps");
        scoreText.setX(game.getWidth() / 2 - scoreText.getLayoutBounds().getWidth());
        scoreText.setY(game.getHeight() - 18);
        scoreText.setFill(Color.WHITE);
    }

    // Creates the text object for the text displaying the current player and styles it
    private void setCurrentPlayerTurnText() {
        currentPlayerTurn = new Text("It's " + game.getCurrentPlayerName() + "'s turn.");
        currentPlayerTurn.setStyle("-fx-text-fill: white;" + "-fx-font-size: 18; -fx-font-family: Fipps");
        currentPlayerTurn.setX(game.getWidth() / 2 - currentPlayerTurn.getLayoutBounds().getWidth());
        currentPlayerTurn.setY(30);
        currentPlayerTurn.setFill(Color.WHITE);
    }

    /**
     * This function gets the angle and velocity and animates the projectile and trajectory until
     * a miss or hit is registered.
     */
    public void throwProjectile() {
        // Make sure the text fields are valid
        if (!validateTextFields()) {
            resetFields();
            return; // Stop the program from continuing
        }
//        projectile.setX(game.getCurrentPlayer().getLocation().getX());
//        projectile.setY(game.getCurrentPlayer().getLocation().getY());
     //   projectile.setRadius(gameSettings.getAcceptedRange());
        ((AnchorPane) root).getChildren().addAll(projectile);

        // Flip the angle if 2nd player's turn
        if (game.getCurrentTurn() == 1) {
            this.angle = (this.angle * -1) + 180;
        }

        currentPlayerTurn.setText("Throwing...");
        currentPlayerTurn.setX(game.getWidth() / 2 - (currentPlayerTurn.getLayoutBounds().getWidth() / 2));
        throwButton.setDisable(true);
        angleField.setDisable(true);
        velocityField.setDisable(true);

        // Pass the required data from the GameController into the Projectile
        setProjectileData();

        resetFields();
    }


    private void setProjectileData() {
        // Calculate and display the projectile position and its trajectory
        game.setRoot(root);
        Projectile p = new Projectile(this.projectile, game);
        p.setRoot(root);
        p.setAngle(getAngle());
        p.setVelocity(getVelocity());
        p.setTrajectory(trajectory);
        p.setThrowFields(throwButton, velocityField, angleField);
        p.start(); // Starts the calculation and animation of the projectile and its trajectory
    }

    // Reset fields
    public void resetFields() {
        angleField.setText("");
        velocityField.setText("");
    }


    public static void updatePlayerTurn() {
        // Get the next player and update turn
        game.nextPlayer();
        currentPlayerTurn.setText("It's " + game.getCurrentPlayerName() + "'s turn.");

        // Update the score text
        scoreText.setText(player1.getScore() + " | " + player2.getScore());
        scoreText.toFront();
    }



    /**
     * Validates that the angle and velocity text fields both are integers and
     * the angle is between 0-90 and velocity is greater than 0
     * @return true if angle and velocity text fields both are valid
     */
    public boolean validateTextFields() {
        try {
            angle = Integer.parseInt(angleField.getText());
            velocity = Integer.parseInt(velocityField.getText());

            // Make sure angle is between 0-90 and velocity is greater than 0
            return angle <= 90 && angle >= 0 && velocity > 0;

        } catch (Exception e) {
            System.out.println("Only numbers allowed");
            return false;
        }
    }


    public void background() {
        if(controllerCount == 0) {
            gameBackground.setImage(background1);
            gameBackground.setFitWidth(game.getWidth());
        } else {
        gameBackground.setImage(testBackground);
        gameBackground.setPreserveRatio(false);
        gameBackground.setFitWidth(game.getWidth());
        }
    }


}
