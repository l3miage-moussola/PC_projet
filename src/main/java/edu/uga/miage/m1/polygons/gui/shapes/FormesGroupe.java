package edu.uga.miage.m1.polygons.gui.shapes;

import java.util.List;

public class FormesGroupe {
	private List<SimpleShape> shapesList;
	public List<SimpleShape> getGroupeForms(){
		return shapesList;
	}
	public void add(SimpleShape shape) {
		this.shapesList.add(shape);
	}
}
