package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import turn.extract.ExtractScheduler;
import tile.ViewableTile;

/**
 * Concrete implementation of an extraction strategy that extracts with a higher efficiency each time
 * Role(s): concrete strategy role in strategy pattern, client role in iterator pattern
 */
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
		for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
			scheduler.queueExtract(tile, tile.getResources(), efficiency);
			
			if (tile.getResources() > 0) {
				tilesLearned++;
			}
		}
		
		efficiency *= Math.pow(learningRate, tilesLearned);
	}
}