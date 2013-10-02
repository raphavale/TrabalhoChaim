/**
 * 
 */
package br.com.dev;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Raphael
 *
 */
public class MainTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	Main m;
	@Before
	public void setUp() throws Exception {
		m = new Main();
	}

	/**
	 * Test method for {@link br.com.dev.Main#somaPositivo(int, int)}.
	 */
	@Test
	public void testSomaPositivo() {
		Assert.assertEquals(5, m.somaPositivo(2, 3));
		
	}

	/**
	 * Test method for {@link br.com.dev.Main#somaNegativo(int, int)}.
	 */
	@Test
	public void testSomaNegativo() {
		Assert.assertEquals(-5, m.somaNegativo(-2, -3));
	}

}
