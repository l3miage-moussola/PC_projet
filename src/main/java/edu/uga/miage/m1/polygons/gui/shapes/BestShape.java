package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

public class BestShape extends AbstractShape  {
	private Image image;
	private static final Toolkit toolkit = Toolkit.getDefaultToolkit();
	public BestShape(int x, int y , BufferedImage imageBuffer) {
		super(x, y);
		this.image = toolkit.createImage(imageBuffer.getSource());
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, null);
		g2.dispose();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
	
	

}
