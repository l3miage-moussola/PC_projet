package edu.uga.miage.m1.polygons.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

class Export {
	public String setFileLocation(String fileName,FileNameExtensionFilter filter) {
		 JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("Veuillez choisir l'emplacement de votre fichier");
	        fileChooser.setSelectedFile(new File(fileName));
	        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
	        fileChooser.setFileFilter(filter);
	        fileChooser.setAcceptAllFileFilterUsed(false);
	        int returnValue = fileChooser.showDialog(null,"Save");
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	            return fileChooser.getSelectedFile().getAbsolutePath();
	        }
		return null;
	}
	public void  convertToXml(List<SimpleShape> shapesList) throws IOException {
		String fileLocation = setFileLocation("shapes.xml", new FileNameExtensionFilter("XML file", "xml"));
		if(fileLocation!=null) {
			if(!fileLocation.endsWith(".xml")) {
				fileLocation+=".xml";
			}
		}
		XMLVisitor visitor = new XMLVisitor();
		File file = new File(fileLocation);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n <root>\n");
		for(SimpleShape localSimpleShape : shapesList) {

			localSimpleShape.accept(visitor);
			stringBuilder.append(visitor.getRepresentation());
		}
		stringBuilder.append("\n </root>");

		String result = stringBuilder.toString();


		try (FileWriter writter = new FileWriter(file);) {
			writter.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	public void convertToJson(List<SimpleShape> shapesList) {
		String fileLocation = setFileLocation("shapes.json", new FileNameExtensionFilter("JSON file", "json"));
		if(fileLocation!=null) {
			if(!fileLocation.endsWith(".json")) {
				fileLocation+=".json";
			}
		}
		JSonVisitor visitor = new JSonVisitor();
		File file = new File(fileLocation);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n \"Shapes\":[\n");

		for(SimpleShape localSimpleShape : shapesList) {

			localSimpleShape.accept(visitor);
			stringBuilder.append(visitor.getRepresentation());
		}
		String result = stringBuilder.toString();
		result =result.replace("}{","},{");
		
		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append(result+"\n]\n}");

		result=stringBuilder2.toString();
		try {
			FileWriter writter = new FileWriter(file);
			writter.write(result);
			writter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
