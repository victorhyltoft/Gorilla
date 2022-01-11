package com.example.gorilla;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    // FXML ANNOTATION
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    @FXML
    static ImageView textureImageView;
    @FXML
    public Label playerName1Text;
    @FXML
    public Label playerName2Text;
    @FXML
    public Text player1Score;
    @FXML
    public Text player2Score;

    // FIELDS
    public static Parent root;
    public static Scene scene;

    private static int velocity;
    private static int angle;

    public static Game game = SettingsController.game;
    public static Player player1;
    public static Player player2;
    public static Circle projectile = new Circle();
    public static Path trajectory = new Path();
    public static Text scoreText;
    public static Text currentPlayerTurn;


    // GETTERS
    public static int getVelocity() {
        return velocity;
    }

    public static int getAngle() {
        return angle;
    }


    /**
     * This function is ran everytime the "game.fxml" file is loaded (which is only once in PlayerCreatorController)
     * It initializes
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        playerName1Text.setText(player1.getName());
        playerName2Text.setText(player2.getName());

        // Score text at bottom
        scoreText = new Text(player1.getScore() + " | " + player2.getScore());
        scoreText.setStyle("-fx-text-fill: white; -fx-font-size: 24");
        scoreText.setX(game.getWidth() / 2 - scoreText.getLayoutBounds().getWidth());
        scoreText.setTextAlignment(TextAlignment.CENTER);
        scoreText.setY(game.getHeight() - 24);
        scoreText.setFill(Color.WHITE);

        currentPlayerTurn = new Text("It's " + game.getCurrentPlayerName() + "'s turn.");
        currentPlayerTurn.setStyle("-fx-text-fill: white;" + "-fx-font-size: 24");
        currentPlayerTurn.setX(game.getWidth() / 2 - currentPlayerTurn.getLayoutBounds().getWidth());
        currentPlayerTurn.setTextAlignment(TextAlignment.CENTER);
        currentPlayerTurn.setY(24);
        currentPlayerTurn.setFill(Color.WHITE);


        // Add the objects which are going to be updated
//        ((AnchorPane) root).getChildren().addAll(projectile, trajectory);

    }


    /**
     * This function gets the angle and velocity and animates the projectile and trajectory until
     * a miss or hit is registered.
     */
    public void throwProjectile() {

        // Make sure the text fields are valid
        if (!validateTextFields()) {
            angleField.setText("");
            velocityField.setText("");
            return;
        }

        // Create a new trajectory path (used to display the trajectory)
        MoveTo moveTo; // Sets the start position of the Path

        // TODO : Code improvements
        if (game.getCurrentTurn() == 1) {
            System.out.println("Setting projectile start (p1):");
            moveTo = new MoveTo(player1.getLocation().getX(), player2.getLocation().getY());
            projectile = player1.getCircle();
        }
        else {
            System.out.println("Setting projectile start (p2):");
            moveTo = new MoveTo(player2.getLocation().getX(), player2.getLocation().getY());
            projectile = player2.getCircle();
            angle = (angle * -1) + 180;
        }


        // Set the start position of the trajectory (which depends on the player position)
        trajectory.getElements().add(moveTo);

        ((AnchorPane) root).getChildren().addAll(projectile);

        currentPlayerTurn.setText("Throwing...");
        currentPlayerTurn.setX(game.getWidth() / 2 - currentPlayerTurn.getLayoutBounds().getWidth());

        // Calculate and display the projectile position and its trajectory
        Projectile projectile = new Projectile();
        projectile.start();
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

    public static void throwFinished() {

        // Reset/Hide trajectory and projectile
        trajectory.getElements().removeAll(trajectory.getElements());
        projectile.setRadius(0);
        projectile.setVisible(false);

        //
        ((AnchorPane) root).getChildren().removeAll(projectile);

        // Get the next player and update turn
        game.nextPlayer();
        currentPlayerTurn.setText("It's " + game.getCurrentPlayerName() + "'s turn.");

        // Update score
        scoreText.setText(player1.getScore() + " | " + player2.getScore());
    }





}
