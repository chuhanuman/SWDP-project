package tests.tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import spreader.DefaultSpreader;
import spreader.Spreader;
import tile.Tile;
import tile.DefaultTile;
import tile.ExtraDefensesDecorator;
import tile.EfficientExtractionDecorator;
import tile.StealthyDecorator;

public class TileDecoratorTests {
	@Test
	public void extraDefensesDecoratorTests() {
		Tile tile = new ExtraDefensesDecorator(new DefaultTile(100, 100));
		Spreader spreader = new DefaultSpreader(null, null);
		tile.infect(5, spreader);
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		assertEquals(100, tile.getDifficulty());
		
		tile.infect(30, spreader);
		assertEquals(null, tile.getOccupier());
		assertEquals(0, tile.getOccupierPower());
		assertEquals(95, tile.getDifficulty());
		
		tile = new ExtraDefensesDecorator(tile);
		tile.infect(110, spreader);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(15, tile.getOccupierPower());
		assertEquals(85, tile.getDifficulty());
	}
	
	@Test
	public void efficientExtractionDecoratorTests() {
		Tile tile = new EfficientExtractionDecorator(new DefaultTile(100, 100));
		double result = tile.extract(10);
		assertEquals(15, result);
		assertEquals(90, tile.getResources());
		
		result = tile.extract(30);
		assertEquals(45, result);
		assertEquals(60, tile.getResources());
		
		tile = new EfficientExtractionDecorator(tile);
		result = tile.extract(1000);
		assertEquals(135, result);
		assertEquals(0, tile.getResources());
		
		result = tile.extract(1000);
		assertEquals(0, result);
		assertEquals(0, tile.getResources());
	}
	
	@Test
	public void stealthyDecoratorTests() {
		Tile tile = new StealthyDecorator(new DefaultTile(100, 100));
		assertEquals(0, tile.getDifficulty());
		
		tile = new StealthyDecorator(tile);
		assertEquals(-100, tile.getDifficulty());
		
		Spreader spreader = new DefaultSpreader(null, null);
		tile.infect(100, spreader);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(10, tile.getOccupierPower());
		assertEquals(-110, tile.getDifficulty());
	}
	
	@Test
	public void multiDecoratorTests() {
		Tile tile = new DefaultTile(100, 100);
		tile = new StealthyDecorator(tile);
		tile = new EfficientExtractionDecorator(tile);
		tile = new ExtraDefensesDecorator(tile);
		tile = new EfficientExtractionDecorator(tile);
		tile = new EfficientExtractionDecorator(tile);
		tile = new StealthyDecorator(tile);
		
		assertEquals(-100, tile.getDifficulty());
		
		double result = tile.extract(50);
		assertEquals(168.75, result);
		assertEquals(50, tile.getResources());
		
		
		Spreader spreader = new DefaultSpreader(null, null);
		tile.infect(105, spreader);
		assertEquals(spreader, tile.getOccupier());
		assertEquals(10, tile.getOccupierPower());
		assertEquals(-110, tile.getDifficulty());
	}
}
