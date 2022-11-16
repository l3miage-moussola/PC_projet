package edu.uga.miage.m1.polygons.gui.shapes;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FormesGroupTest {
	
	public List<SimpleShape> listeDesFormes = new ArrayList<>();
	@Test
	void testGetters() {
		Circle c = new Circle(0,0);
		Triangle t = new Triangle(0,25);
		listeDesFormes.add(t);
		listeDesFormes.add(c);
		
	
		FormesGroupe groupeDeFormes = new FormesGroupe();
		
		groupeDeFormes.add(t);
		groupeDeFormes.add(c);
		

		
		assertEquals(groupeDeFormes.getGroupeForms(),listeDesFormes);
	}
	

}
