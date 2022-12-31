package edu.uga.miage.m1.polygons.gui;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.uga.miage.m1.polygons.gui.shapes.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.TypeShape;


public class ImportFiles{
	private static final  String TYPESTRING ="type";
	private List<SimpleShape> shapesList = new ArrayList<>();
	public void importFile() throws ParserConfigurationException, IOException, SAXException {
		FileDialog fd = new FileDialog(new JFrame());
		fd.setVisible(true);
		File f = fd.getFiles()[0];
		String representation="";
		try {
			representation = Files.readString(Paths.get(f.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileType = f.getAbsolutePath().substring( f.getAbsolutePath().lastIndexOf(".")+1);
		if(fileType.equals("xml")) {
			xmlImport(f);
		}
		if(fileType.equals("json")) {
			jsonImport(representation);
		}


	}
	private void xmlImport(File rep) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(rep.getAbsoluteFile());
		doc.getDocumentElement();

		NodeList nodeList = doc.getElementsByTagName("shape");
		Element	node;
		for (int i = 0; i < nodeList.getLength(); i++) {
			node = (Element) nodeList.item(i);
			String type = node.getElementsByTagName("type").item(0).getTextContent();
			int x = Integer.parseInt(node.getElementsByTagName("x").item(0).getTextContent());
			int y = Integer.parseInt(node.getElementsByTagName("y").item(0).getTextContent());
			SimpleShape simpleShape = ShapeFactory.createShape(x,y, TypeShape.valueOf(type),null);
			shapesList.add(simpleShape);


		}

	}
	private void jsonImport(String rep) {

		while(rep.contains(TYPESTRING)) {
			StringBuilder stringBuilderType = new StringBuilder();
			
			int x=0;
			int y=0;
			int deb = rep.indexOf(TYPESTRING)+8;
			int fin = rep.indexOf(",")-1;
			for(int i=deb;i<fin;i++) {
				stringBuilderType.append(rep.charAt(i)); 
			}
			String type = stringBuilderType.toString();
			StringBuilder stringBuilderxoccur = new StringBuilder();
			int xdeb=rep.indexOf("\"x\"")+4;
			int ydeb=rep.indexOf("\"y\"");
			for(int i=xdeb;i<=ydeb-2;i++) {
				stringBuilderxoccur.append(rep.charAt(i));
			}
			String yoccur="";
			for(int i=ydeb+4;i<rep.indexOf("}");i++) {
				yoccur+=rep.charAt(i);
			}
			String xoccur= stringBuilderxoccur.toString();
			x=Integer.parseInt(xoccur);

			y=Integer.parseInt(yoccur);

			rep=rep.substring(rep.indexOf("}")+2);

			shapesList.add(ShapeFactory.createShape(x,y, TypeShape.valueOf(type.toUpperCase()),null));
		}
	}

	public List<SimpleShape> getList() {
		return this.shapesList;
	}
}
