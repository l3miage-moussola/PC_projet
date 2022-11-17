package edu.uga.miage.m1.polygons.gui;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JDrawingTest {
	
	@Test
	void testFrameInstantiation() {
		
		JDrawingFrame frame = new JDrawingFrame("TestFrame");
		assertNotEquals(null, frame);		
	}
	
	void testMouseClicked(@Mock MouseEvent evt) {
		JDrawingFrame frame = new JDrawingFrame("TestFrame");
		frame.mouseClicked(evt);
		verify(evt, times(1)).consume();

	}


}
