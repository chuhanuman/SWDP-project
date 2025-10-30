package spreader.extraction_strategy;

import spreader.Spreader;
import tile.Tile;

public class InstantExtraction implements ExtractionStrategy {
	private double efficiency;
	
	public InstantExtraction(double efficiency) {
		this.efficiency = efficiency;
	}
	
	@Override
	public void getExtractActions(Simulation simulation, Spreader spreader) {
		for (Tile tile : simulation.getOccupiedTiles(spreader)) {
			simulation.queueExtract(spreader, tile.getResources(), efficiency);
		}
	}
}
