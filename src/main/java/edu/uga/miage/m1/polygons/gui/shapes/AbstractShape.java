package edu.uga.miage.m1.polygons.gui.shapes;

public abstract class AbstractShape implements SimpleShape{

    protected int x;
    protected int y;

    public AbstractShape(int x, int y){
        this.x = x;
        this.y = y;
    }





    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
