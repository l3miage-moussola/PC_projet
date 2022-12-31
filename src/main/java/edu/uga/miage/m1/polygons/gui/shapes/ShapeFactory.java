package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.image.BufferedImage;

public class ShapeFactory {
    public static SimpleShape createShape(int x, int y, TypeShape typeShape,BufferedImage buffer){
        return switch (typeShape){
            case CIRCLE -> new Circle(x, y);
            case SQUARE -> new Square(x, y);
            case TRIANGLE -> new Triangle(x, y);
            case GROUP -> new FormesGroupe();
            case BESTSHAPE -> new BestShape(x,y,buffer);
        };
    }
}
