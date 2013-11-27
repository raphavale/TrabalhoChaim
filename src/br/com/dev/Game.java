package br.com.dev;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
	
public class Game extends JFrame implements KeyListener {
	private static final long serialVersionUID = -7556221553607586976L;
	/**
	 * 
	 */
	
	Game(){
		try {
			bgMusic = Applet.newAudioClip((new File("src/midi/mario1.mid").toURI().toURL()));
			menuMusic = Applet.newAudioClip((new File("src/midi/menu.mid").toURI().toURL()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public final int num_monstros = 5;
	public AudioClip bgMusic, menuMusic; 
	public final int max_monstros = 30;
	public int lvl_monstros = 1;
	public final int tempo_spawn = 5000; //tempo para um novo monstro nascer, em milisegundos
	static BufferedImage backBuffer;
	int FPS = 100;
	int _ATK_DELAY = 700;
	public static int janelaW = 640;
	public static int janelaH = 426;
	Player ash;
	int time_elapsed = 0;
	public static int pontos = 0;
	public static Mapa each;
	public static int contaminacao = 0;
	public static LinkedList<Monstro> monstros = new LinkedList<Monstro>();
	char teclaPressionada;
	public static ArrayList<Timer> timers = new ArrayList<Timer>();
	static int dano;
	static int xd;
	static int yd;
	static int cdano;
	public boolean ingame = true;

	public void inicializar() {
		
		addKeyListener(this);
		setTitle("Descontamine a EACH!");
		setSize(janelaW, janelaH);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		backBuffer = new BufferedImage(janelaW, janelaH,
				BufferedImage.TYPE_INT_RGB);
		long t = 0, a = System.currentTimeMillis();
		boolean continua = true;
		
		menuMusic.loop();
		while(continua_intro()){
			Intro i = new Intro(janelaW, janelaH);
			//Introdução
			// A cada 0.005 segundo, aumenta a var T, fazendo o banner abaixar
			if (System.currentTimeMillis() > a + 5 && continua ){
				a = System.currentTimeMillis();
				t++;
			}
	        
			i.desenharIntro(backBuffer, getGraphics(), this, t); 
			getGraphics().drawImage(backBuffer, 0, 0, this);			
		}
		
		menuMusic.stop();
		bgMusic.loop();
		each = new Mapa(janelaW,janelaH);
		ash = new Player((janelaW/2), (janelaH/2));
		gerar_monstros(num_monstros, 1);
		
		set_timers();
	}

	boolean continua_intro = true;
	private boolean continua_intro() {
		// TODO Auto-generated method stub
		return continua_intro;
	}

	public void run() {
		inicializar();
		while (true) {
			atualizar();
			each.desenharMapa(backBuffer, getGraphics(), this);
			
			for (int i = 0; i<monstros.size(); i++)
				monstros.get(i).desenharSprite(backBuffer, getGraphics(), this);
			
			ash.desenharSprite(backBuffer, getGraphics(), this);
			getGraphics().drawImage(backBuffer, 0, 0, this);
			Font myFont = new Font("Courier", Font.BOLD ,50);
			Graphics g = getGraphics();
			g.setFont(myFont);
			g.setColor(Color.RED);
			g.drawString("Pontos:"+pontos, 10 , 60);
			g.drawString("Tempo:"+time_elapsed, 350, 60);
			Font myFont2 = new Font("Courier", Font.BOLD ,20);
			g.setFont(myFont2);
			g.setColor(Color.BLUE);
			g.drawString("Para recomeçar aperte R", 20, 400);
			if(contaminacao < 25)
				g.setColor(Color.BLACK);
			else if(contaminacao < 50)
				g.setColor(Color.ORANGE);
			else
				g.setColor(Color.RED);
			
			g.drawString("Contaminação:"+contaminacao, 350, 400);
			
			if(cdano > 0){
				g.setColor(Color.RED);
				g.drawString(Integer.toString(dano), xd, yd);
				cdano--;
			}
			
			if(contaminacao >= 100){
				for (Timer timer : timers) {
					timer.cancel();
				}
				g.setColor(Color.BLUE);
				g.drawString("A CONTAMINAÇÃO NÃO FOI CONTROLADA A TEMPO.", 20, 200);
				g.drawString("TUDO ESTÁ PERDIDO.", 20, 230);
				ingame = false;
			}
			
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
			
			monstros.add(new Monstro(x,y, lvl,false));
			contaminacao += 1*lvl;
		}
	}
	
	private void limpa_monstros(){
		for (int i = 0; i < monstros.size(); i++) {
			if(monstros.get(i).getVida() < 0){
				if(monstros.get(i).is_chefe)
					contaminacao-=3*monstros.get(i).level;
				else
					contaminacao-=1*monstros.get(i).level;
				monstros.remove(i);
				i--;
			}
		}
	}
	
	private void limpa_mapa(){
		for (int i = 0; i < monstros.size(); i++) {
				monstros.remove(i);
				i--;
		}
		contaminacao = 0;
	}

	private void gerar_chefe(int n, int lvl){
		for (int i = 0; i<n;i++){
			int x = (int) (Math.random()*each.getWidth() + each.desloc_x);
			int y = (int) (Math.random()*each.getHeight() + each.desloc_y);
						
			monstros.add(new Monstro(x,y,lvl,true));
			lvl_monstros++;
			contaminacao += 3*lvl;
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
			
			//System.out.println("\nX Maior que " + (ash.getX()) + " menor que " + (ash.getX() + (Player.width * 1.5)));
			//System.out.println("Y Maior que " + (ash.getY() - (0.5*Player.height)) + " menor que " + (ash.getY() + (0.5 * Player.height)));
			indice_lista++;
		}

		
		return m;
	}
	
	private long mov_delay = System.currentTimeMillis();
	private long atk_delay = System.currentTimeMillis();
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if (System.currentTimeMillis() - mov_delay < FPS)
			return;
		else if(ingame){

			mov_delay = System.currentTimeMillis();
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
		
		if (e.getKeyCode() == KeyEvent.VK_A && ingame) {
			if (System.currentTimeMillis() - atk_delay < _ATK_DELAY)
				return;
			else {
				atk_delay = System.currentTimeMillis();
				mov_delay = System.currentTimeMillis() + _ATK_DELAY;

				// imprimeMonstros();
				playAudio("src\\midi\\knife_stab.wav");
				ash.atacar(mostros_perto(), ash);
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			continua_intro = false;
		
		if (e.getKeyCode() == KeyEvent.VK_R && !continua_intro){
			ingame = true;
			limpa_mapa();
			for (Timer timer : timers) {
				timer.cancel();
			}
			inicializar();
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

	
	public void loopAudio(String s){
		URL u;
		try {
			u = new File(s).toURI().toURL();
			Applet.newAudioClip(u).loop();
			//System.out.println(u.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playAudio(String s){
		URL u;
		try {
			u = new File(s).toURI().toURL();
			Applet.newAudioClip(u).play();
			//System.out.println(u.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private void set_timers()
	{
		time_elapsed = 0;
		pontos = 0;
		Timer timer_monstros = new Timer();
        timer_monstros.schedule(new TimerTask() {
            @Override
            public void run() {
                gerar_monstros(1, lvl_monstros);
            }
        }, tempo_spawn, tempo_spawn);
        

        Timer timer_chefe = new Timer();
        timer_chefe.schedule(new TimerTask() {
            @Override
            public void run() {
                gerar_chefe(1, lvl_monstros);
            }
        }, tempo_spawn*5, tempo_spawn*5);

        Timer timer_geral = new Timer();
        timer_chefe.schedule(new TimerTask() {
            @Override
            public void run() {
            	time_elapsed++;
            }
        }, 1000, 1000);
        
        timers.add(timer_monstros);
        timers.add(timer_chefe);
        timers.add(timer_geral);

        
	}
}
