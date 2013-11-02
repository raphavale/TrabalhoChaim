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

	public final int num_monstros = 1;
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
		gerar_monstros(num_monstros, 1);
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
			limpa_monstros();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}
	
	private void gerar_monstros(int n, int lvl){
		for (int i = 0; i<n;i++){
			int x = (int) (Math.random()*each.getWidth() + each.desloc_x);
			int y = (int) (Math.random()*each.getHeight() + each.desloc_y);
			
			//test edges.
			
			monstros.add(new Monstro(x,y, lvl));
		}
	}
	
	private void limpa_monstros(){
		for (int i = 0; i < monstros.size(); i++) {
			if(monstros.get(i).getVida() < 0){
				monstros.remove(i);
				i--;
			}
		}
	}
	
	public LinkedList<Monstro> mostros_perto() {
		//Procura monstros na frente do mob (em 2*player.height * player.width) e add num vetor
		LinkedList<Monstro> m = new LinkedList<Monstro>();
		
		int indice_lista = 0;
		Monstro temp;
		while (indice_lista < monstros.size()){
			
			temp = monstros.get(indice_lista);
			
			if ((temp.getX() > (ash.getX())) && (temp.getX() < (ash.getX() + (Player.width * 1.5))) ){
				//está dentro do width. Testando height:
				if ( (temp.getY() > (ash.getY() - (0.5*Player.height))) && (temp.getY() < (ash.getY() + (0.5 * Player.height))) ) {
					//Dentro do range definido. Adicionar no array		
					m.add(temp);
				}
				
			}			
			
			System.out.println("\nX Maior que " + (ash.getX()) + " menor que " + (ash.getX() + (Player.width * 1.5)));
			System.out.println("Y Maior que " + (ash.getY() - (0.5*Player.height)) + " menor que " + (ash.getY() + (0.5 * Player.height)));
			indice_lista++;
		}

		
		return m;
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
			
			if (e.getKeyCode() == KeyEvent.VK_A) {
				imprimeMonstros();
				ash.atacar(mostros_perto(), ash);
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
	
	
	public void imprimeMonstros(){
		for (int i  = 0; i < monstros.size(); i++){
			System.out.println("X:" + monstros.get(i).getX() + "\tASH X:" + ash.getX() 
							+ "\nY:" + monstros.get(i).getY() + "\tASH Y:" + ash.getY() 
							+ "\nVida:" + monstros.get(i).getVida());
		}
	}
}
