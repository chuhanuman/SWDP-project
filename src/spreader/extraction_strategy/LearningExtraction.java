package spreader.extraction_strategy;

import spreader.Spreader;
import tile.Tile;

public class LearningExtraction implements ExtractionStrategy {
	private double efficiency;
	private double learningRate;
	
	public LearningExtraction(double efficiency, double learningRate) {
		this.efficiency = efficiency;
		this.learningRate = learningRate;
	}
	
	@Override
	public void getExtractActions(Simulation simulation, Spreader spreader) {
		double tilesLearned = 0;
		for (Tile tile : simulation.getOccupiedTiles(spreader)) {
			simulation.queueExtract(spreader, tile.getResources(), efficiency);
			
			if (tile.getResources() > 0) {
				tilesLearned++;
			}
		}
		
		efficiency *= Math.pow(learningRate, tilesLearned);
	}
}