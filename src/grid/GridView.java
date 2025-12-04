package grid;

import tile.ViewableTile;

import spreader.Spreader;

/**
 * Represents a viewable 2D grid of tiles.
 * Role: iterator used in some methods
 */
public interface GridView {
	/**
     * Returns the number of rows
     * @return the number of rows
     */
	public abstract int getNumRows();
	
	/**
     * Returns the number of columns
     * @return the number of columns
     */
    public abstract int getNumCols();
    
    /**
     * Get the (row, col) position of the tile in the grid
     * @param tile the tile to get the position of
     * @return (r, c) or null
     */
    public abstract GridPos getPos(ViewableTile tile);
    
    /**
     * Returns the tile at a given position
     * @param pos position of tile
     * @return tile at given position
     */
    public ViewableTile getTile(GridPos pos);

    /**
     * Provides a view of all tiles occupied by the provided {@code spreader}
     * @param spreader the Spreader object to search for within the grid
     * @return a read-only view of all tiles occupied by spreader
     */
    public abstract Iterable<ViewableTile> getOccupiedTiles(Spreader spreader);

    /**
     * Returns the unoccupied resource tile with the lowest difficulty
     * @return the unoccupied resource tile with the lowest difficulty
     */
    public abstract ViewableTile getEasiestUnoccupiedResourceTile();

    /**
     * Provides a view of all tiles in a square range of the provided tile in the grid.
     * @param tile the tile to base the range around
     * @param range the distance from tile to provide within
     * @return all tiles within {@code range} of {@code tile}
     */
    public abstract Iterable<ViewableTile> getAllTilesInRange(ViewableTile tile, int range);
    
    /**
     * Provides a view of all tiles.
     * @return all tiles
     */
    public abstract Iterable<ViewableTile> getAllTiles();
    
    /**
     * Returns a collection of all active spreaders in the grid
     * @return a collection of all active spreaders in the grid
     */
    public abstract Iterable<Spreader> getSpreaders();
}
