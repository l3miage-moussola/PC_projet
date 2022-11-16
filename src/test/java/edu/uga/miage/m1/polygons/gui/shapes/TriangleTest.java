package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

class TriangleTest {
	
	private int counterVisitorCircle;
	private int counterVisitorTriangle;
	private int counterVisitorSquare;

	@Test
	void test_getters() {
		Triangle t = new Triangle(0, 0);
		assertEquals(0, t.getX());
		assertEquals(0, t.getY());
	}
	
	void test_visit_with_mock(@Mock Visitor v) {
		Circle c = new Circle(0, 0);		
		c.accept(v);
		verify(v, times(1)).visit(c);
	}

	@Test
	void test_visit_without_mockito() {
				
		Triangle t = new Triangle(0, 0);
		
		Visitor v = new Visitor() {
			
			@Override
			public void visit(Circle circle) {
				counterVisitorCircle++;			
			}

			@Override
			public void visit(Square square) {
				// TODO Auto-generated method stub
				counterVisitorSquare++;
				
			}

			@Override
			public void visit(Triangle triangle) {
				// TODO Auto-generated method stub
				counterVisitorTriangle++;
			}
			
		};
		
		t.accept(v);
		assertEquals(0, counterVisitorCircle);
		assertEquals(1, counterVisitorTriangle);
		assertEquals(0, counterVisitorSquare);
		

	}

}
