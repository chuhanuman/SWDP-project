package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import turn.extract.ExtractScheduler;
import tile.ConstTile;

public class LearningExtraction implements ExtractionStrategy {
	private double efficiency;
	private double learningRate;
	
	public LearningExtraction(double efficiency, double learningRate) {
		this.efficiency = efficiency;
		this.learningRate = learningRate;
	}
	
	@Override
	public void getExtractActions(GridView grid, ExtractScheduler scheduler, Spreader spreader) {
		double tilesLearned = 0;
		for (ConstTile tile : grid.getOccupiedTiles(spreader)) {
			scheduler.queueExtract(tile, tile.getResources(), efficiency);
			
			if (tile.getResources() > 0) {
				tilesLearned++;
			}
		}
		
		efficiency *= Math.pow(learningRate, tilesLearned);
	}
}