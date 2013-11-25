package br.com.dev;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

import javax.swing.ImageIcon;

public class Monstro {

	private int vida = 3;
	public static final short width = 60;
	public static final short height = 60;
	private byte cena_atual = 0;
	private ImageIcon spr[];
	private int x;
	private int y;
	private long time = System.currentTimeMillis();
	private boolean is_chefe;
	
	Monstro(int x, int y, int level, boolean is_chefe) {
		this.is_chefe = is_chefe;
		if(is_chefe)
		{
			spr = new ImageIcon[4];
			for (int i = 0; i < spr.length; i++) {
				spr[i] = new ImageIcon("src/img/char/monstro/boss_" + (i + 1)
						+ ".png");
			}
			this.x = x;
			this.y = y;
			this.vida *= level*10;
		} 
		else {
			spr = new ImageIcon[4];
			for (int i = 0; i < spr.length; i++) {
				spr[i] = new ImageIcon("src/img/char/monstro/grimmer_" + (i + 1)
						+ ".png");
			}
			this.x = x;
			this.y = y;
			this.vida *= level;
		}
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

		if (cena_atual % 2 == 1)
			cena_atual--;
		else
			cena_atual++;

	}

	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getVida(){
		return vida;
	}
	//Retorna true se o mob morreu.
	public boolean tomar_dano(Player p){
		long t = System.currentTimeMillis();
		x = x+10;
		while ( System.currentTimeMillis() < t + 200 );
		x = x-10;
		
		vida = vida-p.getPoderAtaque();
		if (vida<0){
			if (this.is_chefe)
				Game.pontos+=500;
			else
				Game.pontos+=100;
			Random x = new Random();
			if (x.nextInt(100) < 5){
				p.pegar_bonus();
			}
			return true;
		}
		return false;
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
