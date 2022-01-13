package com.example.gorilla;


import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Victor Hyltoft, Mikkel Allermand
 */
public class Building {
    private double width;
    private double height;
    // TODO : REMOVE VALUES
    private double SCENE_WIDTH = 1280;
    private double SCENE_HEIGHT = 720;
    private double maxHeight;
    private double minHeight;
    private double maxWidth;
    private double minWidth;
    private final Random random = new Random();

    // Building components
    private Rectangle rectangle;
    private final Color[] colors = new Color[]{Color.web("#00aaaa"), Color.web("#aa0000"), Color.web("#aaaaaa")};

    // Window components
    private final ArrayList<Rectangle> windows = new ArrayList<>();
    private final double windowWidthPadding = 10;
    private double totalWindowWidth = windowWidthPadding;
    private final double windowHeightPadding = 15;
    private double totalWindowHeight = windowHeightPadding;

    // Constructor
    public Building(double xPosition) {
        this.maxHeight = SCENE_HEIGHT*0.60;
        this.minHeight = SCENE_HEIGHT*0.20;
        this.maxWidth = SCENE_WIDTH*0.12;
        this.minWidth = SCENE_WIDTH*0.07;
        generateSize();
        generateRectangle(xPosition);
        generateColor();
        generateWindows();
    }

    private void generateSize() {
        this.height = ThreadLocalRandom.current().nextDouble(minHeight,maxHeight);
        this.width = ThreadLocalRandom.current().nextDouble(minWidth,maxWidth);
    }

    private void generateColor() {
        int colorIdx = ThreadLocalRandom.current().nextInt(0, colors.length);
        this.rectangle.setFill(colors[colorIdx]);
    }

    private void generateRectangle(double x) {
        Rectangle r = new Rectangle();
        r.setX(x);
        r.setY(SCENE_HEIGHT-this.height);
        r.setWidth(this.width);
        r.setHeight(this.height);
        this.rectangle = r;
    }


    private void generateWindows() {
        double windowHeight = 20;
        double windowWidth = 10;

        while ((totalWindowHeight + windowHeight) < rectangle.getHeight()) {

            while ((totalWindowWidth + windowWidth) < rectangle.getWidth()) {
                Rectangle window = new Rectangle();
                window.setWidth(windowWidth);
                window.setHeight(windowHeight);
                window.setX(rectangle.getX() + totalWindowWidth);
                window.setY(rectangle.getY() + totalWindowHeight);
                window.setFill(isWindowLit() ? Color.web("#ffff55") : Color.web("#555555"));
                windows.add(window);
                totalWindowWidth += windowWidth + windowWidthPadding;
            }
            totalWindowWidth = windowWidthPadding;
            totalWindowHeight += windowHeight + windowHeightPadding;
        }
    }

    private boolean isWindowLit() {
        return random.nextBoolean();
    }

    public Point2D getBuildingRoof() {
        double x = rectangle.getX() + (rectangle.getWidth() / 2);
        double y = rectangle.getY();
        return new Point2D(x,y);
    }




    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Bounds getBounds() {
        return rectangle.getLayoutBounds();
    }

    public ArrayList<Rectangle> getWindows() {
        return windows;
    }
}
