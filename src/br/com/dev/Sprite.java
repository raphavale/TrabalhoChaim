package br.com.dev;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Sprite {
	
	private final byte speed = 3; //Maior = mais lento
	public static final short width = 32;
	public static final short height = 64;
	private final byte mspd = 20;
	private final byte sleep_time = 5;
	private short speed_control = 1;
	private byte cena_atual = 1;
	private ImageIcon spr[];
	private final int x;
	private final int y;
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
		int i = 1;
		animar('l');
		while(i<mspd){
			Game.each.andar_esquerda();
			i++;
			try {
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void andarDir() {

		int i = 1;
		animar('r');
		try {
			while (i < mspd) {
				Game.each.andar_direita();
				i++;

				Thread.sleep(sleep_time);

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void andarCima(){
		int i = 1;
		animar('u');
		while(i<mspd){
			Game.each.andar_cima();
			i++;
			try {
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void andarBaixo(){
		int i = 1;
		animar('d');
		while(i<mspd){
			Game.each.andar_baixo();
			i++;
			try {
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		bbg.drawImage(spr[cena_atual].getImage(), x, y, width, height, io);
	}
	

	
	
	
}
