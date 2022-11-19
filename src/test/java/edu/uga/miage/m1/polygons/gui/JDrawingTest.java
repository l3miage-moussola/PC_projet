package edu.uga.miage.m1.polygons.gui;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
	
	@Test
	void testMouseClicked(@Mock MouseEvent evt) {
		JDrawingFrame frame = new JDrawingFrame("TestFrame");
		
		frame.mouseClicked(evt);
		frame.mouseClicked(new MouseEvent(frame, 0, 0, 0, 150, 200, 0, true));
		frame.mouseDragged(evt);
		frame.mouseDrag(null, 0, 0);
		verify(evt, times(1)).getX();

	}


}
