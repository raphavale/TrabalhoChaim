package br.com.dev;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Monstro {

	public static final short width = 60;
	public static final short height = 60;
	private byte cena_atual = 0;
	private ImageIcon spr[];
	private int x;
	private int y;
	private long time = System.currentTimeMillis();

	Monstro(int x, int y) {
		spr = new ImageIcon[4];
		for (int i = 0; i < spr.length; i++) {
			spr[i] = new ImageIcon("src/img/char/monstro/grimmer_" + (i + 1)
					+ ".png");
		}
		this.x = x;
		this.y = y;
	}

	public void desenharSprite(BufferedImage bi, Graphics g, ImageObserver io) {
		Graphics bbg = bi.getGraphics();
		bbg.drawImage(spr[cena_atual].getImage(), x, y, width, height, io);
	}

	public void animar() {
		if (System.currentTimeMillis() - time > 300) {
			time = System.currentTimeMillis();
		} else {
			return;
		}

		// se já faz mais de um segundo, faz a ação

		if (cena_atual % 2 == 1)
			cena_atual--;
		else
			cena_atual++;

	}

	// 'andar' com o mapa
	void andar_direita() {
		x--;
	}
	void andar_esquerda() {
		x++;
	}

	void andar_cima() {
		y++;
	}

	void andar_baixo() {
		y--;
	}

}
