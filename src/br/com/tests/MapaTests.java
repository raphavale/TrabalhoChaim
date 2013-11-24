package br.com.tests;

import org.junit.Before;
import org.junit.Test;

import br.com.dev.Mapa;

public class MapaTests {

	Mapa m = new Mapa(500, 500);
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAndar() {
		byte i = 0;
		int[] exp = new int[8];
		int[] act = new int[8];
		
		exp[i] = m.desloc_x - 1; m.andar_direita(); act[i] = m.desloc_x; i++;
		exp[i] = m.desloc_x - 1; m.andar_direita(); act[i] = m.desloc_x; i++;
		exp[i] = m.desloc_x + 1; m.andar_esquerda(); act[i] = m.desloc_x; i++;
		exp[i] = m.desloc_x + 1; m.andar_esquerda(); act[i] = m.desloc_x; i++;
		exp[i] = m.desloc_y - 1; m.andar_baixo(); act[i] = m.desloc_y; i++;
		exp[i] = m.desloc_y - 1; m.andar_baixo(); act[i] = m.desloc_y; i++;
		exp[i] = m.desloc_y + 1; m.andar_cima(); act[i] = m.desloc_y; i++;
		exp[i] = m.desloc_y + 1; m.andar_cima(); act[i] = m.desloc_y; i++;
	}
}
