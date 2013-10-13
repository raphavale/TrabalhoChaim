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
	public static int janelaW = 500;
	public static int janelaH = 500;
	Sprite ash;
	public static Mapa each;
	char teclaPressionada;

	public void inicializar() {
		setTitle("Descontamine a EACH!");
		setSize(janelaW, janelaH);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		backBuffer = new BufferedImage(janelaW, janelaH,
				BufferedImage.TYPE_INT_RGB);
		
		each = new Mapa(janelaW,janelaH);
		ash = new Sprite("andando", 250, 250);

		addKeyListener(this);
	}

	public void run() {
		inicializar();
		while (true) {
			atualizar();
			each.desenharMapa(backBuffer, getGraphics(), this);
			ash.desenharSprite(backBuffer, getGraphics(), 'u', this);
			getGraphics().drawImage(backBuffer, 0, 0, this);
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

	
	private long agora = System.currentTimeMillis();
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
			if (System.currentTimeMillis() - agora < FPS)
				return;
		
			agora = System.currentTimeMillis();
			// Do your work here...
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				ash.andarEsq();
				each.andar_esquerda();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				ash.andarDir();
				each.andar_direita();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				ash.andarCima();
				each.andar_cima();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				ash.andarBaixo();
				each.andar_baixo();
			}

		
	}

	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_UP
				|| e.getKeyCode() == KeyEvent.VK_DOWN)
			System.out.println("SOLTOU!");


	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
