package tests.tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import spreader.Spreader;
import spreader.DefaultSpreader;
import tile.MutableTile;
import tile.DefaultTile;

public class DefaultTileTests {
	@Test
	public void basicTests() {
		MutableTile tile = new DefaultTile(-5, -5);
		assertEquals(-5, tile.getDifficulty());
		assertEquals(0, tile.getResources());
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		
		tile = new DefaultTile(0.5, 0.5);
		assertEquals(0.5, tile.getDifficulty());
		assertEquals(0.5, tile.getResources());
	}
	
	@Test
	public void infectAndReduceOccupierTests() {
		MutableTile tile = new DefaultTile(100, 100);
		Spreader spreader = new DefaultSpreader(null, null);
		tile.infect(0, spreader);
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		assertEquals(100, tile.getDifficulty());
		
		tile.infect(100, spreader);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(10, tile.getOccupierPower());
		assertEquals(90, tile.getDifficulty());
		
		tile.changeOccupierPower(-10);
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
		
		tile.changeOccupierPower(500);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(8600, tile.getOccupierPower());
		
		tile.changeOccupierPower(-1500);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(7100, tile.getOccupierPower());
		
		tile.changeOccupierPower(-10000);
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
		MutableTile tile = new DefaultTile(100, 100);
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
