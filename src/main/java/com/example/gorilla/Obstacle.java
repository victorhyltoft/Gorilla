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

    public ImageView getImageView() {
        return imageView;
    }

    private boolean rightSide() {
        return new Random().nextBoolean();
    }

    public void determinePosition() {
        setHeight();
        if (rightSide()) {
            startPosition = new Point2D(-200,height);
            endPosition = new Point2D(SettingsController.game.getWidth()+200,height);
            imageView.setScaleX(0.2);
        }
        else {
            startPosition = new Point2D(SettingsController.game.getWidth()+200,height);
            endPosition = new Point2D(-200,height);
            imageView.setScaleX(-0.2);
        }
    }


    public void animatePath() {
        transition = new PathTransition();
        transition.setDuration(Duration.seconds(20));
        transition.setNode(imageView);
        transition.setCycleCount(1);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                determinePosition();
                line = new Line(startPosition.getX(), startPosition.getY(), endPosition.getX(), endPosition.getY());
                transition.setPath(line);
                transition.play();
            }

        });

        transition.play();
    }

    public void setHeight() {
        this.height = ThreadLocalRandom.current().nextDouble(buffer, gameSettings.getHeight() - maxHeight);
    }

    public void reset() {
        transition.stop();
        animatePath();
    }

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


}
