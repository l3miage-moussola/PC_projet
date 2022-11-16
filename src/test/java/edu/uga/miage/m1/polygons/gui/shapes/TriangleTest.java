package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

class TriangleTest {

	@Test
	void test_getters() {
		Triangle t = new Triangle(0, 0);
		assertEquals(0, t.getX());
		assertEquals(0, t.getY());
	}
	
	@Test
	void testFrameInstantiation() {
		JDrawingFrame frame = new JDrawingFrame("TriangleTestFrame");		
		assertNotEquals(null, frame);

	}
	@Test
	void testDraw() {
		JDrawingFrame frame = new JDrawingFrame("TriangleTestFrame");		
		assertNotEquals(null, frame);

	}

}
