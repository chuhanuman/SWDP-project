package tests.tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import tile.Tile;
import tile.DefaultTile;

public class DefaultTileTests {
	@Test
	public void basicTests() {
		Tile tile = new DefaultTile(-5, -5);
		assertEquals(-5, tile.getDifficulty());
		assertEquals(0, tile.getResources());
		
		tile = new DefaultTile(0.5, 0.5);
		assertEquals(0.5, tile.getDifficulty());
		assertEquals(0.5, tile.getResources());
	}
	
	@Test
	public void infectTests() {
		Tile tile = new DefaultTile(100, 100);
		double result = tile.infect(0);
		assertEquals(-100, result);
		assertEquals(100, tile.getDifficulty());
		
		result = tile.infect(100);
		assertEquals(10, result);
		assertEquals(90, tile.getDifficulty());
		
		result = tile.infect(-1000);
		assertEquals(-90, result);
		assertEquals(90, tile.getDifficulty());
		
		result = tile.infect(8100);
		assertEquals(8100, result);
		assertEquals(0, tile.getDifficulty());
		
		result = tile.infect(-1);
		assertEquals(0, result);
		assertEquals(0, tile.getDifficulty());
		
		result = tile.infect(25);
		assertEquals(25, result);
		assertEquals(0, tile.getDifficulty());
	}
	
	@Test
	public void extractTests() {
		Tile tile = new DefaultTile(100, 100);
		double result = tile.extract(50);
		assertEquals(50, result);
		assertEquals(50, tile.getResources());
		
		result = tile.extract(-10);
		assertEquals(0, result);
		assertEquals(50, tile.getResources());
		
		result = tile.extract(1000);
		assertEquals(50, result);
		assertEquals(0, tile.getResources());
		
		result = tile.extract(10);
		assertEquals(0, result);
		assertEquals(0, tile.getResources());
	}
}
