package turn.extract;

import grid.TileGrid;
import tile.ConstTile;
import tile.Tile;
import turn.SimulationAction;

public record ExtractAction(ConstTile tile, double resourcesToExtract, double efficiency) 
       implements SimulationAction {

    @Override
    public void execute(TileGrid tileGrid) {
        Tile mutableTile = tileGrid.get(this.tile);
        double resources = mutableTile.extract(resourcesToExtract * efficiency);
        mutableTile.addFlatOccupierPower(resources);
    }
    
}
