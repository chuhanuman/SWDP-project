package simulation;

import tile.ViewableTile;
import spreader.Spreader;

public abstract class GridView {

    /**
     * Provides a view of all tiles occupied by the provided {@code spreader}
     * @param spreader the Spreader object to search for within the grid
     * @return a read-only view of all tiles occupied by spreader
     */
    public abstract Iterable<ViewableTile> getOccupiedTiles(Spreader spreader);

    /**
     * Provides a view of all tiles unnocupied by any spreaders
     * @return a read-only view of all tiles unnocupied by any spreaders
     */
    public abstract Iterable<ViewableTile> getUnoccupiedResourceTiles();

    /**
     * Provides a view of all tiles in a square range of the provided tile in the grid.
     * @param tile the tile to base the range around
     * @param range the distance from tile to provide within
     * @return all tiles within {@code range} of {@code tile}
     */
    public abstract Iterable<ViewableTile> getAllTilesInRange(ViewableTile tile, int range);
}
