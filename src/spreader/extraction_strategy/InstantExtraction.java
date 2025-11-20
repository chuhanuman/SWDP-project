package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import simulation.TurnChange;
import tile.ConstTile;

public class InstantExtraction implements ExtractionStrategy {
	private double efficiency;
	
	public InstantExtraction(double efficiency) {
		this.efficiency = efficiency;
	}
	
	@Override
	public void getExtractActions(GridView grid, TurnChange simulation, Spreader spreader) {
		for (ConstTile tile : grid.getOccupiedTiles(spreader)) {
			simulation.queueExtract(tile, tile.getResources(), efficiency);
		}
	}
}
