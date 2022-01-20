package com.example.gorilla;

import com.example.gorilla.Controllers.PlayerCreatorController;
import com.example.gorilla.Controllers.SettingsController;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Mikkel Allermand
 */
public class Obstacle {
    private final Game gameSettings = SettingsController.game;
    private Point2D startPosition;
    private Point2D endPosition;
    private double height;
    private final double buffer = 50;
    private final double maxHeight = gameSettings.getBuildings().get(1).getMaxHeight() + buffer;
    private Image image;
    private ImageView imageView;
    private Line line;
    private PathTransition transition;

    public Obstacle() {
        setImage();
        this.imageView = new ImageView(image);
        imageView.setScaleY(0.2);
    }


    /**
     * Generates a pseudorandom boolean
     * @return Randomly chooses between true or false.
     */
    private boolean rightSide() {
        return new Random().nextBoolean();
    }

    /**
     * Determines the start and end position of the obstacle depending on boolean generated in the rightside method.
     */
    public void determinePosition() {
        setHeight();
        if (rightSide()) {
            startPosition = new Point2D(-200,height);
            endPosition = new Point2D(SettingsController.game.getWidth()+200,height);
            imageView.setScaleX(0.2); // is used to flip the image back to normal along its x-axis
        }
        else {
            startPosition = new Point2D(SettingsController.game.getWidth()+200,height);
            endPosition = new Point2D(-200,height);
            imageView.setScaleX(-0.2); // is used to flip the image along its X-axis
        }
    }

    /**
     * This is the method used to animate the obstacle.
     */
    public void animatePath() {
        transition = new PathTransition();
        transition.setDuration(Duration.seconds(20)); // Duration of the animation is set to 20 seconds.
        transition.setNode(imageView);
        transition.setCycleCount(1);
        transition.setOnFinished(new EventHandler<ActionEvent>() { // When the animation is finished:
            @Override
            public void handle(ActionEvent event) {
                determinePosition(); //Determines if we start from left or right and what height we start at
                line = new Line(startPosition.getX(), startPosition.getY(), endPosition.getX(), endPosition.getY()); // Defining a line for the path transition, uses values generated in determinePosition.
                transition.setPath(line); // Set the path of the transition to the line we just defined.
                transition.play(); // Lastly we start the animation.
            }

        });

        transition.play();
    }



    /**
     * Stops the animation and restarts it.
     */
    public void reset() {
        transition.stop();
        animatePath();
    }
    // SETTERS

    /**
     * Chooses obstacle texture based on which map you choose in the menu.
     */
    private void setImage() {
        if (PlayerCreatorController.levelCount == 2) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("textures/Bullet.png")));

        }
        else if (PlayerCreatorController.levelCount == 3) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("textures/UnidentifiedFlyingObject.png")));

        }
        else {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("textures/BirdGif.gif")));


        }

    }

    public void setHeight() {
        this.height = ThreadLocalRandom.current().nextDouble(buffer, gameSettings.getHeight() - maxHeight);
    }

    // GETTERS

    public ImageView getImageView() {
        return imageView;
    }


}
