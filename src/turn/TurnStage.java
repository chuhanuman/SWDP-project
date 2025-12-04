package turn;

import grid.GridView;
import grid.TileGrid;

/**
 * Represents one action stage of a turn
 * Role(s): n/a
 */
public interface TurnStage {
    
    /**
     * Gathers all actions for the turn without executing them
     * @param gridView the grid view object to use to gather actions
     */
    public abstract void gatherActions(GridView gridView);

    /**
     * executes all actions related to the turn stage upon the provided {@code tileGrid}
     * @param tileGrid the grid to execute all changes upon
     */
    public void executeStage(TileGrid tileGrid);
}
