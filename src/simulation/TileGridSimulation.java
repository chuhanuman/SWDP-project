package simulation;

import java.util.ArrayList;
import java.util.List;

import tile.MutableTile;

public class TileGridSimulation extends Simulation {
    public static class Builder extends Simulation.Builder {
        private List<List<MutableTile>> tileGrid;
        private int numRows, numCols;
        private MutableTile.Builder defaultTile;
        boolean firstBuild;

        public Builder() {
            super();
            this.tileGrid = null;
            this.numRows = 0;
            this.numCols = 0;
            this.defaultTile = null;
            this.firstBuild = true;
        }

        /**
         * Sets the grid size for the builder and initializes the grid to null values.
         * @param numRows the number of rows (vertical size) in grid
         * @param numCols the number of columns (horizontal size) in grid
         * @throws IllegalArgumentException if {@code numRows} or {@code numCols} negative or zero
         */
        public void setDimensions(int numRows, int numCols) throws IllegalArgumentException {
            if (numRows <= 0 || numCols <= 0) {
                throw new IllegalArgumentException("Invalid bounds: r: " + numRows + ", c: " + numCols);
            }

            this.firstBuild = true;
            this.numRows = numRows;
            this.numCols = numCols;
            this.tileGrid = new ArrayList<List<MutableTile>>(numRows);
            for (int i = 0; i < numRows; i++) {
                tileGrid.add(new ArrayList<MutableTile>(numCols));
                for (int j = 0; j < numCols; j++) {
                    tileGrid.get(i).add(null);
                }
            }
        }

        /**
         * Sets the provided tile within the grid at the tile's location.
         * @param tile the tile to put within the grid
         * @throws IllegalArgumentException if the tile's location (row, col) is out of bounds for the {@code tileGrid}
         * @throws IllegalStateException if {@code tileGrid} is uninitialized
         */
        public void setTile(MutableTile tile) throws IllegalArgumentException, IllegalStateException{
        	//TODO: this doesn't actually do anything but throw errors?
            if (this.tileGrid == null) {
                throw new IllegalStateException("tileGrid is uninitialized");
            }

            /*
            int row = tile.getRow();
            int col = tile.getCol();

            if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
                throw new IllegalArgumentException("tile location out of bounds: (" + row + ", " + col + ") not in [(0, 0), (" 
                                                    + numRows + ", " + numCols + "))");
            }
            */
        }

        /**
         * provides a default {@code MutableTile.Builder} for filling in uninitialized tiles at build time.
         * @param tileBuilder the builder specifying the default tile
         */
        public void setDefaultTile(MutableTile.Builder tileBuilder) {
            this.defaultTile = tileBuilder;
        }

        @Override
        public Simulation build() {
            if (firstBuild) {
                this.setDefaultTiles();
            }

            return new TileGridSimulation(this.rngSeed, this.powerLossRate, this.tileGrid, this.numRows, this.numCols);
        }

        private void setDefaultTiles() {
            for (int i = 0; i < this.numRows; i++) {
                for (int j = 0; j < this.numCols; j++) {
                    if (this.tileGrid.get(i).get(j) == null) {
                        this.tileGrid.get(i).set(j, this.defaultTile.build());
                    }
                }
            }
        }
    }

    private List<List<MutableTile>> tileGrid;
    private int numRows, numCols;
    
    private GridView gridView;
    private TurnChange turnChange;

    protected TileGridSimulation(long rngSeed, double powerLossRate, List<List<MutableTile>> tileGrid, int numRows, int numCols) {
        super(rngSeed, powerLossRate);
        this.tileGrid = tileGrid;
        this.numRows = numRows;
        this.numCols = numCols;
        this.gridView = new DefaultGridView(this.tileGrid);
        this.turnChange = new TurnChange();
    }
}
