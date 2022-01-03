package com.example.gorilla.Models;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

class ProjectileTest {

    Projectile banana = new Projectile(new Point2D(0,0),45,10);

    @Test
    void trajectory() {
        for (Point2D point : banana.trajectory) {
            System.out.println(point.getY());
        }

    }
    
}