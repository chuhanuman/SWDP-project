package turn;

import grid.TileGrid;

public interface SimulationAction {

    /**
     * Execute the action upon the provided {@code tileGrid}
     * @param tileGrid the grid to execute the action upon
     */
    public void execute(TileGrid tileGrid);
}
