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
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void setSelected(int x , int y) {
    		selected = x>= this.x && x<= this.x +50 && y>= this.y && y<= this.y +50;
    }
    @Override
	public boolean getSelected() {
		return selected ;
    	
    }
    
}
