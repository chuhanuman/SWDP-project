package grid;

import java.util.List;

import spreader.Spreader;
import tile.Tile;
import tile.ConstTile;

public class DefaultGridView extends GridView {
    private TileGrid tileGrid;

    public DefaultGridView(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
    }

    @Override
    public Iterable<ConstTile> getOccupiedTiles(Spreader spreader) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOccupiedTiles'");
    }

    @Override
    public Iterable<ConstTile> getUnoccupiedResourceTiles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUnoccupiedResourceTiles'");
    }

    @Override
    public Iterable<ConstTile> getAllTilesInRange(ConstTile tile, int range) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTilesInRange'");
    }
    
}
