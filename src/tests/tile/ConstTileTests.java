package tests.tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import spreader.Spreader;
import spreader.DefaultSpreader;
import tile.Tile;
import tile.ConstTile;
import tile.DefaultTile;
import tile.EfficientExtractionDecorator;
import tile.ExtraDefensesDecorator;
import tile.StealthyDecorator;

public class ConstTileTests {
	@Test
	public void basicTests() {
		Tile tile = new DefaultTile(-5, -5);
		ConstTile constTile = new ConstTile(tile);
		assertEquals(-5, constTile.getDifficulty());
		assertEquals(0, constTile.getResources());
		assertEquals(null, constTile.getOccupier());
		assertEquals(0, constTile.getOccupierPower());
		
		tile = new DefaultTile(0.5, 0.5);
		constTile = new ConstTile(tile);
		assertEquals(0.5, constTile.getDifficulty());
		assertEquals(0.5, constTile.getResources());
		assertEquals(tile.getID(), constTile.getID());
	}
	
	@Test
	public void afterChangesTests() {
		Tile tile = new DefaultTile(100, 100);
		Spreader spreader = new DefaultSpreader(null, null);
		ConstTile constTile = new ConstTile(tile);
		tile.infect(0, spreader);
		assertEquals(null, constTile.getOccupier());
		assertEquals(0, constTile.getOccupierPower());
		assertEquals(100, constTile.getDifficulty());
		
		tile.infect(100, spreader);
		assertEquals(spreader, constTile.getOccupier());
		assertEquals(10, constTile.getOccupierPower());
		assertEquals(90, constTile.getDifficulty());
		
		tile.changeOccupierPower(-10);
		assertEquals(null, constTile.getOccupier());
		assertEquals(0, constTile.getOccupierPower());
		
		tile.infect(-1000, spreader);
		assertEquals(null, constTile.getOccupier());
		assertEquals(0, constTile.getOccupierPower());
		assertEquals(90, constTile.getDifficulty());
		
		tile.infect(8100, spreader);
		assertEquals(spreader, constTile.getOccupier());
		assertEquals(8100, constTile.getOccupierPower());
		assertEquals(0, constTile.getDifficulty());
		
		tile.changeOccupierPower(500);
		assertEquals(spreader, constTile.getOccupier());
		assertEquals(8600, constTile.getOccupierPower());
		
		tile.changeOccupierPower(-1500);
		assertEquals(spreader, constTile.getOccupier());
		assertEquals(7100, constTile.getOccupierPower());
		
		tile.changeOccupierPower(-10000);
		assertEquals(null, constTile.getOccupier());
		assertEquals(0, constTile.getOccupierPower());
		
		tile.infect(-1, spreader);
		assertEquals(null, constTile.getOccupier());
		assertEquals(0, constTile.getOccupierPower());
		assertEquals(0, constTile.getDifficulty());
		
		tile.infect(25, spreader);
		assertEquals(spreader, constTile.getOccupier());
		assertEquals(25, constTile.getOccupierPower());
		assertEquals(0, constTile.getDifficulty());
		
		double result = tile.extract(50);
		assertEquals(50, result);
		assertEquals(50, constTile.getResources());
		
		result = tile.extract(-10);
		assertEquals(0, result);
		assertEquals(50, constTile.getResources());
		
		result = tile.extract(1000);
		assertEquals(50, result);
		assertEquals(0, constTile.getResources());
		
		result = tile.extract(10);
		assertEquals(0, result);
		assertEquals(0, constTile.getResources());
	}
	
	@Test
	public void afterDecoratorTests() {
		Tile tile = new DefaultTile(100, 100);
		tile = new StealthyDecorator(tile);
		tile = new EfficientExtractionDecorator(tile);
		tile = new ExtraDefensesDecorator(tile);
		tile = new EfficientExtractionDecorator(tile);
		tile = new EfficientExtractionDecorator(tile);
		tile = new StealthyDecorator(tile);
		
		ConstTile constTile = new ConstTile(tile);
		assertEquals(-100, constTile.getDifficulty());
		
		double result = tile.extract(50);
		assertEquals(168.75, result);
		assertEquals(50, constTile.getResources());
		
		
		Spreader spreader = new DefaultSpreader(null, null);
		tile.infect(105, spreader);
		assertEquals(spreader, constTile.getOccupier());
		assertEquals(10, constTile.getOccupierPower());
		assertEquals(-110, constTile.getDifficulty());
	}
}
