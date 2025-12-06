package spreader;

import grid.GridView;
import turn.move.MoveScheduler;
import turn.extract.ExtractScheduler;

/**
 * Handles the interface for a spreader
 * Role(s): N/A
 */
public interface Spreader {
	/**
	 * Tells the spreader to queue up any movements it wants its infections to make
	 * @param grid simulation grid to view
	 * @param scheduler simulation object to queue actions to
	 */
	public abstract void getMoveActions(GridView grid, MoveScheduler scheduler);
	
	/**
	 * Tells the spreader to queue up any resource extractions it wants to do
	 * @param grid simulation grid to view
	 * @param scheduler simulation object to queue actions to
	 */
	public abstract void getExtractActions(GridView grid, ExtractScheduler scheduler);
	
	/**
	 * Returns the name of the spreader
	 * @return name of the spreader
	 */
	public abstract String getName();
}
