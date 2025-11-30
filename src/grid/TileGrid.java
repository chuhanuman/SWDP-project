package grid;

import java.util.Collection;
import java.util.UUID;

import spreader.Spreader;
import tile.ConstTile;
import tile.Tile;
import tile.TileDecorator;
import tile.ViewableTile;

public abstract class TileGrid implements Iterable<Tile> {
    public static abstract class Builder {
        public abstract TileGrid build();
    }
    
    /**
     * Get the (row, col) position of the tile in the grid
     * @param tile the tile to get the position of
     * @return (r, c) or null
     */
    public abstract GridPos getPos(ViewableTile tile);

    /**
     * Get the tile at the (row, col) position in the grid
     * @param pos the (row, col) position to get the tile
     * @return the tile or null
     */
    public abstract Tile get(GridPos pos);
    
    /**
     * Get the tile with the provided ID in the grid
     * @param id the id of the tile to find
     * @return the tile or null
     */
    public abstract Tile get(UUID id);

    /**
     * Get the corresponding tile from the const tile instance
     * @param tile the const tile instance
     * @return the tile or null
     */
    public Tile get(ConstTile tile) {
        return this.get(tile.getID());
    }

    /**
     * Returns a collection of all active spreaders in the grid
     * @return
     */
    public abstract Collection<Spreader> getSpreaders();

    /**
     * Decorate the tile at (row, col) with the provided decorator applier
     * @param pos the (row, col) position of the tile to decorate
     * @param decoratorFunc the function which applies the decorator to the tile
     */
    public abstract void decorateTile(GridPos pos, TileDecorator.Applier decoratorFunc);

    public abstract int getNumRows();
    public abstract int getNumCols();
}
