package edu.uga.miage.m1.polygons.gui.shapes;

public abstract class AbstractShape implements SimpleShape{

    protected int x;
    protected int y;
    protected boolean selected;

    public AbstractShape(int x, int y){
        this.x = x;
        this.y = y;
        this.selected=false;
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
        this.x += x;
        this.y += y;
    }
    
    @Override
    public void setSelected() {
    	System.out.println("Set "+this);
    	this.selected=!this.selected;
    	System.out.println("Selected "+this);
    }
    @Override
	public boolean getSelected() {
		return this.selected ;
    	
    }
    @Override
    public boolean isInside(int x , int y) {
    	return x>= this.x-50 && x<= this.x +50 && y>= this.y-50 && y<= this.y +50;
    }
    
}
