package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormesGroupe extends AbstractShape {

	protected FormesGroupe() {
		super(0,0);
		this.shapesList = new ArrayList<>();
	}
	private static final long serialVersionUID = 5368906687199772837L;
	private List<SimpleShape> shapesList;
	public List<SimpleShape> getGroupeForms(){
		return shapesList;
	}
	public void add(SimpleShape shape) {
		this.shapesList.add(shape);
	}

	@Override
	public void draw(Graphics2D g2) {

	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public void accept(Visitor visitor) {

	}

	private void updateSize() {
		int tempX=shapesList.get(0).getX();
		int tempY=shapesList.get(0).getY();
		int tempHeight = shapesList.get(0).getHeight();
		int tempWidth = shapesList.get(0).getWidth();
		for (SimpleShape shape: shapesList){
			if(shape.getX()<tempX){
				tempWidth+=tempX-shape.getX();
				tempX=shape.getX();
			}
			if(shape.getY()<tempY){
				tempHeight+=tempY-shape.getY();
				tempY=shape.getY();
			}
			if(shape.getX()+shape.getWidth()>tempX+tempWidth){
				tempWidth=shape.getX()+shape.getWidth()-tempX;
			}
			if(shape.getY()+shape.getHeight()>tempY+tempHeight){
				tempHeight=shape.getY()+shape.getHeight()-tempY;
			}
		}
		this.x = tempX;
		this.y = tempY;
		this.width = tempWidth;
		this.height = tempHeight;
	}


}
