package spreader.extraction_strategy;

import spreader.Spreader;
import tile.Tile;

public class SlowExtraction implements ExtractionStrategy {
	private double efficiency;
	private double maxConsumption;
	
	public SlowExtraction(double efficiency, double maxConsumption) {
		this.efficiency = efficiency;
		this.maxConsumption = maxConsumption;
	}
	
	@Override
	public void getExtractActions(Simulation simulation, Spreader spreader) {
		for (Tile tile : simulation.getOccupiedTiles(spreader)) {
			simulation.queueExtract(spreader, maxConsumption, efficiency);
		}
	}
}