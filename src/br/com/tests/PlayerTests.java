package br.com.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.dev.Player;

public class PlayerTests {
	Player p;
	@Before
	public void setUp() throws Exception {
		 p = new Player(300, 300);
	}

	@Test
	public void testGetFrente() {
		byte i = 0;
		char[] expectedValues = new char[8];
		char[] actualValues = new char[8];
		
		p.animar('D', false); actualValues[i] = p.getFrente(); expectedValues[i] = 'd'; i++;
		p.animar('d', false); actualValues[i] = p.getFrente(); expectedValues[i] = 'd'; i++;
		p.animar('U', false); actualValues[i] = p.getFrente(); expectedValues[i] = 'u'; i++;
		p.animar('u', false); actualValues[i] = p.getFrente(); expectedValues[i] = 'u'; i++;
		p.animar('L', false); actualValues[i] = p.getFrente(); expectedValues[i] = 'l'; i++;
		p.animar('l', false); actualValues[i] = p.getFrente(); expectedValues[i] = 'l'; i++;
		p.animar('R', false); actualValues[i] = p.getFrente(); expectedValues[i] = 'r'; i++;
		p.animar('r', false); actualValues[i] = p.getFrente(); expectedValues[i] = 'r'; i++;		
		
		assertArrayEquals(expectedValues, actualValues);
	}

	@Test
	public void testPegar_bonus() {
		int poderInicial = p.poder_ataque;
		p.pegar_bonus();
		assertNotEquals(poderInicial, p.poder_ataque);
	}

	@Test
	public void testAnimar() {
		p = new Player(300,300);
		byte i = 0;
		int[] exp = new int[20];
		int[] act = new int[20];
		
		//cena atual começa em 1. (olhando pra baixo, pés juntos.)
		p.animar('d', false); exp[i] = 2; act[i] = p.getCenaAtual(); i++;
		p.animar('d', false); exp[i] = 1; act[i] = p.getCenaAtual(); i++;
		p.animar('d', false); exp[i] = 0; act[i] = p.getCenaAtual(); i++;
		p.animar('d', false); exp[i] = 1; act[i] = p.getCenaAtual(); i++;
		p.animar('d', false); exp[i] = 2; act[i] = p.getCenaAtual(); i++;
		
		p.animar('u', false); exp[i] = 5; act[i] = p.getCenaAtual(); i++;
		p.animar('u', false); exp[i] = 4; act[i] = p.getCenaAtual(); i++;
		p.animar('u', false); exp[i] = 3; act[i] = p.getCenaAtual(); i++;
		p.animar('u', false); exp[i] = 4; act[i] = p.getCenaAtual(); i++;
		p.animar('u', false); exp[i] = 5; act[i] = p.getCenaAtual(); i++;
		
		p.animar('l', false); exp[i] = 8; act[i] = p.getCenaAtual(); i++;
		p.animar('l', false); exp[i] = 7; act[i] = p.getCenaAtual(); i++;
		p.animar('l', false); exp[i] = 6; act[i] = p.getCenaAtual(); i++;
		p.animar('l', false); exp[i] = 7; act[i] = p.getCenaAtual(); i++;
		p.animar('l', false); exp[i] = 8; act[i] = p.getCenaAtual(); i++;
		
		p.animar('r', false); exp[i] = 11; act[i] = p.getCenaAtual(); i++;
		p.animar('r', false); exp[i] = 10; act[i] = p.getCenaAtual(); i++;
		p.animar('r', false); exp[i] =  9; act[i] = p.getCenaAtual(); i++;
		p.animar('r', false); exp[i] = 10; act[i] = p.getCenaAtual(); i++;
		p.animar('r', false); exp[i] = 11; act[i] = p.getCenaAtual(); i++;
		
		assertArrayEquals(exp, act);
	}


}
