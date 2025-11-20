package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import simulation.TurnChange;
import tile.ConstTile;

public class SlowExtraction implements ExtractionStrategy {
	private double efficiency;
	private double maxConsumption;
	
	public SlowExtraction(double efficiency, double maxConsumption) {
		this.efficiency = efficiency;
		this.maxConsumption = maxConsumption;
	}
	
	@Override
	public void getExtractActions(GridView grid, TurnChange simulation, Spreader spreader) {
		for (ConstTile tile : grid.getOccupiedTiles(spreader)) {
			simulation.queueExtract(tile, maxConsumption, efficiency);
		}
	}
}