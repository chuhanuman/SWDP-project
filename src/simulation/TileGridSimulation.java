package simulation;


import grid.DefaultGridView;
import grid.GridView;
import grid.TileGrid;
import turn.Turn;

public class TileGridSimulation extends Simulation {
    public static class Builder extends Simulation.Builder {
        private TileGrid tileGrid;
        private Turn turnSpec;

        public Builder(TileGrid tileGrid, Turn turnSpec) {
            super();
            this.tileGrid = tileGrid;
            this.turnSpec = turnSpec;
        }

        @Override
        public Simulation build() {
            return new TileGridSimulation(this.rngSeed, this.powerLossRate, this.tileGrid, this.turnSpec);
        }
    }

    private TileGrid tileGrid;
    
    private GridView gridView;
    private Turn turn;

    protected TileGridSimulation(long rngSeed, double powerLossRate, TileGrid tileGrid, Turn turnSpec) {
        super(rngSeed, powerLossRate);
        this.tileGrid = tileGrid;
        this.gridView = new DefaultGridView(this.tileGrid);
        this.turn = turnSpec;
    }
}
