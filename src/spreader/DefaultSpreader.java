package spreader;

public class DefaultSpreader implements Spreader {
	private SpreadingStrategy spreadingStrategy;
	private ExtractionStrategy extractionStrategy;
	
	public DefaultSpreader(SpreadingStrategy spreadingStrategy, ExtractionStrategy extractionStrategy) {
		this.spreadingStrategy = spreadingStrategy;
		this.extractionStrategy = extractionStrategy;
	}
	
	@Override
	public void getMoveActions(Simulation simulation) {
		spreadingStrategy.getMoveActions(simulation);
	}
	
	@Override
	public void getExtractActions(Simulation simulation) {
		extractionStrategy.getExtractActions(simulation);
	}
}
