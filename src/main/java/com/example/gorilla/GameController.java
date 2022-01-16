package com.example.gorilla;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    @FXML
    public Button Player1ThrowButton;
    // FXML ANNOTATION
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    @FXML
    static ImageView textureImageView;
    @FXML
    public ImageView gameBackground;

    // FIELDS
    public static Parent root;
    public static Scene scene;

    private int velocity;
    private int angle;

    public static Image testBackground;
    public static Game gameSettings;
    public static Player player1;
    public static Player player2;
    private Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("textures/Banana.png")));
    private ImageView projectile;
    public static Path trajectory = new Path();
    public static Text scoreText;
    public static Text currentPlayerTurn;
    public Obstacle obstacle = new Obstacle();


    public GameController() {
        gameSettings = SettingsController.game;
    }

    // GETTERS
    public int getVelocity() {
        return velocity;
    }

    public int getAngle() {
        return angle;
    }


    /**
     * This function is ran everytime the "game.fxml" file is loaded (which is only once in PlayerCreatorController)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background();
        projectile = new ImageView(image);
        setScoreText();
        setCurrentPlayerTurnText();
    }

        // Score text at bottom
    // Creates the text object for the score and styles it
    private void setScoreText() {
        scoreText = new Text(player1.getScore() + " | " + player2.getScore());
        scoreText.setStyle("-fx-text-fill: white; -fx-font-size: 24; -fx-font-family: Fipps");
        scoreText.setX(gameSettings.getWidth() / 2 - scoreText.getLayoutBounds().getWidth());
        scoreText.setY(gameSettings.getHeight() - 18);
        scoreText.setFill(Color.WHITE);
    }

    // Creates the text object for the text displaying the current player and styles it
    private void setCurrentPlayerTurnText() {
        currentPlayerTurn = new Text("It's " + gameSettings.getCurrentPlayerName() + "'s turn.");
        currentPlayerTurn.setStyle("-fx-text-fill: white;" + "-fx-font-size: 18; -fx-font-family: Fipps");
        currentPlayerTurn.setX(gameSettings.getWidth() / 2 - currentPlayerTurn.getLayoutBounds().getWidth());
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
        projectile.setX(gameSettings.getCurrentPlayer().getLocation().getX());
        projectile.setY(gameSettings.getCurrentPlayer().getLocation().getY());
     //   projectile.setRadius(gameSettings.getAcceptedRange());
        ((AnchorPane) root).getChildren().addAll(projectile);

        // TODO : Revamp for 3+ players
        if (gameSettings.getCurrentTurn() == 1) {
            this.angle = (this.angle * -1) + 180;
        }

        currentPlayerTurn.setText("Throwing...");
        currentPlayerTurn.setX(gameSettings.getWidth() / 2 - (currentPlayerTurn.getLayoutBounds().getWidth() / 2));
        Player1ThrowButton.setDisable(true);

        // Pass the data from the GameController into the Projectile model
        setProjectileData();
        resetFields();


    }


    private void setProjectileData() {
        // Calculate and display the projectile position and its trajectory
        gameSettings.setRoot(root);
        Projectile p = new Projectile(this.projectile, gameSettings);
        p.setRoot(root);
        p.setAngle(getAngle());
        p.setVelocity(getVelocity());
        p.setTrajectory(trajectory);
        p.setPlayer1ThrowButton(Player1ThrowButton);
        p.start(); // Starts the calculation and animation of the projectile and its trajectory
    }

    // Reset fields
    public void resetFields() {
        angleField.setText("");
        velocityField.setText("");
    }


    public static void updatePlayerTurn() {
        // Get the next player and update turn
        gameSettings.nextPlayer();
        currentPlayerTurn.setText("It's " + gameSettings.getCurrentPlayerName() + "'s turn.");

        System.out.println(player1.getScore() + " | " + player2.getScore());
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
        gameBackground.setImage(testBackground);
        System.out.println(gameSettings.getWidth());
        gameBackground.setFitWidth(gameSettings.getWidth());
        System.out.println(gameBackground.getX());

    }

}
