package com.example.gorilla;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static Text player1ScoreText;
    // FXML ANNOTATION
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    @FXML
    static
    ImageView textureImageView;
    @FXML
    Button textureButtonRight;
    @FXML
    private Label playerName1Text;
    @FXML
    private Label playerName2Text;
    @FXML
    private TextField playerName1;
    @FXML
    private TextField playerName2;
    @FXML
    public Text player1Score;
    @FXML
    public Text player2Score;

    // FIELDS
    private static int velocity;
    private static int angle;
    private Stage stage;
    private Scene scene;
    public static String player1NameT = "player1";
    public static String player2NameT = "player1";
    public static Game game = SettingsController.game;
    public static Player player1;
    public static Player player2;
    public static Circle playerCircle1;
    public static Circle playerCircle2;
    public static Circle projectile;
    public static Path trajectory;

    private Parent root;

    // TODO : TEST-BLOCK
    public static Text scoreText;
    // TEST-BLOCK DONE
    public static Text currentPlayerTurn;


    // GETTERS
    public static int getVelocity() {
        return velocity;
    }

    public static int getAngle() {
        return angle;
    }

    static Image myImage = new Image(Objects.requireNonNull(GameController.class.getResourceAsStream("Cat.png")));


    /**
     * This function is run everytime the "game.fxml" file is loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (url.toString().endsWith("game.fxml")) {
            playerName1Text.setText(player1NameT);
            playerName2Text.setText(player2NameT);

            // Score text at bottom
            scoreText = new Text(player1.score + " | " + player2.score);
            scoreText.setStyle("-fx-text-fill: white; -fx-font-size: 24");
            scoreText.setX(game.getWidth() / 2 - scoreText.getLayoutBounds().getWidth());
            scoreText.setTextAlignment(TextAlignment.CENTER);
            scoreText.setY(game.getHeight() - 24);

            currentPlayerTurn = new Text("It's " + game.getCurrentPlayerName() + "'s turn.");
            currentPlayerTurn.setStyle("-fx-text-fill: white;" + "-fx-font-size: 24");
            currentPlayerTurn.setX(game.getWidth() / 2 - currentPlayerTurn.getLayoutBounds().getWidth());
            currentPlayerTurn.setTextAlignment(TextAlignment.CENTER);
            currentPlayerTurn.setY(24);
        }
    }

    public static void throwFinished() {
        trajectory.getElements().removeAll(GameController.trajectory.getElements());
        projectile.setRadius(0);
        projectile.setVisible(false);
        game.setCurrentPlayer();
        currentPlayerTurn.setText("It's " + game.getCurrentPlayerName() + "'s turn.");
        scoreText.setText(player1.score + " | " + player2.score);

        player1ScoreText = new Text("test???");

    }


    /**
     * This function gets the angle and velocity and animates the projectile and trajectory until
     * a miss or hit is registered.
     */
    public void throwProjectile(ActionEvent event) throws IOException {
        // Update the latest player

        // Make sure the text fields are valid
        if (!validateTextFields()) {
            return;
        }

        // Load the basic game scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new trajectory path (used to display the trajectory)
        trajectory = new Path();
        MoveTo moveTo; // Sets the start position of the Path

        // TODO : Code improvements
        if (game.getCurrentPlayer() == 1) {
            System.out.println("Setting projectile start (p1):");
            moveTo = new MoveTo(playerCircle1.getCenterX(), playerCircle1.getCenterY());
            projectile = new Circle(playerCircle1.getCenterX(), playerCircle1.getCenterY(), game.getAcceptedRange());
        }
        else {
            System.out.println("Setting projectile start (p2):");
            moveTo = new MoveTo(playerCircle2.getCenterX(), playerCircle2.getCenterY());
            projectile = new Circle(playerCircle2.getCenterX(), playerCircle2.getCenterY(), game.getAcceptedRange());
            angle = (angle * -1) + 180;
        }

        trajectory.getElements().add(moveTo);
        System.out.println(trajectory.getElements());

        // Add the objects which are going to be updated
        ((AnchorPane) root).getChildren().addAll(playerCircle1, playerCircle2, projectile, trajectory, scoreText, currentPlayerTurn);
        currentPlayerTurn.setText("Throwing...");
        currentPlayerTurn.setX(game.getWidth() / 2 - currentPlayerTurn.getLayoutBounds().getWidth());
        // Start animation timer
        TrajectoryTimer timer = new TrajectoryTimer();
        timer.start();


        scene = new Scene(root, game.getWidth(), game.getHeight());

        stage.setScene(scene);
        stage.show();
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



}
