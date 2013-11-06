package br.com.dev;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class Player {
	
	private final byte speed = 2; //Maior = mais lento
	public static final short width = 32;
	public static final short height = 64;
	private final byte mspd = 20;
	private final byte sleep_time = 5;
	private short speed_control = 1;
	private byte cena_atual = 1;
	private ImageIcon spr[];
	private ImageIcon atk[];
	private final int x;
	private final int y;
	private boolean started = false;
	private char move_old;
	private int poder_ataque = 3;
	private boolean isAtacking = false;
	private boolean duringAtack = false;
	private boolean midstep = false;
	private long _t = System.currentTimeMillis();
	private boolean time_changed = false;
	
	Player(String velocidade, int x, int y){
		spr = new ImageIcon[12];
		atk = new ImageIcon[4];
		for (int i = 0; i < spr.length; i++ ){
			if(i<4)
				atk[i] = new ImageIcon("src/img/char/pers/atk_" + i + ".png");
			spr[i] = new ImageIcon("src/img/char/pers/" + velocidade + "_" + i + ".png"); 
		}
		this.x = x;
		this.y = y;
	}

	public char getFrente(){
		if (cena_atual == 4)
			return 'u';
		if (cena_atual == 7)
			return 'l';
		if (cena_atual == 10)
			return 'r';
		
		return 'd';
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	
	public int getPoderAtaque(){
		return poder_ataque;
	}
	
	public void pegar_bonus(){
		poder_ataque++;
	}
	public void andarEsq(){
		int i = 1;
		animar('l', false);
		while(i<mspd){
			if(Game.each.andar_esquerda())
				for(int j = 0; j<Game.monstros.size(); j++)
					Game.monstros.get(j).andar_esquerda();
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
		animar('r', false);
		try {
			while (i < mspd) {
				if(Game.each.andar_direita())
					for(int j = 0; j<Game.monstros.size(); j++)
						Game.monstros.get(j).andar_direita();
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
		animar('u', false);
		while(i<mspd){
			if(Game.each.andar_cima())
				for(int j = 0; j<Game.monstros.size(); j++)
					Game.monstros.get(j).andar_cima();
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
		animar('d', false);
		while(i<mspd){
			if(Game.each.andar_baixo())
				for(int j = 0; j<Game.monstros.size(); j++)
					Game.monstros.get(j).andar_baixo();
			i++;
			try {
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void animar(char move, boolean atacking){	
			
		if (move != move_old){
			started = false;
			move_old = move;
		}
		if (!started && !isAtacking) {
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
			
			if (isAtacking) {
				
				if(!duringAtack){
					if (cena_atual < 3) cena_atual = 0;
					else if (cena_atual < 6) cena_atual = 1;
						else if (cena_atual < 9) cena_atual = 2;
							 else cena_atual = 3;
					duringAtack = true;
				} else {
					isAtacking = false;
					duringAtack = false;
				}
			
			} else {
				
				if (cena_atual % 3 == 1) {
					if (midstep)
						cena_atual --;
					else
						cena_atual ++;
				}
				else if (cena_atual % 3 == 2){
					midstep = true;
					cena_atual--;
				}
				else{
					midstep = false;
					cena_atual++;
				}
			}
			
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
	
	public void desenharSprite(BufferedImage bi, Graphics g, ImageObserver io) {
		Graphics bbg = bi.getGraphics();
		if (isAtacking || duringAtack){
			
			if (cena_atual < atk.length)
				bbg.drawImage(atk[cena_atual].getImage(), x, y, width, height, io);
			if (!time_changed){
				_t = System.currentTimeMillis();
				time_changed = true;
			}
			
			if (System.currentTimeMillis() - _t > 200){
				_t = System.currentTimeMillis();
				isAtacking = false;
				duringAtack = false;
				time_changed = false;
				
				//voltando a cena normal
				if (cena_atual == 0) cena_atual = 1;
				else if (cena_atual == 1) cena_atual = 4;
				else if (cena_atual == 2) cena_atual = 7;
				else if (cena_atual == 3) cena_atual = 10;
			}
			
		} else {
			bbg.drawImage(spr[cena_atual].getImage(), x, y, width, height, io);
		}
		
	}
	

	public void atacar(LinkedList<Monstro> m, Player p){
		isAtacking = true;
		duringAtack = false;
		animar('l', true);

		for (int i = 0; i < m.size(); i++)
			m.get(i).tomar_dano(p);
	}
	
	
}
