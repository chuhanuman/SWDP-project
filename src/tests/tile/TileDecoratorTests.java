package tests.tile;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import spreader.DefaultSpreader;
import spreader.Spreader;
import tests.spreader.FakeSpreader;
import tile.Tile;
import tile.DefaultTile;
import tile.ExtraDefensesDecorator;
import tile.EfficientExtractionDecorator;
import tile.StealthyDecorator;

public class TileDecoratorTests {
	private FakeTile fakeTile1 = new FakeTile(), fakeTile2 = new FakeTile();
	private Spreader spreader1 = new FakeSpreader(), spreader2 = new FakeSpreader();
	
	@Test
	public void extraDefensesDecoratorTests() {
		Tile tile = new ExtraDefensesDecorator(fakeTile1);
		
		fakeTile1.difficulty = -5;
		fakeTile1.resources = -6;
		fakeTile1.occupier = spreader1;
		fakeTile1.occupierPower = -7;
		fakeTile1.id = UUID.randomUUID();
		assertEquals(-5, tile.getDifficulty());
		assertEquals(-6, tile.getResources());
		assertEquals(spreader1, tile.getOccupier());
		assertEquals(-7, tile.getOccupierPower());
		assertEquals(fakeTile1.id, tile.getID());
		
		fakeTile1.copy = fakeTile2;
		fakeTile2.difficulty = -4;
		fakeTile2.resources = -5;
		fakeTile2.occupier = spreader2;
		fakeTile2.occupierPower = -6;
		fakeTile2.id = UUID.randomUUID();
		Tile copyTile = tile.copy();
		assertEquals(-4, copyTile.getDifficulty());
		assertEquals(-5, copyTile.getResources());
		assertEquals(spreader2, copyTile.getOccupier());
		assertEquals(-6, copyTile.getOccupierPower());
		assertNotEquals(fakeTile1.id, copyTile.getID());
		assertEquals(fakeTile2.id, copyTile.getID());
		
		tile.infect(5, spreader2);
		copyTile.infect(10, spreader1);
		assertEquals(0, fakeTile1.infectCalls.getLast().power());
		assertEquals(spreader2, fakeTile1.infectCalls.getLast().spreader());
		assertEquals(5, fakeTile2.infectCalls.getLast().power());
		assertEquals(spreader1, fakeTile2.infectCalls.getLast().spreader());
		
		fakeTile1.resourcesToExtract = 4;
		double result = tile.extract(30);
		assertEquals(4, result);
		assertEquals(30, fakeTile1.extractCalls.getLast());
		
		tile.addFlatOccupierPower(50);
		assertEquals(50, fakeTile1.addFlatCalls.getLast());
		
		tile.multiplyOccupierPower(6);
		assertEquals(6, fakeTile1.multiplyCalls.getLast());
		
		ExtraDefensesDecorator.getApplier().apply(tile).infect(10, spreader1);
		assertEquals(0, fakeTile1.infectCalls.getLast().power());
		assertEquals(spreader1, fakeTile1.infectCalls.getLast().spreader());
	}
	
	@Test
	public void efficientExtractionDecoratorTests() {
		Tile tile = new EfficientExtractionDecorator(fakeTile1);
		
		fakeTile1.difficulty = -5;
		fakeTile1.resources = -6;
		fakeTile1.occupier = spreader1;
		fakeTile1.occupierPower = -7;
		fakeTile1.id = UUID.randomUUID();
		assertEquals(-5, tile.getDifficulty());
		assertEquals(-6, tile.getResources());
		assertEquals(spreader1, tile.getOccupier());
		assertEquals(-7, tile.getOccupierPower());
		assertEquals(fakeTile1.id, tile.getID());
		
		fakeTile1.copy = fakeTile2;
		fakeTile2.difficulty = -4;
		fakeTile2.resources = -5;
		fakeTile2.occupier = spreader2;
		fakeTile2.occupierPower = -6;
		fakeTile2.id = UUID.randomUUID();
		Tile copyTile = tile.copy();
		assertEquals(-4, copyTile.getDifficulty());
		assertEquals(-5, copyTile.getResources());
		assertEquals(spreader2, copyTile.getOccupier());
		assertEquals(-6, copyTile.getOccupierPower());
		assertNotEquals(fakeTile1.id, copyTile.getID());
		assertEquals(fakeTile2.id, copyTile.getID());
		
		tile.infect(5, spreader2);
		assertEquals(5, fakeTile1.infectCalls.getLast().power());
		assertEquals(spreader2, fakeTile1.infectCalls.getLast().spreader());
		
		fakeTile1.resourcesToExtract = 4;
		fakeTile2.resourcesToExtract = 6;
		double result1 = tile.extract(30);
		double result2 = copyTile.extract(50);
		assertEquals(6, result1);
		assertEquals(30, fakeTile1.extractCalls.getLast());
		assertEquals(9, result2);
		assertEquals(50, fakeTile2.extractCalls.getLast());
		
		tile.addFlatOccupierPower(50);
		assertEquals(50, fakeTile1.addFlatCalls.getLast());
		
		tile.multiplyOccupierPower(6);
		assertEquals(6, fakeTile1.multiplyCalls.getLast());
		
		result1 = EfficientExtractionDecorator.getApplier().apply(tile).extract(10);
		assertEquals(9, result1);
		assertEquals(10, fakeTile1.extractCalls.getLast());
	}
	
	@Test
	public void stealthyDecoratorTests() {
		Tile tile = new StealthyDecorator(fakeTile1);
		
		fakeTile1.difficulty = 95;
		fakeTile1.resources = -6;
		fakeTile1.occupier = spreader1;
		fakeTile1.occupierPower = -7;
		fakeTile1.id = UUID.randomUUID();
		assertEquals(-5, tile.getDifficulty());
		assertEquals(-6, tile.getResources());
		assertEquals(spreader1, tile.getOccupier());
		assertEquals(-7, tile.getOccupierPower());
		assertEquals(fakeTile1.id, tile.getID());
		
		fakeTile1.copy = fakeTile2;
		fakeTile2.difficulty = 96;
		fakeTile2.resources = -5;
		fakeTile2.occupier = spreader2;
		fakeTile2.occupierPower = -6;
		fakeTile2.id = UUID.randomUUID();
		Tile copyTile = tile.copy();
		assertEquals(-4, copyTile.getDifficulty());
		assertEquals(-5, copyTile.getResources());
		assertEquals(spreader2, copyTile.getOccupier());
		assertEquals(-6, copyTile.getOccupierPower());
		assertNotEquals(fakeTile1.id, copyTile.getID());
		assertEquals(fakeTile2.id, copyTile.getID());
		
		tile.infect(5, spreader2);
		assertEquals(5, fakeTile1.infectCalls.getLast().power());
		assertEquals(spreader2, fakeTile1.infectCalls.getLast().spreader());
		
		fakeTile1.resourcesToExtract = 4;
		double result = tile.extract(30);
		assertEquals(4, result);
		assertEquals(30, fakeTile1.extractCalls.getLast());
		
		tile.addFlatOccupierPower(50);
		assertEquals(50, fakeTile1.addFlatCalls.getLast());
		
		tile.multiplyOccupierPower(6);
		assertEquals(6, fakeTile1.multiplyCalls.getLast());
		
		tile = StealthyDecorator.getApplier().apply(tile);
		assertEquals(-105, tile.getDifficulty());
	}
}
