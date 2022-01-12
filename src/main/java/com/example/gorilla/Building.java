package com.example.gorilla;


import javafx.scene.shape.Rectangle;

import java.util.concurrent.ThreadLocalRandom;

public class Building {
    private double width;
    private double height;
    private double SCENE_WIDTH = 1280;
    private double SCENE_HEIGHT = 720;
    private double maxHeight;
    private double minHeight;
    private double maxWidth;
    private double minWidth;
    private Rectangle rectangle;


    public Building() {
        this.maxHeight = SCENE_HEIGHT*0.60;
        this.minHeight = SCENE_HEIGHT*0.20;
        this.maxWidth = SCENE_WIDTH*0.12;
        this.minWidth = SCENE_WIDTH*0.07;
        calculateSize();
    }

    public void calculateSize() {
        this.height = ThreadLocalRandom.current().nextDouble(minHeight,maxHeight);
        this.width = ThreadLocalRandom.current().nextDouble(minWidth,maxWidth);
    }

    public void buildingRectangle(double width, double height, double x) {
        calculateSize();
        Rectangle r = new Rectangle();
        r.setX(x);
        r.setY(SCENE_HEIGHT-height);
        r.setWidth(width);
        r.setHeight(height);
        this.rectangle = r;
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
}
