package br.com.dev;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Mapa {

	
	
	
	
	ImageIcon fundo = new ImageIcon("src/img/mapa_each.png");
	
	public void desenharMapa(BufferedImage bi, Graphics g, ImageObserver io) {
		Graphics bbg = bi.getGraphics();
		bbg.drawImage(fundo.getImage(), 0, 0, io);
		
		
		g.drawImage(bi, 0, 0, io);
	}
	
	
}
