package simulation;

import java.util.List;

import spreader.Spreader;
import tile.MutableTile;
import tile.ViewableTile;

public class DefaultGridView extends GridView {

    private List<List<MutableTile>> tileGrid;

    public DefaultGridView(List<List<MutableTile>> tileGrid) {
        this.tileGrid = tileGrid;
    }

    @Override
    public Iterable<ViewableTile> getOccupiedTiles(Spreader spreader) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOccupiedTiles'");
    }

    @Override
    public Iterable<ViewableTile> getUnoccupiedResourceTiles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUnoccupiedResourceTiles'");
    }

    @Override
    public Iterable<ViewableTile> getAllTilesInRange(ViewableTile tile, int range) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTilesInRange'");
    }
    
}
