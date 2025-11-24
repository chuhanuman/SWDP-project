package spreader;

import spreader.extraction_strategy.ExtractionStrategy;
import spreader.spreading_strategy.SpreadingStrategy;
import grid.GridView;
import turn.move.MoveScheduler;
import turn.extract.ExtractScheduler;

public class DefaultSpreader implements Spreader {
	private SpreadingStrategy spreadingStrategy;
	private ExtractionStrategy extractionStrategy;
	
	public DefaultSpreader(SpreadingStrategy spreadingStrategy, ExtractionStrategy extractionStrategy) {
		this.spreadingStrategy = spreadingStrategy;
		this.extractionStrategy = extractionStrategy;
	}
	
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler) {
		spreadingStrategy.getMoveActions(grid, scheduler, this);
	}
	
	@Override
	public void getExtractActions(GridView grid, ExtractScheduler scheduler) {
		extractionStrategy.getExtractActions(grid, scheduler, this);
	}
}
