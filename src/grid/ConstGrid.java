package grid;

import java.util.Iterator;
import java.util.NoSuchElementException;

import spreader.Spreader;
import tile.Tile;
import tile.ViewableTile;

public class ConstGrid implements GridView {
    private TileGrid tileGrid;

    public ConstGrid(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
    }

	@Override
	public int getNumRows() {
		return tileGrid.getNumRows();
	}

	@Override
	public int getNumCols() {
		return tileGrid.getNumCols();
	}
	
	@Override
	public GridPos getPos(ViewableTile tile) {
		return tileGrid.getPos(tile);
	}
	
	@Override
	public ViewableTile getTile(GridPos pos) {
		return tileGrid.getTile(pos);
	}

	@Override
	public Iterable<ViewableTile> getOccupiedTiles(Spreader spreader) {
		return tileGrid.getOccupiedTiles(spreader);
	}

	@Override
	public ViewableTile getEasiestUnoccupiedResourceTile() {
		return tileGrid.getEasiestUnoccupiedResourceTile();
	}

	@Override
	public Iterable<ViewableTile> getAllTilesInRange(ViewableTile tile, int range) {
		return tileGrid.getAllTilesInRange(tile, range);
	}

	@Override
	public Iterable<ViewableTile> getAllTiles() {
		return tileGrid.getAllTiles();
	}

	@Override
	public Iterable<Spreader> getSpreaders() {
		return tileGrid.getSpreaders();
	}
}
