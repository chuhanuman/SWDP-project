package turn.extract;

import grid.TileGrid;
import tile.ViewableTile;
import turn.SimulationAction;

/**
 * Represents/handles logic for a single extract action
 * Role: n/a
 */
public record ExtractAction(ViewableTile tile, double resourcesToExtract, double efficiency)
       implements SimulationAction {

    @Override
    public void execute(TileGrid tileGrid) {
        tileGrid.extractTile(this.tile.getID(), resourcesToExtract, efficiency);
    }
    
}
