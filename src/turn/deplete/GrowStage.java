package turn.deplete;

import grid.GridView;
import grid.TileGrid;
import tile.ViewableTile;
import turn.TurnStage;

/**
 * Implementation for a turn stage where all spreaders grow/shrink proportionally.
 * Role(s): client role in iterator pattern
 */
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
    public void gatherActions(GridView gridView) {
        // nothing to prepare before execution
        return;
    }

    @Override
    public void executeStage(TileGrid tileGrid) {
        for (ViewableTile t : tileGrid.getAllTiles()) {
            tileGrid.multiplyOccupierPower(t.getID(), this.proportion);
        }
    }
}
