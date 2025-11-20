package spreader.extraction_strategy;

import spreader.Spreader;
import grid.GridView;
import simulation.TurnChange;

public interface ExtractionStrategy {
	/**
	 * Tells the strategy to queue up any resource extractions it wants to do
	 * @param grid grid to view
	 * @param simulation object to queue actions to
	 * @param spreader the spreader to queue actions for
	 */
	public abstract void getExtractActions(GridView grid, TurnChange simulation, Spreader spreader);
}
