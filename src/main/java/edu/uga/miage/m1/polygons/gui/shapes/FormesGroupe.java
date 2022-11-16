package edu.uga.miage.m1.polygons.gui.shapes;

import java.io.Serializable;
import java.util.List;

public class FormesGroupe implements Serializable{
	
	private static final long serialVersionUID = 5368906687199772837L;
	private List<SimpleShape> shapesList;
	public List<SimpleShape> getGroupeForms(){
		return shapesList;
	}
	public void add(SimpleShape shape) {
		this.shapesList.add(shape);
	}
}
