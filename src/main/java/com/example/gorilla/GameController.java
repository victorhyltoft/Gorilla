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

    public static Text Player1ScoreText;
    // FXML ANNOTATION
    @FXML
    private TextField angleField;
    @FXML
    private TextField velocityField;
    @FXML
    static
    ImageView TextureImageView;
    @FXML
    Button TextureButtonRight;
    @FXML
    private Label PlayerName1Text;
    @FXML
    private Label PlayerName2Text;
    @FXML
    private TextField PlayerName1;
    @FXML
    private TextField PlayerName2;
    @FXML
    private Text CurrentPlayerTurn;
    @FXML
    public Text Player1Score;
    @FXML
    public Text Player2Score;

    // FIELDS
    private static int velocity;
    private static int angle;
    private Stage stage;
    private Scene scene;
    public static String Player1NameT = "player1";
    public static String Player2NameT = "player1";
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


    // GETTERS
    public static int getVelocity() {
        return velocity;
    }

    public static int getAngle() {
        return angle;
    }

    static Image myImage = new Image(Objects.requireNonNull(GameController.class.getResourceAsStream("Cat.png")));








    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (url.toString().endsWith("game.fxml")) {
            PlayerName1Text.setText(Player1NameT);
            PlayerName2Text.setText(Player2NameT);

            // Score text at bottom
            scoreText = new Text(player1.score + " | " + player2.score);
            scoreText.setStyle("-fx-text-fill: white;" + "-fx-font-size: 24");
            scoreText.setX(game.getWidth() / 2 - scoreText.getLayoutBounds().getWidth());
            scoreText.setTextAlignment(TextAlignment.CENTER);
            scoreText.setY(game.getHeight() - 24);
            

            CurrentPlayerTurn.setX(game.getWidth() / 2 -70);
            CurrentPlayerTurn.setY(0 + 20);
            CurrentPlayerTurn.setText("It's " + game.getCurrentPlayerName() + "'s turn.");
        }
    }

    public static void throwFinished() {
        trajectory.getElements().removeAll(GameController.trajectory.getElements());
        projectile.setRadius(0);
        projectile.setVisible(false);
        game.setCurrentPlayer();
        System.out.println(game.getCurrentPlayerName());
        scoreText.setText(player1.score + " | " + player2.score);

        Player1ScoreText = new Text("test???");

    }


    /**
     * This function gets the angle and velocity and animates the projectile and trajectory until
     * a miss or hit is registered.
     */
    public void throwProjectile(ActionEvent event) throws IOException {
        // Update the latest player
        // Get velocity and angle from the textfields and validate them.
        validate();
        // Load the basic game scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new trajectory path (used to
        trajectory = new Path();
        MoveTo moveTo;

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




        // Add the players, projectile and trajectory to the window
        ((AnchorPane) root).getChildren().addAll(playerCircle1, playerCircle2, projectile, trajectory, scoreText);

        // Start animation timer
        TrajectoryTimer timer = new TrajectoryTimer();
        timer.start();


        scene = new Scene(root, game.getWidth(), game.getHeight());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Validates angle and velocity
     */
    public void validate() {
        if (angleField.getText() == null || !isNumber(angleField.getText())) {
            angle = 0;
        }
        else {
            angle = Integer.parseInt(angleField.getText());
        }

        if (velocityField.getText() == null || !isNumber(velocityField.getText())) {
            velocity = 0;
        }
        else {
            velocity = Integer.parseInt(velocityField.getText());
        }

        if (angle < 0) {
            angle = 0;
        }
        else if (angle > 90) {
            angle = 90;
        }

        if (velocity < 0) {
            velocity = 0;
        }
    }

    /**
     * Checks if the given string can be parsed to an integer. Used for checking and validating velocityField and angleField.
     * @param string
     * @return
     */
    public boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e){
            System.out.println("Cannot be parsed to integer");
        }
        return false;
    }


}
