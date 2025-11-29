package tests.tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import spreader.Spreader;
import spreader.DefaultSpreader;
import tile.Tile;
import tile.DefaultTile;

public class DefaultTileTests {
	@Test
	public void basicTests() {
		Tile tile = new DefaultTile(-5, -5);
		assertEquals(-5, tile.getDifficulty());
		assertEquals(0, tile.getResources());
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		
		tile = new DefaultTile(0.5, 0.5);
		assertEquals(0.5, tile.getDifficulty());
		assertEquals(0.5, tile.getResources());
		
		tile.addFlatOccupierPower(100);
		assertEquals(0, tile.getOccupierPower());
		
		tile = new DefaultTile(5, 5, null, 5);
		assertEquals(0, tile.getOccupierPower());
		
		Spreader spreader = new DefaultSpreader(null, null);
		tile = new DefaultTile(5, 5, spreader, 5);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(5, tile.getOccupierPower());
	}
	
	@Test
	public void occupierTests() {
		Tile tile = new DefaultTile(100, 100);
		Spreader spreader = new DefaultSpreader(null, null);
		tile.infect(0, spreader);
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		assertEquals(100, tile.getDifficulty());
		
		tile.infect(100, spreader);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(10, tile.getOccupierPower());
		assertEquals(90, tile.getDifficulty());
		
		tile.infect(10, spreader);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(20, tile.getOccupierPower());
		assertEquals(90, tile.getDifficulty());
		
		tile.addFlatOccupierPower(-20);
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		
		tile.infect(-1000, spreader);
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		assertEquals(90, tile.getDifficulty());
		
		tile.infect(8100, spreader);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(8100, tile.getOccupierPower());
		assertEquals(0, tile.getDifficulty());
		
		tile.multiplyOccupierPower(-5);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(8100, tile.getOccupierPower());
		
		tile.multiplyOccupierPower(2);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(16200, tile.getOccupierPower());
		
		tile.multiplyOccupierPower(0.5);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(8100, tile.getOccupierPower());
		
		tile.addFlatOccupierPower(500);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(8600, tile.getOccupierPower());
		
		tile.addFlatOccupierPower(-1500);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(7100, tile.getOccupierPower());
		
		tile.addFlatOccupierPower(-10000);
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		
		tile.infect(-1, spreader);
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		assertEquals(0, tile.getDifficulty());
		
		tile.infect(25, spreader);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(25, tile.getOccupierPower());
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
