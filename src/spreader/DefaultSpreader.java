package spreader;

import spreader.extraction_strategy.ExtractionStrategy;
import spreader.spreading_strategy.SpreadingStrategy;
import grid.GridView;
import turn.move.MoveScheduler;
import turn.extract.ExtractScheduler;

/**
 * Default concrete implementation of a spreader
 * Role(s): context role in strategy pattern
 */
public class DefaultSpreader implements Spreader {
	private SpreadingStrategy spreadingStrategy;
	private ExtractionStrategy extractionStrategy;
	private String name;
	
	public DefaultSpreader(SpreadingStrategy spreadingStrategy, ExtractionStrategy extractionStrategy) {
		this.spreadingStrategy = spreadingStrategy;
		this.extractionStrategy = extractionStrategy;
		this.name = "unnamed";
	}
	
	public DefaultSpreader(SpreadingStrategy spreadingStrategy, ExtractionStrategy extractionStrategy, String name) {
		this.spreadingStrategy = spreadingStrategy;
		this.extractionStrategy = extractionStrategy;
		this.name = name;
	}
	
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler) {
		spreadingStrategy.getMoveActions(grid, scheduler, this);
	}
	
	@Override
	public void getExtractActions(GridView grid, ExtractScheduler scheduler) {
		extractionStrategy.getExtractActions(grid, scheduler, this);
	}
	
	@Override
	public String getName() {
		return name;
	}
}
