package com.example.gorilla;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Buildings extends Application {
    private ArrayList<Building> buildings = new ArrayList<>();
    private double totalBuildingWidth;
    public void createBuildings() {
        while (totalBuildingWidth < 1280) {
            Building building = new Building();
            building.buildingRectangle(building.getWidth(), building.getHeight(), totalBuildingWidth);
            buildings.add(building);
            totalBuildingWidth += building.getWidth();
            System.out.println(totalBuildingWidth);
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
        }
        stage.setTitle("SIMPGorillas");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}