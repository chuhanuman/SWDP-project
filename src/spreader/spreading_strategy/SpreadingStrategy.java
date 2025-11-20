package spreader.spreading_strategy;

import spreader.Spreader;
import grid.GridView;
import simulation.TurnChange;

public interface SpreadingStrategy {
	/**
	 * Tells the strategy to queue up any move actions it wants to do
	 * @param grid simulation grid to view
	 * @param simulation simulation object to queue actions to
	 * @param spreader the spreader to queue actions for
	 */
	public abstract void getMoveActions(GridView grid, TurnChange simulation, Spreader spreader);
}
