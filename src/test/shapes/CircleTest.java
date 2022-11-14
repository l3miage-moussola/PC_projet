package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

@ExtendWith(MockitoExtension.class)
class CircleTest {
	
	@Mock
	Visitor v;

	@Test
	void test_getters() {
		Circle c = new Circle(0, 0);
		assertEquals(-25, c.getX());
		assertEquals(-25, c.getY());
	}

}
