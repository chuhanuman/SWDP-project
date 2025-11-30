package tests.grid;

import java.util.HashMap;
import java.util.Map;

import grid.GridPos;
import grid.GridView;
import spreader.Spreader;
import tile.ViewableTile;
import tile.ViewableTile;

public class FakeGridView implements GridView {
	private Iterable<ViewableTile> occupiedTiles;
	private ViewableTile easiestUnoccupiedResourceTile;
	private record TileRangeRequest(ViewableTile tile, int range) {}
	private Map<TileRangeRequest, Iterable<ViewableTile>> tileRangeRequests;
	
	public FakeGridView() {
		tileRangeRequests = new HashMap<TileRangeRequest, Iterable<ViewableTile>>();
	}
	
	@Override
	public int getNumRows() {
		return -1;
	}

	@Override
	public int getNumCols() {
		return -1;
	}
	
	@Override
	public GridPos getPos(ViewableTile tile) {
		return null;
	}

	@Override
	public ViewableTile getTile(GridPos pos) {
		return null;
	}
	
	@Override
	public Iterable<ViewableTile> getOccupiedTiles(Spreader spreader) {
		return occupiedTiles;
	}

	@Override
	public ViewableTile getEasiestUnoccupiedResourceTile() {
		return easiestUnoccupiedResourceTile;
	}

	@Override
	public Iterable<ViewableTile> getAllTilesInRange(ViewableTile tile, int range) {
		return tileRangeRequests.get(new TileRangeRequest(tile, range));
	}

	@Override
	public Iterable<ViewableTile> getAllTiles() {
		return null;
	}
	
	public void setOccupiedTiles(Iterable<ViewableTile> occupiedTiles) {
		this.occupiedTiles = occupiedTiles;
	}
	
	public void setEasiestUnoccupiedResourceTile(ViewableTile easiestUnoccupiedResourceTile) {
		this.easiestUnoccupiedResourceTile = easiestUnoccupiedResourceTile;
	}
	
	public void addTileRangeAnswer(ViewableTile tile, int range, Iterable<ViewableTile> answer) {
		tileRangeRequests.put(new TileRangeRequest(tile, range), answer);
	}
	
	public void resetTileRange() {
		tileRangeRequests.clear();
	}
}
