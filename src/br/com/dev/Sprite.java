package br.com.dev;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Sprite {
	
	private final byte speed = 15; //Maior = mais lento
	private final short width = 16;
	private final short height = 32;
	private final byte mspd = 10;
	private short speed_control = 1;
	private byte cena_atual = 1;
	private ImageIcon spr[];
	private int x;
	private int y;
	private boolean started = false;
	private char move_old;
	
	
	Sprite(String velocidade, int x, int y){
		spr = new ImageIcon[12];
		for (int i = 0; i < spr.length; i++ ){
			spr[i] = new ImageIcon("src/img/char/" + velocidade + "_" + i + ".png"); 
		}
		this.x = x;
		this.y = y;
	}
	
	public void andarEsq(){
		x = x-mspd;
	}
	public void andarDir(){
		x = x+mspd;
	}
	public void andarCima(){
		y = y-mspd;
	}
	public void andarBaixo(){
		y = y+mspd;
	}

	public void animar(char move){	
		if (move != move_old){
			started = false;
		}
		if (!started) {
			switch (move) {
			case 'u':
			case 'U':
				cena_atual = 4;
				break;
			case 'l':
			case 'L':
				cena_atual = 7;
				break;
			case 'r':
			case 'R':
				cena_atual = 10;
				break;
			case 'd':
			case 'D':
				cena_atual = 1;
				break;
			}
			started = true;
		}
		
		speed_control ++;
		if (speed_control == speed){
			
			speed_control = 1;
			
			if (cena_atual % 3 == 1)
				cena_atual++;
			else if (cena_atual % 3 == 2)
				cena_atual-=2;
			else
				cena_atual +=2;
			
		}
		
	}
	
	public void animarUp(){	
		if (!started){
			cena_atual = 4;
			started = true;
		}
		
		speed_control ++;
		if (speed_control == speed){
			
			speed_control = 1;
			
			if (cena_atual % 3 == 1)
				cena_atual++;
			else if (cena_atual % 3 == 2)
				cena_atual-=2;
			else
				cena_atual +=2;
			
		}
		
	}
	
	public void desenharSprite(BufferedImage bi, Graphics g, char move, ImageObserver io) {
		Graphics bbg = bi.getGraphics();
		//animar(move);
		bbg.drawImage(spr[cena_atual].getImage(), x, y, width, height, io);
		g.drawImage(bi, 0, 0, io);
	}
	

	
	
	
}