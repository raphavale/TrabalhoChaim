package br.com.dev;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Game extends JFrame implements KeyListener {
	private static final long serialVersionUID = -7556221553607586976L;
	/**
	 * 
	 */

	public final int num_monstros = 10;
	BufferedImage backBuffer;
	int FPS = 100;
	public static int janelaW = 1000;
	public static int janelaH = 1000;
	Player ash;
	public static Mapa each;
	public static LinkedList<Monstro> monstros = new LinkedList<Monstro>();
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
		ash = new Player("andando", 250, 250);
		gerar_monstros(num_monstros);
		addKeyListener(this);
	}

	public void run() {
		inicializar();
		while (true) {
			atualizar();
			each.desenharMapa(backBuffer, getGraphics(), this);
			
			for (int i = 0; i<monstros.size(); i++)
				monstros.get(i).desenharSprite(backBuffer, getGraphics(), this);
			
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
	
	private void gerar_monstros(int n){
		for (int i = 0; i<n;i++){
			int x = (int) (Math.random()*each.getWidth() + each.desloc_x);
			int y = (int) (Math.random()*each.getHeight() + each.desloc_y);
			
			//test edges.
			
			monstros.add(new Monstro(x,y));
			System.out.println("Mob" + i + ":\n\tX = " + x + "\n\tY = " + y);
		}
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
			System.out.print("");


	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
