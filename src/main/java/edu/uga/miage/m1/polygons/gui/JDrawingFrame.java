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
import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;



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
	private enum Shapes {SQUARE, TRIANGLE, CIRCLE}
	private static final long serialVersionUID = 1L;
	private JToolBar mToolbar;
	private Shapes mSelected;
	private JPanel mPanel;
	private JLabel mLabel;
	private ActionListener mReusableActionListener = new ShapeActionListener();


	/**
	 * Tracks buttons to manage the background.
	 */
	private EnumMap<Shapes, JButton> mButtons = new EnumMap<>(Shapes.class);
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
		mLabel = new JLabel(" ", JLabel.LEFT);

		// Fills the panel
		setLayout(new BorderLayout());
		add(mToolbar, BorderLayout.NORTH);
		add(mPanel, BorderLayout.CENTER);
		add(mLabel, BorderLayout.SOUTH);

		// Add shapes in the menu
		addShape(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
		addShape(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
		addShape(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));
		addExport("XML");
		addExport("JSON");
		addImport();

		setPreferredSize(new Dimension(400, 400));
	}


	/**
	 * Injects an available <tt>SimpleShape</tt> into the drawing frame.
	 * @param name The name of the injected <tt>SimpleShape</tt>.
	 * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
	 **/
	private void addShape(Shapes shape, ImageIcon icon)
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
	private void addExport(String exportType) {
		JButton button = new JButton(exportType);
		eButtons.put(exportType, button);
		button.setActionCommand(exportType);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch(exportType) {
				case "XML" : Export export = new Export();
							 export.convertToXml(shapesList);
							 break;
				case "JSON" :Export exportJ = new Export(); 
							 exportJ.convertToJson(shapesList);
							break;
				default : break;
				
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
				importF.importFile();
				for(SimpleShape shape : importF.getList()) {
					if (mPanel.contains(shape.getX(), shape.getY()))
					{
						Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
						shape.draw(g2);
						shapesList.add(shape);
					}
				}
				
			}
			
		});
		mToolbar.add(button);
		mToolbar.validate();
		
	}

	/**
	 * TODO Use the factory to abstract shape creation
	 * Implements method for the <tt>MouseListener</tt> interface to
	 * draw the selected shape into the drawing canvas.
	 * @param evt The associated mouse event.
	 **/
	List<SimpleShape> shapesList = new ArrayList<>();
	public void mouseClicked(MouseEvent evt)
	{

		if (mPanel.contains(evt.getX(), evt.getY()))
		{
			Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
			switch(mSelected)
			{
			case CIRCLE: 		new Circle(evt.getX(), evt.getY()).draw(g2);
			shapesList.add(new Circle(evt.getX(), evt.getY()));
			break;
			case TRIANGLE: 		new Triangle(evt.getX(), evt.getY()).draw(g2);
			shapesList.add(new Triangle(evt.getX(), evt.getY()));
			break;
			case SQUARE: 		new Square(evt.getX(), evt.getY()).draw(g2);
			shapesList.add(new Square(evt.getX(), evt.getY()));
			break;
			default: 			System.out.println("No shape named " + mSelected);

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
	public void mousePressed(MouseEvent evt)
	{
	}

	/**
	 * Implements method for the <tt>MouseListener</tt> interface to complete
	 * shape dragging.
	 * @param evt The associated mouse event.
	 **/
	public void mouseReleased(MouseEvent evt)
	{
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
			Iterator<Shapes> keys = mButtons.keySet().iterator();
			while (keys.hasNext()) {
				Shapes shape = keys.next();
				JButton btn = mButtons.get(shape);
				if (evt.getActionCommand().equals(shape.toString())) {
					btn.setBorderPainted(true);
					mSelected = shape;
				} else {
					btn.setBorderPainted(false);
				}
				btn.repaint();
			}
		}
	}

	private class Export {
		public void  convertToXml(List<SimpleShape> shapesList) {
			XMLVisitor visitor = new XMLVisitor();
			File file = new File("shapes.xml");
			String result ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n <root>\n";
			for(SimpleShape shape : shapesList) {
				 switch(shape.getClass().getSimpleName()) {
				 case "Circle" : new Circle(shape.getX(),shape.getY()).accept(visitor);
					 result=result+visitor.getRepresentation();
					 break;
				 case "Square" : new Square(shape.getX(),shape.getY()).accept(visitor);
				 				 result=result+visitor.getRepresentation();
				 				 break;
				 case "Triangle" :  new Triangle(shape.getX(),shape.getY()).accept(visitor);
				 					result=result+visitor.getRepresentation();
				 				    break;
				} 
			}
			result+="\n </root>";
			try {
				FileWriter writter = new FileWriter(file);
				writter.write(result);
				writter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void convertToJson(List<SimpleShape> shapesList) {
			JSonVisitor visitor = new JSonVisitor();
			File file = new File("shapesJson.json");
			String result ="{\n \"Shapes\":[\n";
			for(SimpleShape shape : shapesList) {
				 switch(shape.getClass().getSimpleName()) {
				 case "Circle" : new Circle(shape.getX(),shape.getY()).accept(visitor);
					 result=result+visitor.getRepresentation();
					 break;
				 case "Square" : new Square(shape.getX(),shape.getY()).accept(visitor);
				 				 result=result+visitor.getRepresentation();
				 				 break;
				 case "Triangle" :  new Triangle(shape.getX(),shape.getY()).accept(visitor);
				 					result=result+visitor.getRepresentation();
				 				    break;
				} 
			}
			result =result.replace("}{","},{");
			result+="\n]\n}";
			try {
				FileWriter writter = new FileWriter(file);
				writter.write(result);
				writter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class ImportFiles{
		private List<SimpleShape> shapesList = new ArrayList<>();
		public void importFile(){
			FileDialog fd = new FileDialog(new JFrame());
			fd.setVisible(true);
			File f = fd.getFiles()[0];
			String representation="";
			try {
			  representation = Files.readString(Paths.get(f.getAbsolutePath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String fileType = f.getAbsolutePath().substring( f.getAbsolutePath().lastIndexOf(".")+1);
			if(fileType.equals("xml")) {
				xmlImport(representation);
			}
			if(fileType.equals("json")) {
				jsonImport(representation);
			}
			
		}
		private void xmlImport(String rep) {
			System.out.print(rep.contains("/type")+"\n");
			while(rep.contains("/type")) {
				String type ="";
				int x = 0 ;
				int y = 0;
				int debut = rep.indexOf("type") +5;
				int fin = rep.indexOf("/type")-2;
				for(int i=debut;i<=fin;i++) {
					type+=rep.charAt(i);
				}
				System.out.print("\n le type"+type);
				String xoccur = "";
				int xDeb=rep.indexOf("<x>")+3;
				System.out.print("\n le xdeb "+xDeb);
				int xFin=rep.indexOf("</x>");
				System.out.print("\n le xdxFineb "+xFin);
				for(int i=xDeb;i<xFin;i++) {
					xoccur+=rep.charAt(i);
				}
				System.out.print("X: "+x);
				x=Integer.parseInt(xoccur);
				
				String yoccur = "";
				int yDeb=rep.indexOf("<y>")+3;;
				int yFin=rep.indexOf("</y>");
				for(int i=yDeb;i<yFin;i++) {
					yoccur+=rep.charAt(i);
				}	
				System.out.print("Y: "+y);
				y=Integer.parseInt(yoccur);
				
				rep = rep.substring(rep.indexOf("</shape>")+5);
				shapesList.add(createShape(type,x,y));
			}
		}
		private void jsonImport(String rep) {
			System.out.print("Rep :" +rep);
			while(rep.contains("type")) {
				String type = "";
				int x=0;
				int y=0;
				int deb = rep.indexOf("type")+8;
				int fin = rep.indexOf(",")-1;
				for(int i=deb;i<fin;i++) {
					type+= rep.charAt(i);
				}
				System.out.print("\n le type"+type);
				String xoccur="";
				int xdeb=rep.indexOf("\"x\"")+4;
				int ydeb=rep.indexOf("\"y\"");
				for(int i=xdeb;i<=ydeb-2;i++) {
					xoccur+=rep.charAt(i);
				}
				String yoccur="";
				for(int i=ydeb+4;i<rep.indexOf("}");i++) {
					yoccur+=rep.charAt(i);
				}
				
				x=Integer.parseInt(xoccur);
				System.out.print("X :" +x);
				
				y=Integer.parseInt(yoccur);
				System.out.print("Y :" +y);
				rep=rep.substring(rep.indexOf("}")+2);
				System.out.print("Rep :" +rep);
				shapesList.add(createShape(type,x,y));
			}
		}
		public SimpleShape createShape(String type,int x , int y){
			
			switch(type) {
			case "circle" : return new Circle(x,y);
			case "square" : return new Square(x,y);
			case "triangle" : return new Triangle(x,y);
			default : break;
			}
			return null;
		}
		public List<SimpleShape> getList() {
			return this.shapesList;
		}
	}

}