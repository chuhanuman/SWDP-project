package turn.extract;

import grid.TileGrid;
import tile.ConstTile;
import tile.Tile;
import tile.ViewableTile;
import turn.SimulationAction;

public record ExtractAction(ViewableTile tile, double resourcesToExtract, double efficiency) 
       implements SimulationAction {

    @Override
    public void execute(TileGrid tileGrid) {
        tileGrid.extractTile(this.tile.getID(), resourcesToExtract, efficiency);
    }
    
}
