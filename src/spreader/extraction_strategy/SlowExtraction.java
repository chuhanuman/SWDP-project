package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import turn.extract.ExtractScheduler;
import tile.ViewableTile;

/**
 * Concrete implementation of an extraction strategy that extracts up to a maximum value
 * Role(s): concrete strategy role in strategy pattern, client role in iterator pattern
 */
public class SlowExtraction implements ExtractionStrategy {
	private double efficiency;
	private double maxConsumption;
	
	public SlowExtraction(double efficiency, double maxConsumption) {
		this.efficiency = efficiency;
		this.maxConsumption = maxConsumption;
	}
	
	@Override
	public void getExtractActions(GridView grid, ExtractScheduler scheduler, Spreader spreader) {
		for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
			scheduler.queueExtract(tile, maxConsumption, efficiency);
		}
	}
}