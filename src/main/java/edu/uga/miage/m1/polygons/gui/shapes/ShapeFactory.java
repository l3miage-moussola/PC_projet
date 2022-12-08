package edu.uga.miage.m1.polygons.gui.shapes;

public class ShapeFactory {
    public static SimpleShape createShape(int x, int y, TypeShape typeShape){
        return switch (typeShape){
            case CIRCLE -> new Circle(x, y);
            case SQUARE -> new Square(x, y);
            case TRIANGLE -> new Triangle(x, y);
            case GROUP -> new FormesGroupe();
        };
    }
}
