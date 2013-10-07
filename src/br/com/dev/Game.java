package br.com.dev;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends JFrame implements KeyListener {
	private static final long serialVersionUID = -7556221553607586976L;
	/**
	 * 
	 */
	
	BufferedImage backBuffer;
	int FPS = 100;
	int janelaW = 500;
	int janelaH = 500;
	Sprite ash;
	char teclaPressionada;
	public void inicializar() {
		setTitle("Titulo do Jogo!");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setBounds(100, 100, janelaW, janelaH);
		backBuffer = new BufferedImage(janelaW, janelaH,
				BufferedImage.TYPE_INT_RGB);
		
		ash = new Sprite("andando", 250, 250);
		
		addKeyListener(this);
	}

	public void run() {
		inicializar();
		while (true) {
			atualizar();
			new Mapa().desenharMapa(backBuffer, getGraphics(),this);
			ash.desenharSprite(backBuffer, getGraphics(),'u', this);
			//ash.animarUp();
			//ash.animar('u');
			
			try {
				Thread.sleep(1000 / FPS);
			} catch (Exception e) {
				System.out.println("Thread interrompida!");
			}
		}
	}
	public void atualizar() {
		 
	 }
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			ash.andarEsq();
			ash.animar('l');
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			ash.andarDir();
			ash.animar('r');
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			ash.andarCima();
			ash.animar('u');
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			ash.andarBaixo();
			ash.animar('d');
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
