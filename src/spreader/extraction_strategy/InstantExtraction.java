package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import turn.extract.ExtractScheduler;
import tile.ViewableTile;

/**
 * Concrete implementation of an extraction strategy that extracts everything
 * Role(s): concrete strategy role in strategy pattern
 */
public class InstantExtraction implements ExtractionStrategy {
	private double efficiency;
	
	public InstantExtraction(double efficiency) {
		this.efficiency = efficiency;
	}
	
	@Override
	public void getExtractActions(GridView grid, ExtractScheduler scheduler, Spreader spreader) {
		for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
			scheduler.queueExtract(tile, tile.getResources(), efficiency);
		}
	}
}
