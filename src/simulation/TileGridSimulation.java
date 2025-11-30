package simulation;


import java.util.Collection;

import grid.ConstGrid;
import grid.GridView;
import grid.TileGrid;
import spreader.Spreader;
import turn.TurnManager;

public class TileGridSimulation extends Simulation {
    public static class Builder extends Simulation.Builder {
        private TileGrid tileGrid;
        private TurnManager turnSpec;

        public Builder(TileGrid tileGrid, TurnManager turnSpec) {
            super();
            this.tileGrid = tileGrid;
            this.turnSpec = turnSpec;
        }

        @Override
        public Simulation build() {
            return new TileGridSimulation(this.rngSeed, this.powerLossRate, this.tileGrid, this.turnSpec);
        }
    }
    public record View(GridView grid, Collection<Spreader> spreaders) {};

    private TileGrid tileGrid;
    
    private GridView gridView;
    private TurnManager turn;

    protected TileGridSimulation(long rngSeed, double powerLossRate, TileGrid tileGrid, TurnManager turnSpec) {
        super(rngSeed, powerLossRate);
        this.tileGrid = tileGrid;
        this.gridView = new ConstGrid(this.tileGrid);
        this.turn = turnSpec;
    }
}
