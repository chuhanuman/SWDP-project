package turn.extract;

import grid.TileGrid;
import logging.SimulationLogger;
import tile.ConstTile;
import tile.Tile;
import tile.ViewableTile;
import turn.SimulationAction;

public record ExtractAction(ViewableTile tile, double resourcesToExtract, double efficiency)
       implements SimulationAction {

    @Override
    public void execute(TileGrid tileGrid) {
        SimulationLogger.getInstance().log(
            new logging.events.ExtractionEvent(
                this.tile.getID(),
                resourcesToExtract,
                efficiency
            )
        );
        tileGrid.extractTile(this.tile.getID(), resourcesToExtract, efficiency);
    }

}
