package spreader;

import simulation.Simulation;

public interface Spreader {
	/**
	 * Tells the spreader to queue up any movements it wants its infections to make
	 * @param simulation simulation to view and queue actions to
	 */
	public abstract void getMoveActions(Simulation simulation);
	
	/**
	 * Tells the spreader to queue up any resource extractions it wants to do
	 * @param simulation simulation to view and queue actions to
	 */
	public abstract void getExtractActions(Simulation simulation);
}
