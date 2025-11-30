package tests.spreader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import prng.PRNG;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.spreading_strategy.CowardSpreading;
import spreader.spreading_strategy.GreedySpreading;
import spreader.spreading_strategy.RandomSpreading;
import tests.grid.FakeGridView;
import tests.turn.FakeMoveScheduler;
import tile.Tile;
import tile.ConstTile;
import tile.DefaultTile;

public class SpreadingStrategyTests {
	private FakeMoveScheduler moveScheduler = new FakeMoveScheduler();
	private FakeGridView gridView = new FakeGridView();
	
	@Test
	public void cowardSpreadingTests() {
		Spreader spreader = new DefaultSpreader(new CowardSpreading(), null);
		List<ConstTile> tiles = List.of(
			new ConstTile(new DefaultTile(30, 10, spreader, 0.1)), 
			new ConstTile(new DefaultTile(45, 5, spreader, 1.1)),
			new ConstTile(new DefaultTile(30, 0, spreader, 0.1)), 
			new ConstTile(new DefaultTile(45, 0, spreader, 2)), 
			new ConstTile(new DefaultTile(50, 30)), 
			new ConstTile(new DefaultTile(5, 0)),
			new ConstTile(new DefaultTile(40, 20))
		);
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(1), tiles.get(2), tiles.get(3)));
		gridView.setEasiestUnoccupiedResourceTile(tiles.get(6));
		moveScheduler.reset();
		spreader.getMoveActions(gridView, moveScheduler);
		assertEquals(3, moveScheduler.getNumMoveActions());
		assertTrue(moveScheduler.checkMoveAction(tiles.get(1), tiles.get(6), 0.1));
		assertTrue(moveScheduler.checkMoveAction(tiles.get(2), tiles.get(6), 0.1));
		assertTrue(moveScheduler.checkMoveAction(tiles.get(3), tiles.get(6), 2));
		
		
		gridView.setOccupiedTiles(List.of(tiles.get(1), tiles.get(2), tiles.get(3)));
		gridView.setEasiestUnoccupiedResourceTile(tiles.get(0));
		moveScheduler.reset();
		spreader.getMoveActions(gridView, moveScheduler);
		assertEquals(3, moveScheduler.getNumMoveActions());
		assertTrue(moveScheduler.checkMoveAction(tiles.get(1), tiles.get(0), 0.1));
		assertTrue(moveScheduler.checkMoveAction(tiles.get(2), tiles.get(0), 0.1));
		assertTrue(moveScheduler.checkMoveAction(tiles.get(3), tiles.get(0), 2));
	}
	
	@Test
	public void greedySpreadingTests() {
		Spreader spreader = new DefaultSpreader(new GreedySpreading(4), null);
		List<ConstTile> tiles = List.of(
			new ConstTile(new DefaultTile(0.1, 0, spreader, 60)),
			new ConstTile(new DefaultTile(0.2, 40, spreader, 120)), 
			new ConstTile(new DefaultTile(3, 30)), 
			new ConstTile(new DefaultTile(2, 0)),
			new ConstTile(new DefaultTile(5, 20))
		);
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(1)));
		gridView.resetTileRange();
		gridView.addTileRangeAnswer(tiles.get(0), 4, List.of(tiles.get(1), tiles.get(2), tiles.get(3)));
		gridView.addTileRangeAnswer(tiles.get(1), 4, List.of(tiles.get(0), tiles.get(1), tiles.get(4)));
		moveScheduler.reset();
		spreader.getMoveActions(gridView, moveScheduler);
		assertEquals(4, moveScheduler.getNumMoveActions());
		assertTrue(moveScheduler.checkMoveAction(tiles.get(0), tiles.get(1), 15));
		assertTrue(moveScheduler.checkMoveAction(tiles.get(0), tiles.get(2), 45));
		assertTrue(moveScheduler.checkMoveAction(tiles.get(1), tiles.get(1), 20));
		assertTrue(moveScheduler.checkMoveAction(tiles.get(1), tiles.get(4), 100));
	}
	
	@Test
	public void randomSpreadingTests() {
		PRNG.seed((new Random()).nextLong());
		Spreader spreader = new DefaultSpreader(new RandomSpreading(), null);
		List<ConstTile> tiles = List.of(
			new ConstTile(new DefaultTile(0.1, 0, spreader, 3)),
			new ConstTile(new DefaultTile(0.2, 40, spreader, 6)), 
			new ConstTile(new DefaultTile(3, 30)), 
			new ConstTile(new DefaultTile(2, 0)),
			new ConstTile(new DefaultTile(5, 20))
		);
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(1)));
		gridView.resetTileRange();
		gridView.addTileRangeAnswer(tiles.get(0), 1, List.of(tiles.get(1), tiles.get(2), tiles.get(3)));
		gridView.addTileRangeAnswer(tiles.get(1), 1, List.of(tiles.get(0), tiles.get(1), tiles.get(4)));
		
		List<Double> totalAllocations = new ArrayList<Double>(List.of(0., 0., 0., 0., 0., 0.));
		int loops = 100000;
		for (int i = 0; i < loops; i++) {
			moveScheduler.reset();
			spreader.getMoveActions(gridView, moveScheduler);
			assertEquals(6, moveScheduler.getNumMoveActions());
			totalAllocations.set(0, totalAllocations.get(0) + moveScheduler.checkMoveAction(tiles.get(0), tiles.get(1)));
			totalAllocations.set(1, totalAllocations.get(1) + moveScheduler.checkMoveAction(tiles.get(0), tiles.get(2)));
			totalAllocations.set(2, totalAllocations.get(2) + moveScheduler.checkMoveAction(tiles.get(0), tiles.get(3)));
			totalAllocations.set(3, totalAllocations.get(3) + moveScheduler.checkMoveAction(tiles.get(1), tiles.get(0)));
			totalAllocations.set(4, totalAllocations.get(4) + moveScheduler.checkMoveAction(tiles.get(1), tiles.get(1)));
			totalAllocations.set(5, totalAllocations.get(5) + moveScheduler.checkMoveAction(tiles.get(1), tiles.get(4)));
		}
		
		assertEquals(1, totalAllocations.get(0) / loops, 0.005);
		assertEquals(1, totalAllocations.get(1) / loops, 0.005);
		assertEquals(1, totalAllocations.get(2) / loops, 0.005);
		assertEquals(2, totalAllocations.get(3) / loops, 0.01);
		assertEquals(2, totalAllocations.get(4) / loops, 0.01);
		assertEquals(2, totalAllocations.get(5) / loops, 0.01);
	}
}
