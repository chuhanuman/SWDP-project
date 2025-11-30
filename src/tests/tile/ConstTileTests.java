package tests.tile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import spreader.Spreader;
import tests.spreader.FakeSpreader;
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
		Spreader spreader = new FakeSpreader();
		FakeTile tile = new FakeTile();
		tile.difficulty = -5;
		tile.resources = -6;
		tile.occupier = spreader;
		tile.occupierPower = -7;
		tile.id = UUID.randomUUID();
		
		ConstTile constTile = new ConstTile(tile);
		assertEquals(-5, constTile.getDifficulty());
		assertEquals(-6, constTile.getResources());
		assertEquals(spreader, constTile.getOccupier());
		assertEquals(-7, constTile.getOccupierPower());
		assertEquals(tile.id, constTile.getID());
	}
	
	@Test
	public void afterChangesTests() {
		FakeTile tile = new FakeTile();
		Spreader spreader = new FakeSpreader();
		tile.difficulty = -5;
		tile.resources = -6;
		tile.occupier = spreader;
		tile.occupierPower = -7;
		tile.id = UUID.randomUUID();
		
		ConstTile constTile = new ConstTile(tile);
		assertEquals(-5, constTile.getDifficulty());
		assertEquals(-6, constTile.getResources());
		assertEquals(spreader, constTile.getOccupier());
		assertEquals(-7, constTile.getOccupierPower());
		assertEquals(tile.id, constTile.getID());
		
		tile.difficulty = 5;
		tile.resources = 6;
		tile.occupier = null;
		tile.occupierPower = 7;
		tile.id = UUID.randomUUID();
		constTile = new ConstTile(tile);
		assertEquals(5, constTile.getDifficulty());
		assertEquals(6, constTile.getResources());
		assertEquals(null, constTile.getOccupier());
		assertEquals(7, constTile.getOccupierPower());
		assertEquals(tile.id, constTile.getID());
	}
}
