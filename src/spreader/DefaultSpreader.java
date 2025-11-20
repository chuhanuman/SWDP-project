package spreader;

import spreader.extraction_strategy.ExtractionStrategy;
import spreader.spreading_strategy.SpreadingStrategy;
import grid.GridView;
import simulation.TurnChange;

public class DefaultSpreader implements Spreader {
	private SpreadingStrategy spreadingStrategy;
	private ExtractionStrategy extractionStrategy;
	
	public DefaultSpreader(SpreadingStrategy spreadingStrategy, ExtractionStrategy extractionStrategy) {
		this.spreadingStrategy = spreadingStrategy;
		this.extractionStrategy = extractionStrategy;
	}
	
	@Override
	public void getMoveActions(GridView grid, TurnChange simulation) {
		spreadingStrategy.getMoveActions(grid, simulation, this);
	}
	
	@Override
	public void getExtractActions(GridView grid, TurnChange simulation) {
		extractionStrategy.getExtractActions(grid, simulation, this);
	}
}
