package spreader.extraction_strategy;

import spreader.Spreader;

public interface ExtractionStrategy {
	/**
	 * Tells the strategy to queue up any resource extractions it wants to do
	 * @param simulation simulation to view and queue actions to
	 * @param spreader the spreader to queue actions for
	 */
	public abstract void getExtractActions(Simulation simulation, Spreader spreader);
}
