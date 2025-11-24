package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import turn.extract.ExtractScheduler;
import tile.ConstTile;

public class SlowExtraction implements ExtractionStrategy {
	private double efficiency;
	private double maxConsumption;
	
	public SlowExtraction(double efficiency, double maxConsumption) {
		this.efficiency = efficiency;
		this.maxConsumption = maxConsumption;
	}
	
	@Override
	public void getExtractActions(GridView grid, ExtractScheduler scheduler, Spreader spreader) {
		for (ConstTile tile : grid.getOccupiedTiles(spreader)) {
			scheduler.queueExtract(tile, maxConsumption, efficiency);
		}
	}
}