package spreader;

import spreader.extraction_strategy.ExtractionStrategy;
import spreader.spreading_strategy.SpreadingStrategy;

public class DefaultSpreader implements Spreader {
	private SpreadingStrategy spreadingStrategy;
	private ExtractionStrategy extractionStrategy;
	
	public DefaultSpreader(SpreadingStrategy spreadingStrategy, ExtractionStrategy extractionStrategy) {
		this.spreadingStrategy = spreadingStrategy;
		this.extractionStrategy = extractionStrategy;
	}
	
	@Override
	public void getMoveActions(Simulation simulation) {
		spreadingStrategy.getMoveActions(simulation, this);
	}
	
	@Override
	public void getExtractActions(Simulation simulation) {
		extractionStrategy.getExtractActions(simulation, this);
	}
}
