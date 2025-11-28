package tests.spreader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import spreader.Spreader;
import spreader.DefaultSpreader;
import spreader.extraction_strategy.InstantExtraction;
import spreader.extraction_strategy.LearningExtraction;
import spreader.extraction_strategy.SlowExtraction;
import tests.grid.FakeGridView;
import tests.turn.FakeExtractScheduler;
import tile.ConstTile;
import tile.DefaultTile;


public class ExtractionStrategyTests {
	private FakeExtractScheduler extractScheduler = new FakeExtractScheduler();
	private FakeGridView gridView = new FakeGridView();
	
	@Test
	public void instantExtractionTests() {
		Spreader spreader = new DefaultSpreader(null, new InstantExtraction(4.5));
		List<ConstTile> tiles = List.of(
			new ConstTile(new DefaultTile(0, 10)), 
			new ConstTile(new DefaultTile(0, 0)), 
			new ConstTile(new DefaultTile(0, 30)), 
			new ConstTile(new DefaultTile(0, 50))
		);
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(1)));
		extractScheduler.reset();
		spreader.getExtractActions(gridView, extractScheduler);
		assertEquals(2, extractScheduler.getNumExtractActions());
		assertTrue(extractScheduler.checkExtractAction(tiles.get(0), 10, 4.5));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(1), 0, 4.5));
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(2), tiles.get(3)));
		extractScheduler.reset();
		spreader.getExtractActions(gridView, extractScheduler);
		assertEquals(3, extractScheduler.getNumExtractActions());
		assertTrue(extractScheduler.checkExtractAction(tiles.get(0), 10, 4.5));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(2), 30, 4.5));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(3), 50, 4.5));
	}
	
	@Test
	public void learningExtractionTests() {
		Spreader spreader = new DefaultSpreader(null, new LearningExtraction(1, 1.1));
		List<ConstTile> tiles = List.of(
			new ConstTile(new DefaultTile(0, 10)), 
			new ConstTile(new DefaultTile(0, 0)), 
			new ConstTile(new DefaultTile(0, 30)), 
			new ConstTile(new DefaultTile(0, 0))
		);
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(1)));
		extractScheduler.reset();
		spreader.getExtractActions(gridView, extractScheduler);
		assertEquals(2, extractScheduler.getNumExtractActions());
		assertTrue(extractScheduler.checkExtractAction(tiles.get(0), 10, 1));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(1), 0, 1));
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(2), tiles.get(3)));
		extractScheduler.reset();
		spreader.getExtractActions(gridView, extractScheduler);
		assertEquals(3, extractScheduler.getNumExtractActions());
		assertTrue(extractScheduler.checkExtractAction(tiles.get(0), 10, 1.1));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(2), 30, 1.1));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(3), 0, 1.1));
		
		gridView.setOccupiedTiles(List.of(tiles.get(1), tiles.get(3)));
		extractScheduler.reset();
		spreader.getExtractActions(gridView, extractScheduler);
		assertEquals(2, extractScheduler.getNumExtractActions());
		assertTrue(extractScheduler.checkExtractAction(tiles.get(1), 0, 1.331));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(3), 0, 1.331));
	}
	
	@Test
	public void slowExtractionTests() {
		Spreader spreader = new DefaultSpreader(null, new SlowExtraction(2, 4));
		List<ConstTile> tiles = List.of(
			new ConstTile(new DefaultTile(0, 10)), 
			new ConstTile(new DefaultTile(0, 0)), 
			new ConstTile(new DefaultTile(0, 30)), 
			new ConstTile(new DefaultTile(0, 0))
		);
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(1)));
		extractScheduler.reset();
		spreader.getExtractActions(gridView, extractScheduler);
		assertEquals(2, extractScheduler.getNumExtractActions());
		assertTrue(extractScheduler.checkExtractAction(tiles.get(0), 4, 2));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(1), 4, 2));
		
		gridView.setOccupiedTiles(List.of(tiles.get(0), tiles.get(2), tiles.get(3)));
		extractScheduler.reset();
		spreader.getExtractActions(gridView, extractScheduler);
		assertEquals(3, extractScheduler.getNumExtractActions());
		assertTrue(extractScheduler.checkExtractAction(tiles.get(0), 4, 2));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(2), 4, 2));
		assertTrue(extractScheduler.checkExtractAction(tiles.get(3), 4, 2));
	}
}
