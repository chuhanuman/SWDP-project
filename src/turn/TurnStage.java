package turn;

import grid.TileGrid;
import simulation.TileGridSimulation;

public interface TurnStage {
    
    /**
     * Gathers all actions for the turn without executing them
     * @param simulation the simulation view object to use to gather actions
     */
    public abstract void gatherActions(TileGridSimulation.View simulation);

    /**
     * executes all actions related to the turn stage upon the provided {@code tileGrid}
     * @param tileGrid the grid to execute all changes upon
     */
    public void executeStage(TileGrid tileGrid);
}
