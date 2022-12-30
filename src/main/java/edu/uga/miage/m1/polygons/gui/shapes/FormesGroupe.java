package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormesGroupe extends AbstractShape {

	protected FormesGroupe() {
		super(0,0,0,0);
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
	
	public int getXmin() {
		int xmin =shapesList.get(0).getX();
		for(SimpleShape shape : shapesList) {
			if(shape.getX()<=xmin) {
				xmin=shape.getX();
			}
		}
		return xmin;
	}
	public int getXmax() {
		int xmax=0;
		for(SimpleShape shape : shapesList) {
			if(shape.getX()>=xmax) {
				xmax=shape.getX();
			}
		}
		return xmax;
	}
	
	public int getYmin() {
		int ymin =shapesList.get(0).getX();
		for(SimpleShape shape : shapesList) {
			if(shape.getY()<=ymin) {
				ymin=shape.getY();
			}
		}
		return ymin;
	}
	public int getYmax() {
		int ymax=0;
		for(SimpleShape shape : shapesList) {
			if(shape.getX()>=ymax) {
				ymax=shape.getX();
			}
		}
		return ymax;
	}
	


	@Override
	public void accept(Visitor visitor) {

	}
}
