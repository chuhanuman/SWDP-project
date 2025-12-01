package turn.deplete;

import grid.TileGrid;
import simulation.TileGridSimulation.View;
import tile.Tile;
import turn.TurnStage;

public class GrowStage implements TurnStage {

    private double proportion;

    /**
     * Stage which changes spreader power on each tile by a proportion each turn.
     * @param proportion the proportion of spreader power which will change
     *  e.g. 1.1 means 10% growth each turn
     */
    public GrowStage(double proportion) {
        this.proportion = proportion;
    }

    @Override
    public void gatherActions(View simulation) {
        // nothing to prepare before execution
        return;
    }

    @Override
    public void executeStage(TileGrid tileGrid) {
        for (Tile t : tileGrid) {
            t.multiplyOccupierPower(proportion);
        }
    }
}
