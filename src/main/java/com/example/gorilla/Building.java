package com.example.gorilla;


import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Victor Hyltoft, Mikkel Allermand
 */
public class Building {
    private double width;
    private double height;
    // TODO : REMOVE VALUES

    private final double maxHeight;
    private final double minHeight;
    private final double maxWidth;
    private final double minWidth;
    private final Random random = new Random();



    // Building components
    private Rectangle rectangle;
    private Shape buildingShape;
    private final Color[] colors = new Color[]{Color.web("#00aaaa"), Color.web("#aa0000"), Color.web("#aaaaaa")};
    private final ArrayList<Shape> craters;

    // Window components
    private final ArrayList<Shape> windows = new ArrayList<>();
    private final double windowWidthPadding = 10;
    private double totalWindowWidth = windowWidthPadding;
    private final double windowHeightPadding = 15;
    private double totalWindowHeight = windowHeightPadding;
    private Game gameSettings = SettingsController.game;
    private double sceneHeight = gameSettings.getHeight();
    private double sceneWidth = gameSettings.getWidth();

    // Constructor
    public Building(double xPosition) {
        this.maxHeight = sceneHeight*0.60;
        this.minHeight = sceneHeight*0.20;
        this.maxWidth = sceneWidth*0.12;
        this.minWidth = sceneWidth*0.07;
        this.craters = new ArrayList<>();
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
        r.setY(sceneHeight-this.height);
        r.setWidth(this.width);
        r.setHeight(this.height);
        this.rectangle = r;
        setBuildingShape(this.rectangle);
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

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Bounds getRectangleBounds() {
        return rectangle.getLayoutBounds();
    }

    public Bounds getShapeBounds() {
        // Gets a rectangular bounding box
        return buildingShape.getLayoutBounds();
    }

    public ArrayList<Shape> getWindows() {
        return windows;
    }

    public Shape getBuildingShape() {
        return buildingShape;
    }


    public void setBuildingShape(Shape buildingShape) {
        this.buildingShape = buildingShape;
    }

    public ArrayList<Shape> getCraters() {
        return craters;
    }

    public void addCrater(Shape crater) {
        this.craters.add(crater);
        this.buildingShape = Shape.subtract(this.buildingShape, crater);
        this.buildingShape.setFill(getRectangle().getFill());

        // Iterate over the windows to see if any of them are affected by the explosion
        for (int j = 0; j < getWindows().size(); j++) {
            Shape currentWindow = getWindows().get(j);
            if (crater.intersects(currentWindow.getLayoutBounds())) {
                System.out.println("Explosion intersects window");

                Shape newWindowShape = Shape.subtract(currentWindow, crater);
                newWindowShape.setFill(currentWindow.getFill());
                getWindows().set(j, newWindowShape);
            }
        }
    }

    public double getMaxHeight() {
        return maxHeight;
    }

}
