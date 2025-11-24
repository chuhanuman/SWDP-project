package spreader.spreading_strategy;

import spreader.Spreader;
import grid.GridView;
import turn.move.MoveScheduler;

public interface SpreadingStrategy {
	/**
	 * Tells the strategy to queue up any move actions it wants to do
	 * @param grid simulation grid to view
	 * @param scheduler simulation object to queue actions to
	 * @param spreader the spreader to queue actions for
	 */
	public abstract void getMoveActions(GridView grid, MoveScheduler scheduler, Spreader spreader);
}
