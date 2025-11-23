package grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import tile.DefaultTile;
import tile.Tile;
import tile.TileDecorator;
import tile.ViewableTile;

public class DefaultTileGrid extends TileGrid {
    public static class Builder extends TileGrid.Builder {
        private List<List<Tile>> tileGrid;
        private int numRows, numCols;

        private Map<UUID, GridPos> tilePosMap;

        private DefaultTile defaultTile;
        boolean firstBuild;

        public Builder(int numRows, int numCols) throws IllegalArgumentException {
            super();

            this.setDimensions(numRows, numCols);

            this.defaultTile = null;
        }

        /**
         * Sets/resets the grid size for the builder and initializes the grid to null values.
         * @param numRows the number of rows (vertical size) in grid
         * @param numCols the number of columns (horizontal size) in grid
         * @throws IllegalArgumentException if {@code numRows} or {@code numCols} negative or zero
         */
        private void setDimensions(int numRows, int numCols) throws IllegalArgumentException {
            if (numRows <= 0 || numCols <= 0) {
                throw new IllegalArgumentException("Invalid bounds: r: " + numRows + ", c: " + numCols);
            }

            this.firstBuild = true;
            this.numRows = numRows;
            this.numCols = numCols;
            this.tileGrid = new ArrayList<List<Tile>>(numRows);
            for (int i = 0; i < numRows; i++) {
                tileGrid.add(new ArrayList<Tile>(numCols));
                for (int j = 0; j < numCols; j++) {
                    tileGrid.get(i).add(null);
                }
            }

            this.tilePosMap = new HashMap<>(numRows * numCols);
        }

        /**
         * Sets the provided tile within the grid at the tile's location.
         * @param tile the tile to put within the grid
         * @throws IllegalArgumentException if the tile's location (row, col) is out of bounds for the {@code tileGrid}
         * @throws IllegalStateException if {@code tileGrid} is uninitialized
         */
        public Builder setTile(Tile tile, GridPos pos) throws IllegalArgumentException, IllegalStateException{
            if (this.tileGrid == null) {
                throw new IllegalStateException("tileGrid is uninitialized");
            }

            if (!pos.inBounds(0, 0, numRows-1, numCols-1)) {
                throw new IllegalArgumentException("tile location out of bounds: (" + pos.row() + ", " + pos.col() + ") not in [(0, 0), (" 
                                                    + numRows + ", " + numCols + "))");
            }
            this.validSetTile(tile, pos);

            return this;
        }

        /**
         * provides a default {@code Tile.Builder} for filling in uninitialized tiles at build time.
         * @param tileBuilder the builder specifying the default tile
         */
        public Builder setDefaultTile(DefaultTile defaultTile) {
            this.defaultTile = defaultTile;
            return this;
        }

        @Override
        public TileGrid build() {
            if (firstBuild) {
                this.setDefaultTiles();
            }

            return new DefaultTileGrid(tileGrid, numRows, numCols, tilePosMap);
        }

        private void setDefaultTiles() {
            for (int i = 0; i < this.numRows; i++) {
                for (int j = 0; j < this.numCols; j++) {
                    if (this.tileGrid.get(i).get(j) == null) {
                        this.validSetTile(defaultTile.copy(), new GridPos(i, j));
                    }
                }
            }
        }

        private void validSetTile(Tile tile, GridPos pos) {
            tileGrid.get(pos.row()).set(pos.col(), tile);
            tilePosMap.put(tile.getID(), pos);
        }
    }

    private final List<List<Tile>> tileGrid;
    private final int numRows;
    private final int numCols;

    private final Map<UUID, GridPos> tilePosMap;

    protected DefaultTileGrid(List<List<Tile>> tileGrid, int numRows, int numCols, Map<UUID, GridPos> tilePosMap) {
        this.tileGrid = tileGrid;
        this.numRows = numRows;
        this.numCols = numCols;

        this.tilePosMap = tilePosMap;
    }

    @Override
    public Iterator<Tile> iterator() {
        return new Iterator<Tile>() {
            private int r = 0;
            private int c = 0;

            @Override
            public boolean hasNext() {
                if (c >= numCols) {
                    r++;
                    c = 0;
                }
                return r < numRows;
            }

            @Override
            public Tile next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return tileGrid.get(r).get(c++);
            }
        };
    }

    @Override
    public GridPos getPos(ViewableTile tile) {
        return tilePosMap.get(tile.getID());
    }

    @Override
    public Tile get(GridPos pos) throws IllegalArgumentException {
        if (!pos.inBounds(0, 0, numRows-1, numCols-1)) {
            throw new IllegalArgumentException("tile location out of bounds: (" + pos.row() + ", " + pos.col() + ") not in [(0, 0), (" 
                                                    + numRows + ", " + numCols + "))");
        }
        
        return tileGrid.get(pos.row()).get(pos.col());
    } 

    @Override
    public void decorateTile(GridPos pos, TileDecorator.Applier decoratorFunc) {
        tileGrid.get(pos.row()).set(pos.col(), decoratorFunc.apply(this.get(pos)));
    }

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }
}
