package br.com.dev;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Mapa {

	private int desloc_x;
	private int desloc_y;
	ImageIcon fundo;

	Mapa(int w, int h) {
		fundo = new ImageIcon("src/img/mapa_each.png");
		desloc_x = -1 * ((fundo.getIconWidth() / 2) - (w / 2));
		desloc_y = -1 * ((fundo.getIconHeight() / 2) - (h / 2));
	}

	public void desenharMapa(BufferedImage bi, Graphics g, ImageObserver io) {
		Graphics bbg = bi.getGraphics();
		bbg.drawImage(fundo.getImage(), desloc_x, desloc_y, io);
	}

	public boolean andar_direita() {
		if((Math.abs(desloc_x)+(Game.janelaW/2) + Sprite.width) > fundo.getIconWidth())
			return false;
		desloc_x--;
		return true;
	}

	public boolean andar_esquerda() {
		if((desloc_x) > Game.janelaW/2 - (Sprite.width/3))
			return false;
		desloc_x++;
		return true;
	}

	public boolean andar_cima() {
		if((desloc_y) > Game.janelaH/2 + (Sprite.height/2))
			return false;
		desloc_y++;
		return true;
	}

	public boolean andar_baixo() {
		if((Math.abs(desloc_y)+(Game.janelaH/2) + (Sprite.height)) > fundo.getIconHeight())
			return false;
		desloc_y--;
		return true;
	}

}
