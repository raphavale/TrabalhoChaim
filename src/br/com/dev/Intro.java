package br.com.dev;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Intro {

	ImageIcon fundo, logo, grimmer, enter;

	public Intro(int w, int h) {
		fundo = new ImageIcon("src/img/char/intro/intro.png");
		logo  = new ImageIcon("src/img/char/intro/logo.png");
		grimmer = new ImageIcon("src/img/char/intro/grimmer.png");
		enter = new ImageIcon("src/img/char/intro/enter.png");
	}
	
	public int getWidth(){
		return fundo.getIconWidth();
	}
	
	public int getHeight(){
		return fundo.getIconHeight();
	}

	public void desenharIntro(BufferedImage bi, Graphics g, ImageObserver io, long t) {
		Graphics bbg = bi.getGraphics();
		bbg.drawImage(fundo.getImage(),0,0, io);
		int x = 41, y = -105;
		
		if ( t > 150 && t < 450 )
			bbg.drawImage(grimmer.getImage(),(int) (1200 - (t*4)), 160, 250, 250, io);
		else if (t > 450 && t <= 600)
			bbg.drawImage(grimmer.getImage(),(int) (2500 - (t*3.666)), 160, 250, 250, io);
		else if (t > 600)
			bbg.drawImage(grimmer.getImage(), 300, 160, 250, 250, io);
		
		if ( t < 100 )
			bbg.drawImage(logo.getImage(), x, (int)  (t + y), io);
		else
			bbg.drawImage(logo.getImage(), x, -5, io);
		
		if ( t > 750 )
			bbg.drawImage(enter.getImage(), 30, 150, io);
		
		
	}
	
}