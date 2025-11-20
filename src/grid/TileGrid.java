package grid;

import tile.Tile;
import tile.ViewableTile;

public abstract class TileGrid implements Iterable<Tile> {
    public static abstract class Builder {
        public abstract TileGrid build();
    }
    
    public abstract GridPos getPos(ViewableTile tile);

    public abstract Tile get(GridPos pos);
}
