package edu.uga.miage.m1.polygons.gui;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import javax.xml.parsers.ParserConfigurationException;
import edu.uga.miage.m1.polygons.gui.persistence.*;
import edu.uga.miage.m1.polygons.gui.shapes.*;
import org.xml.sax.SAXException;


/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 * 
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public class JDrawingFrame extends JFrame
implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	private JToolBar mToolbar;
	private TypeShape mSelected;
	private JPanel mPanel;
	private JLabel mLabel;
	private ActionListener mReusableActionListener = new ShapeActionListener();
	private static final Logger logger = Logger.getLogger(JDrawingFrame.class.getName());
	boolean groupeCreation = false;
	UndoManager undoManager = new UndoManager();

	



	/**
	 * Tracks buttons to manage the background.
	 */
	private EnumMap<TypeShape, JButton> mButtons = new EnumMap<>(TypeShape.class);
	private Map<String, JButton> eButtons = new HashMap<>();

	/**
	 * Default constructor that populates the main window.
	 * @param frameName 
	 **/
	public JDrawingFrame(String frameName)
	{
		super(frameName);
		// Instantiates components
		mToolbar = new JToolBar("Toolbar");
		mPanel = new JPanel();
		mPanel.setBackground(Color.WHITE);
		mPanel.setLayout(null);
		mPanel.setMinimumSize(new Dimension(400, 400));
		mPanel.addMouseListener(this);
		mPanel.addMouseMotionListener(this);
		mPanel.addKeyListener(new KeyAdapter() {
			   public void keyPressed(KeyEvent e) {
			       if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
			           if (!shapesList.isEmpty()) {
			        	   annuler();
			           }
			       }
			   }
			});
		mLabel = new JLabel(" ", SwingConstants.LEFT);
		
		// Fills the panel
		setLayout(new BorderLayout());
		add(mToolbar, BorderLayout.NORTH);
		add(mPanel, BorderLayout.CENTER);
		add(mLabel, BorderLayout.SOUTH);

		// Add shapes in the menu
		addShape(TypeShape.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
		addShape(TypeShape.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
		addShape(TypeShape.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));
		addExport("XML");
		addExport("JSON");
		addImport();
		addGroupe();
		addUndo();
		setPreferredSize(new Dimension(400, 400));
	}
	


	/**
	 * Injects an available <tt>SimpleShape</tt> into the drawing frame.
	 * @param shape The name of the injected <tt>SimpleShape</tt>.
	 * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
	 **/
	private void addShape(TypeShape shape, ImageIcon icon)
	{
		JButton button = new JButton(icon);
		button.setBorderPainted(false);
		mButtons.put(shape, button);
		button.setActionCommand(shape.toString());
		button.addActionListener(mReusableActionListener);

		if (mSelected == null)
		{
			button.doClick();
		}

		mToolbar.add(button);
		mToolbar.validate();
		repaint();
	}
	private void addUndo() {
		JButton undoButton = new JButton("clear");
		eButtons.put("Annuler", undoButton);
		undoButton.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
			       if (shapesList.size()>0) {
			           annuler();
			       }
			   }
			});
		mToolbar.add(undoButton);
		mToolbar.validate();
	}
	private void annuler() {
		mPanel.repaint();
		shapesList.clear();
	}
	private void addExport(String exportType) {
		JButton button = new JButton(exportType);
		eButtons.put(exportType, button);
		button.setActionCommand(exportType);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch(exportType) {
				case "XML" : Export export = new Export();
				try {
					export.convertToXml(shapesList);
				} catch (IOException e1) {
					logger.log(new LogRecord(Level.INFO,"IOException while converting to XML"));
				}
				break;
				case "JSON" :Export exportJ = new Export(); 
				exportJ.convertToJson(shapesList);
				break;
				default :

				}

			}

		});
		mToolbar.add(button);
		mToolbar.validate();
	}
	
	public void addGroupe() {
        JButton button = new JButton("CreateNewGroupe");
        button.setActionCommand("CreateNewGroupe");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	groupeCreation=!groupeCreation;
            	if(!groupeCreation) {
                	FormesGroupe groupe = (FormesGroupe) ShapeFactory.createShape(0,0,TypeShape.GROUP);
                	shapesList.add(groupe);
                	List<SimpleShape> shapesToRemove = new ArrayList<>();
                	for(SimpleShape shape : shapesList) {
                		if(shape.getSelected()) {
                			groupe.add(shape);
                			shapesToRemove.add(shape);
                			
                		}
                	
            	}
                	for(SimpleShape shape : shapesToRemove) {
                		shapesList.remove(shape);
                	}

}
            	
                
            }
            
        });
        mToolbar.add(button);
        mToolbar.validate();
    }

	private void addImport(){
		JButton button = new JButton("Import");
		button.setActionCommand("Import");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ImportFiles importF = new ImportFiles();
				try {
					importF.importFile();
				} catch (ParserConfigurationException ex) {
					throw new RuntimeException(ex);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				} catch (SAXException ex) {
					throw new RuntimeException(ex);
				}
				for(SimpleShape simpleShape : importF.getList()) {
					if (mPanel.contains(simpleShape.getX(), simpleShape.getY()))
					{
						Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
						simpleShape.draw(g2);
						shapesList.add(simpleShape);
					}
				}

			}

		});
		mToolbar.add(button);
		mToolbar.validate();

	}
	
	

	/**
	 * Implements method for the <tt>MouseListener</tt> interface to
	 * draw the selected shape into the drawing canvas.
	 * @param evt The associated mouse event.
	 **/
	List<SimpleShape> shapesList = new ArrayList<>();
	public void mouseClicked(MouseEvent evt)
	{

		if (mPanel.contains(evt.getX(), evt.getY()))
		{
			if(groupeCreation) {
				for(SimpleShape shape : shapesList) {
					if(shape.isInside(evt.getX(), evt.getY())) {
						shape.setSelected();
					}
				}		
			}
			else {
				Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
				SimpleShape shape = ShapeFactory.createShape(evt.getX(), evt.getY(), mSelected);
				shapesList.add(shape);
				shape.draw(g2);
			}

		}

	}
	

	/**
	 * Implements an empty method for the <tt>MouseListener</tt> interface.
	 * @param evt The associated mouse event.
	 **/
	public void mouseEntered(MouseEvent evt)
	{

	}

	/**
	 * Implements an empty method for the <tt>MouseListener</tt> interface.
	 * @param evt The associated mouse event.
	 **/
	public void mouseExited(MouseEvent evt)
	{
		mLabel.setText(" ");
		mLabel.repaint();
	}

	/**
	 * Implements method for the <tt>MouseListener</tt> interface to initiate
	 * shape dragging.
	 * @param evt The associated mouse event.
	 **/
	SimpleShape movingShape=null;
	int xDeb=0;
	int yDeb=0;
	boolean isShapeSlected;
	public void mousePressed(MouseEvent evt)
	{
		if(!groupeCreation) {
			xDeb=evt.getX();
			yDeb=evt.getY();
			for(SimpleShape shape : shapesList) {
				if(shape.isInside(evt.getX(), evt.getY())){
					shape.setSelected();
				}
				
			}	
		}
	}
	
	private void ClearAndDraw() {
		Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
		g2.clearRect(0, 0, mPanel.getWidth(), mPanel.getHeight());
		for(SimpleShape shape : shapesList) {
			shape.draw(g2);
		}
	}

	/**
	 * Implements method for the <tt>MouseListener</tt> interface to complete
	 * shape dragging.
	 * @param evt The associated mouse event.
	 **/
	boolean moveGroupe=false;
	public void mouseReleased(MouseEvent evt)
	{	
		
		if(!groupeCreation && mPanel.contains(evt.getX(), evt.getY())) {
			for(SimpleShape shape : shapesList) {
				if(shape.getSelected()) {
					shape.move((evt.getX()-xDeb),(evt.getY()-yDeb));
					shape.setSelected();
				}
			}
			
			
	
		}
		ClearAndDraw();

	}

	/**
	 * Implements method for the <tt>MouseMotionListener</tt> interface to
	 * move a dragged shape.
	 * @param evt The associated mouse event.
	 **/
	public void mouseDragged(MouseEvent evt)
	{   
	}

	/**
	 * Implements an empty method for the <tt>MouseMotionListener</tt>
	 * interface.
	 * @param evt The associated mouse event.
	 **/
	public void mouseMoved(MouseEvent evt)
	{
		modifyLabel(evt);
	}

	private void modifyLabel(MouseEvent evt) {
		mLabel.setText("(" + evt.getX() + "," + evt.getY() + ")");    	
	}

	/**
	 * Simple action listener for shape tool bar buttons that sets
	 * the drawing frame's currently selected shape when receiving
	 * an action event.
	 **/
	private class ShapeActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			// Itère sur tous les boutons
			Iterator<TypeShape> keys = mButtons.keySet().iterator();
			while (keys.hasNext()) {
				TypeShape shapeGroup = keys.next();
				JButton btn = mButtons.get(shapeGroup);
				if (evt.getActionCommand().equals(shapeGroup.toString())) {
					btn.setBorderPainted(true);
					mSelected = shapeGroup;
				} else {
					btn.setBorderPainted(false);
				}
				btn.repaint();
			}
		}
	}
}