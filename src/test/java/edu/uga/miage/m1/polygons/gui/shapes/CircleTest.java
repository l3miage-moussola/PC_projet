package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;

@ExtendWith(MockitoExtension.class)
class CircleTest {
	
	private int counterVisitorCircle;

	@Test
	void test_getters() {
		Circle c = new Circle(0, 0);
		assertEquals(0, c.getX());
		assertEquals(0, c.getY());
	}
	
	@Test
	void test_visit_with_mock(@Mock Visitor v) {
		Circle c = new Circle(0, 0);		
		c.accept(v);
		verify(v, times(1)).visit(c);
	}
	
	@Test
	void test_draw_with_mock(@Mock Graphics2D graph) {
		Circle c = new Circle(0, 0);		
		c.draw(graph);
		verify(graph, times(1)).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);;
	}
	

	@Test
	void test_visit_without_mockito() {
				
		Circle c = new Circle(0, 0);
		
		Visitor v = new Visitor() {
			
			@Override
			public void visit(Circle circle) {
				counterVisitorCircle++;			
			}

			@Override
			public void visit(Square square) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void visit(Triangle triangle) {
				// TODO Auto-generated method stub
				
			}
			
		};	
		
		c.accept(v);
		assertEquals(1, counterVisitorCircle);
		
	}
	
	@Test
	void test_XMLVisitor_visit_circle() {
				
		Circle c = new Circle(0, 0);
		
		XMLVisitor v = new XMLVisitor();
		
		v.visit(c);
		
		String representation = v.getRepresentation();
		
		c.accept(v);
		assertEquals("<shape><type>circle</type><x>0</x><y>0</y></shape>",representation);
	}
}
