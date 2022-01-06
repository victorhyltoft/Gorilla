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

//    @Test
//    void testValidAngles() {
//        Projectile p1 = new Projectile(new Point2D(0,0), 0, 0);
//        Projectile p2 = new Projectile(new Point2D(0,0), 45, 0);
//        Projectile p3 = new Projectile(new Point2D(0,0), 90, 0);
//
//        Projectile p4 = new Projectile(new Point2D(0,0), -10, 0);
//        Projectile p5 = new Projectile(new Point2D(0,0), 110, 0);
//
//    }

    // TODO : Test angle 0, 45, 90 and 100+
    // TODO : Limit min speed 0, max speed ??? undefined
    // TODO : Point must be inside scene
}