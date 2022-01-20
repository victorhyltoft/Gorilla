package com.example.gorilla;

import com.example.gorilla.Models.Building;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BuildingsTest extends Application {
    private ArrayList<Building> buildings = new ArrayList<>();
    private double totalBuildingWidth;


    public void createBuildings() {
        while (totalBuildingWidth < 1280) {
            Building b = new Building(totalBuildingWidth);
            buildings.add(b);
            totalBuildingWidth += b.getWidth();
        }

    }

    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        createBuildings();
        for (Building building : buildings) {
            root.getChildren().addAll(building.getRectangle());
            root.getChildren().addAll(building.getWindows());
        }


        Point2D location1 = buildings.get(1).getBuildingRoof();
        Circle p1 = new Circle(location1.getX(), location1.getY(), 10);
        Point2D location2 = buildings.get(buildings.size() - 2).getBuildingRoof();
        Circle p2 = new Circle(location2.getX(), location2.getY(), 10);
        root.getChildren().addAll(p1, p2);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}