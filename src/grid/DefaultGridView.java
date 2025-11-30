package grid;

import java.util.Iterator;
import java.util.NoSuchElementException;

import spreader.Spreader;
import tile.Tile;
import tile.ViewableTile;
import tile.ConstTile;

public class DefaultGridView extends GridView {
    private TileGrid tileGrid;

    public DefaultGridView(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
    }

    protected abstract class FilterIterable implements Iterable<ConstTile> {
        @Override
        public final Iterator<ConstTile> iterator() {
            return new Iterator<ConstTile>() {
                private Iterator<Tile> it = tileGrid.iterator();
                Tile next = advance();

                @Override
                public boolean hasNext() {
                    return next != null;
                }

                @Override
                public ConstTile next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }

                    Tile current = next;
                    next = advance();
                    return new ConstTile(current);
                }

                private Tile advance() {
                    while (it.hasNext()) {
                        Tile t = it.next();
                        if (trueFilter(t)) return t;
                    }
                    return null;
                }
            };
        }

        /**
         * the boolean filter for the iterator
         * @param nextTile the tile to check
         * @return true if the conditions mean we want to provide the next tile, false if we should skip it
         */
        protected abstract boolean trueFilter(Tile nextTile);
    }

    @Override
    public Iterable<ConstTile> getOccupiedTiles(Spreader spreader) {
        return new FilterIterable() {
            @Override
            protected boolean trueFilter(Tile nextTile) {
                return nextTile.getOccupier() != null && nextTile.getOccupier().equals(spreader);
            }
        };
    }

    @Override
    public Iterable<ConstTile> getUnoccupiedResourceTiles() {
        return new FilterIterable() {
            @Override
            protected boolean trueFilter(Tile nextTile) {
                return nextTile.getOccupier() == null;
            }
        };
    }

    @Override
    public Iterable<ConstTile> getAllTilesInRange(ViewableTile tile, int range) {
        // O(r*c), could technically be a bit more efficient
        GridPos thisPos = tileGrid.getPos(tile);
        if (thisPos == null) return null;
        int minR = Math.max(0, thisPos.row() - range);
        int maxR = Math.min(tileGrid.getNumRows() - 1, thisPos.row() + range);
        int minC = Math.max(0, thisPos.col() - range);
        int maxC = Math.min(tileGrid.getNumCols() - 1, thisPos.col() + range);
        return new FilterIterable() {
            @Override
            protected boolean trueFilter(Tile nextTile) {
                return tileGrid.getPos(nextTile).inBounds(minR, minC, maxR, maxC);
            }
        };
    }

    @Override
    public ConstTile get(GridPos pos) {
        return new ConstTile(tileGrid.get(pos));
    }

    @Override
    public Iterator<ConstTile> iterator() {
        return new Iterator<ConstTile>() {

            Iterator<Tile> it = tileGrid.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public ConstTile next() {
                return new ConstTile(it.next());
            }
        };
    }
    
}
