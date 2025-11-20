package simulation;


import grid.DefaultGridView;
import grid.GridView;
import grid.TileGrid;

public class TileGridSimulation extends Simulation {
    public static class Builder extends Simulation.Builder {
        private TileGrid tileGrid;

        public Builder(TileGrid tileGrid) {
            super();
            this.tileGrid = tileGrid;
        }

        @Override
        public Simulation build() {
            return new TileGridSimulation(this.rngSeed, this.powerLossRate, this.tileGrid);
        }
    }

    private TileGrid tileGrid;
    
    private GridView gridView;
    private TurnChange turnChange;

    protected TileGridSimulation(long rngSeed, double powerLossRate, TileGrid tileGrid) {
        super(rngSeed, powerLossRate);
        this.tileGrid = tileGrid;
        this.gridView = new DefaultGridView(this.tileGrid);
        this.turnChange = new TurnChange();
    }
}
