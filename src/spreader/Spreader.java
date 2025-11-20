package spreader;

import grid.GridView;
import simulation.TurnChange;

public interface Spreader {
	/**
	 * Tells the spreader to queue up any movements it wants its infections to make
	 * @param grid simulation grid to view
	 * @param simulation simulation object to queue actions to
	 */
	public abstract void getMoveActions(GridView grid, TurnChange simulation);
	
	/**
	 * Tells the spreader to queue up any resource extractions it wants to do
	 * @param grid simulation grid to view
	 * @param simulation simulation object to queue actions to
	 */
	public abstract void getExtractActions(GridView grid, TurnChange simulation);
}
