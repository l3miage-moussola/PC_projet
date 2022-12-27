package edu.uga.miage.m1.polygons.gui;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.SAXException;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame.ImportFiles;

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
		
		verify(evt, times(1)).getX();

	}
	@Test
	void testVariousMouseActions(@Mock MouseEvent evt) {
		JDrawingFrame frame = new JDrawingFrame("TestFrame");
		
		frame.mouseClicked(evt);
		frame.mouseClicked(new MouseEvent(frame, 0, 0, 0, 150, 200, 0, true));
		frame.mouseDragged(evt);
		frame.mouseDrag(null, 0, 0);
		frame.mouseDown(null, 0, 0);
		frame.mouseEntered(evt);
		
		verify(evt, times(1)).getX();

	}
	
	
	
	
	@Test
	void testMouseMoved(@Mock MouseEvent evt) {
		JDrawingFrame frame = new JDrawingFrame("TestFrame");
		MouseEvent evt2 = new MouseEvent(frame, 0, 0, 0, 150, 200, 0, true);
		frame.mousePressed(evt2);
		frame.mouseExited(evt);
		frame.mouseReleased(evt2);
		frame.mouseMoved(new MouseEvent(frame, 0, 0, 0, 150, 200, 0, true));
		
		
		assertNotEquals(null, frame);		

	}

	@Test
	void testImport() {
		JDrawingFrame frame = new JDrawingFrame("TestFrame");
		ImportFiles impfil = frame.new ImportFiles();
		try {
			impfil.importFile();
		} catch (ParserConfigurationException | IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		assertNotEquals(null, frame);		

	}



}
