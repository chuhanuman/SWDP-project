package tests.grid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import grid.GridPos;
import grid.GridView;
import spreader.Spreader;
import tile.ConstTile;
import tile.ViewableTile;

public class FakeGridView extends GridView {
	private Iterable<ConstTile> occupiedTiles, unoccupiedResourceTiles;
	private record TileRangeRequest(ViewableTile tile, int range) {}
	private Map<TileRangeRequest, Iterable<ConstTile>> tileRangeRequests;
	
	public FakeGridView() {
		tileRangeRequests = new HashMap<TileRangeRequest, Iterable<ConstTile>>();
	}
	
	@Override
	public Iterable<ConstTile> getOccupiedTiles(Spreader spreader) {
		return occupiedTiles;
	}

	@Override
	public Iterable<ConstTile> getUnoccupiedResourceTiles() {
		return unoccupiedResourceTiles;
	}

	@Override
	public Iterable<ConstTile> getAllTilesInRange(ViewableTile tile, int range) {
		return tileRangeRequests.get(new TileRangeRequest(tile, range));
	}

	@Override
	public ConstTile get(GridPos pos) {
		return null;
	}
	
	public void setOccupiedTiles(Iterable<ConstTile> occupiedTiles) {
		this.occupiedTiles = occupiedTiles;
	}
	
	public void setUnoccupiedResourceTiles(Iterable<ConstTile> unoccupiedResourceTiles) {
		this.unoccupiedResourceTiles = unoccupiedResourceTiles;
	}
	
	public void addTileRangeAnswer(ViewableTile tile, int range, Iterable<ConstTile> answer) {
		tileRangeRequests.put(new TileRangeRequest(tile, range), answer);
	}
	
	public void resetTileRange() {
		tileRangeRequests.clear();
	}

	@Override
	public Iterator<ConstTile> iterator() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'iterator'");
	}
}
