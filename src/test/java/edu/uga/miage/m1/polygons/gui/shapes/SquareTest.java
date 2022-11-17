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
class SquareTest {
	
	@Test
	void test_draw_with_mock(@Mock Graphics2D graph) {
		Square s = new Square(0, 0);		
		s.draw(graph);
		verify(graph, times(1)).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}	

	@Test
	void test_getters() {
		Square s = new Square(0, 0);
		assertEquals(0, s.getX());
		assertEquals(0, s.getY());
	}
	
	@Test
	void testXMLVisitorVisitSquare() {
				
		Square s = new Square(0, 0);
		
		XMLVisitor v = new XMLVisitor();
		
		v.visit(s);
		
		String representation = v.getRepresentation();
		
		s.accept(v);
		assertEquals("<shape><type>square</type><x>0</x><y>0</y></shape>",representation);
	}

}
