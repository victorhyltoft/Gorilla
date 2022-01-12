package com.example.gorilla;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public class Buildings extends Application {
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
    public void start(Stage stage) throws Exception {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        createBuildings();
        for (Building building : buildings) {
            root.getChildren().addAll(building.getRectangle());
            root.getChildren().addAll(building.getWindows());
        }
        stage.setTitle("Gorillas ");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}