package turn;

import grid.TileGrid;

public interface TurnStage {

    /**
     * executes all actions related to the turn stage upon the provided {@code tileGrid}
     * @param tileGrid the grid to execute all changes upon
     */
    public void execute(TileGrid tileGrid);
}
