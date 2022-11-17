package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;

@ExtendWith(MockitoExtension.class)
class TriangleTest {
	
	@Test
	void test_draw_with_mock(@Mock Graphics2D graph) {
		Triangle t = new Triangle(0, 0);		
		t.draw(graph);
		verify(graph, times(1)).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Test
	void test_getters() {
		Triangle t = new Triangle(0, 0);
		assertEquals(0, t.getX());
		assertEquals(0, t.getY());
	}
	
	@Test
	void testXMLVisitorVisitTriangle() {
				
		Triangle t = new Triangle(0, 0);
		
		XMLVisitor v = new XMLVisitor();
		
		v.visit(t);
		
		String representation = v.getRepresentation();
		
		t.accept(v);
		assertEquals("<shape><type>triangle</type><x>0</x><y>0</y></shape>",representation);
	}
	


}
