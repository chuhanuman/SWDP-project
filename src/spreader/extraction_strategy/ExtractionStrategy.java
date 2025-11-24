package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import turn.extract.ExtractScheduler;

public interface ExtractionStrategy {
	/**
	 * Tells the strategy to queue up any resource extractions it wants to do
	 * @param grid grid to view
	 * @param scheduler object to queue actions to
	 * @param spreader the spreader to queue actions for
	 */
	public abstract void getExtractActions(GridView grid, ExtractScheduler scheduler, Spreader spreader);
}
