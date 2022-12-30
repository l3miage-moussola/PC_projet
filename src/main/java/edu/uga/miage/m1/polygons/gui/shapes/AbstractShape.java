package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.*;

public abstract class AbstractShape implements SimpleShape{
    public static final int SELECTED_BORDER_WIDTH = 3;
    public static final int BORDER_PADDING = 5;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    //protected SimpleShapeState state;
    protected boolean selected;

    public AbstractShape(int x, int y){
        this.x = x;
        this.y = y;
        this.height = 50;
        this.width = 50;
        this.selected = false;
    }
    AbstractShape(int x, int y, int width, int height) {
        this(x, y);
        this.height = height;
        this.width = width;
    }
    void drawSelection(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(SELECTED_BORDER_WIDTH));
        g2.drawRect(x - BORDER_PADDING, y - BORDER_PADDING, width + BORDER_PADDING * 2, height + BORDER_PADDING * 2);
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

    @Override
    public void select() {
        this.selected = true;
    }
    @Override
    public void unselect() {
        this.selected = false;
    }
    @Override
    public void group() {
        this.selected = false;
    }
    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }
    @Override
    public boolean isInside(int x, int y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

    }
