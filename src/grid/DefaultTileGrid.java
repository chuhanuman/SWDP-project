package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

import logging.SimulationLogger;
import spreader.Spreader;
import tile.ConstTile;
import tile.Tile;
import tile.TileDecorator;
import tile.ViewableTile;

public class DefaultTileGrid implements TileGrid {
    public static class Builder extends TileGrid.Builder {
        private List<Tile> tileGrid;
        private int numRows, numCols;

        private Tile defaultTile;
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
            this.tileGrid = new ArrayList<Tile>(numRows * numCols);
            for (int i = 0; i < numRows * numCols; i++) {
                tileGrid.add(null);
            }
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
        public Builder setDefaultTile(Tile defaultTile) {
            this.defaultTile = defaultTile;
            return this;
        }

        @Override
        public TileGrid build() {
            if (firstBuild) {
                this.setDefaultTiles();
            }

            return new DefaultTileGrid(tileGrid, numRows, numCols);
        }

        private void setDefaultTiles() {
            for (int index = 0, row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++, index++) {
                    if (tileGrid.get(index) == null) {
                        validSetTile(defaultTile.copy(), new GridPos(row, col));
                    }
                }
            }
        }

        private void validSetTile(Tile tile, GridPos pos) {
        	tileGrid.set(pos.get1DIndex(numCols), tile);
        }
    }

    private final List<Tile> tileGrid;
    private final List<ViewableTile> constTileGrid;
    private final int numRows;
    private final int numCols;
    private final Map<UUID, GridPos> tilePosMap;
    private final Map<Spreader, HashSet<ViewableTile>> occupiedTiles;
    private final PriorityQueue<ViewableTile> unoccupiedResourceTiles; //Sorted by lowest difficulty to highest

    protected DefaultTileGrid(List<Tile> tileGrid, int numRows, int numCols) {
        this.tileGrid = tileGrid;
        this.constTileGrid = new ArrayList<ViewableTile>(numRows * numCols);
        for (Tile tile : tileGrid) {
        	constTileGrid.add(new ConstTile(tile));
        }
        
        this.numRows = numRows;
        this.numCols = numCols;

        tilePosMap = new HashMap<>(numRows * numCols);
        occupiedTiles = new HashMap<>();
        unoccupiedResourceTiles = new PriorityQueue<ViewableTile>(
			(ViewableTile left, ViewableTile right) -> Double.compare(left.getDifficulty(), right.getDifficulty())
		);
        for (int index = 0, row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++, index++) {
            	ViewableTile tile = constTileGrid.get(index);
        		GridPos pos = new GridPos(row, col);
        		tilePosMap.put(tile.getID(), pos);
        		updateTileReferences(null, tile);
        	}
        }
    }

    @Override
    public void decorateTile(GridPos pos, TileDecorator.Applier decoratorFunc) {
    	int index = pos.get1DIndex(numCols);
        Tile oldTile = tileGrid.get(index);
        tileGrid.set(index, decoratorFunc.apply(oldTile));

        String decoratorName = tileGrid.get(index).getClass().getSimpleName();
        SimulationLogger.getInstance().log(
            new logging.events.DecorationEvent(
                String.format("(%d,%d)", pos.row(), pos.col()),
                decoratorName
            )
        );

        ViewableTile oldViewable = constTileGrid.get(index);
        constTileGrid.set(index, new ConstTile(tileGrid.get(index)));
        updateTileReferences(oldViewable, constTileGrid.get(index));
    }
    
    @Override
	public void infectTile(UUID id, double power, Spreader spreader) {
    	ViewableTile tile = getTile(id);
    	Spreader oldSpreader = tile.getOccupier();
    	getModifiableTile(id).infect(power, spreader);
    	
    	if (tile.getOccupier() != oldSpreader) {
    		if (oldSpreader != null) {
    			occupiedTiles.get(spreader).remove(tile);
    		} else if (tile.getResources() > 0) {
				unoccupiedResourceTiles.remove(tile);
			}
    		
    		if (occupiedTiles.get(spreader) == null) {
				occupiedTiles.put(spreader, new HashSet<>());
			}
			
			occupiedTiles.get(spreader).add(tile);
    	}
	}

	@Override
	public void extractTile(UUID id, double amountToExtract, double efficiency) {
		ViewableTile tile = getTile(id);
		double resources = getModifiableTile(id).extract(amountToExtract);
		getModifiableTile(id).addFlatOccupierPower(resources * efficiency);
		
		if (tile.getResources() == 0) {
			unoccupiedResourceTiles.remove(tile);
		}
	}

	@Override
	public void addFlatOccupierPower(UUID id, double power) {
		ViewableTile tile = getTile(id);
		double oldPower = tile.getOccupierPower();
		Spreader oldSpreader = tile.getOccupier();
		getModifiableTile(id).addFlatOccupierPower(power);

		SimulationLogger.getInstance().log(
			new logging.events.PowerChangeEvent(
				id,
				oldPower,
				tile.getOccupierPower(),
				"flat"
			)
		);

		checkOccupierChange(oldSpreader, tile);
	}
	
	private void updateTileReferences(ViewableTile oldTile, ViewableTile newTile) {
		if (oldTile != null) {
			if (oldTile.getOccupier() != null) {
				occupiedTiles.get(oldTile.getOccupier()).remove(oldTile);
			} else if (oldTile.getResources() > 0) {
				unoccupiedResourceTiles.remove(oldTile);
			}
		}
		
		if (newTile.getOccupier() != null) {
			if (occupiedTiles.get(newTile.getOccupier()) == null) {
				occupiedTiles.put(newTile.getOccupier(), new HashSet<>());
			}
			
			occupiedTiles.get(newTile.getOccupier()).add(newTile);
		} else if (newTile.getResources() > 0) {
			unoccupiedResourceTiles.add(newTile);
		}
	}

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }
    
    @Override
    public GridPos getPos(ViewableTile tile) {
        return tilePosMap.get(tile.getID());
    }
    
    @Override
	public ViewableTile getTile(GridPos pos) {
		return constTileGrid.get(pos.get1DIndex(numCols));
	}
    
    private ViewableTile getTile(UUID id) {
        return constTileGrid.get(tilePosMap.get(id).get1DIndex(numCols));
    }
    
    private Tile getModifiableTile(UUID id) {
        return tileGrid.get(tilePosMap.get(id).get1DIndex(numCols));
    }

    @Override
    public Iterable<ViewableTile> getOccupiedTiles(Spreader spreader) {
    	if (occupiedTiles.get(spreader) != null) {
    		return occupiedTiles.get(spreader);
    	} else {
    		return Collections.emptyList();
    	}
    }

    @Override
    public ViewableTile getEasiestUnoccupiedResourceTile() {
        return unoccupiedResourceTiles.peek();
    }

    @Override
    public Iterable<ViewableTile> getAllTilesInRange(ViewableTile tile, int range) {
    	GridPos pos = getPos(tile);
        if (pos == null) {
        	return null;
        }
    	
    	List<ViewableTile> tilesInRange = new ArrayList<>();
    	int minR = Math.max(0, pos.row() - range);
        int maxR = Math.min(numRows - 1, pos.row() + range);
        int minC = Math.max(0, pos.col() - range);
        int maxC = Math.min(numCols - 1, pos.col() + range);
    	for (int row = minR; row <= maxR; row++) {
    		for (int col = minC; col <= maxC; col++) {
        		tilesInRange.add(constTileGrid.get((new GridPos(row, col)).get1DIndex(numCols)));
        	}
    	}
        
        return tilesInRange;
    }
    
    @Override
    public Iterable<ViewableTile> getAllTiles() {
        return constTileGrid;
    }

    @Override
    public Iterable<Spreader> getSpreaders() {
    	return occupiedTiles.keySet();
    }

    @Override
    public void multiplyOccupierPower(UUID id, double amount) {
        ViewableTile tile = getTile(id);
		double oldPower = tile.getOccupierPower();
		Spreader oldSpreader = tile.getOccupier();
		getModifiableTile(id).multiplyOccupierPower(amount);

		SimulationLogger.getInstance().log(
			new logging.events.PowerChangeEvent(
				id,
				oldPower,
				tile.getOccupierPower(),
				"multiply"
			)
		);

        checkOccupierChange(oldSpreader, tile);
    }

    private void checkOccupierChange(Spreader oldSpreader, ViewableTile tile) {
        if (tile.getOccupier() != oldSpreader) {
			occupiedTiles.get(oldSpreader).remove(tile);
			
			if (tile.getResources() > 0) {
				unoccupiedResourceTiles.add(tile);
			}
		}
    }
    
}
