package spreader.spreading_strategy;

import spreader.Spreader;

public interface SpreadingStrategy {
	/**
	 * Tells the strategy to queue up any move actions it wants to do
	 * @param simulation simulation to view and queue actions to
	 * @param spreader the spreader to queue actions for
	 */
	public abstract void getMoveActions(Simulation simulation, Spreader spreader);
}
