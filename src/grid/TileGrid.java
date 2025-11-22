package grid;

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
     * Decorate the tile at (row, col) with the provided decorator applier
     * @param pos the (row, col) position of the tile to decorate
     * @param decoratorFunc the function which applies the decorator to the tile
     */
    public abstract void decorateTile(GridPos pos, TileDecorator.Applier decoratorFunc);
}
