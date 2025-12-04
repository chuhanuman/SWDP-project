package simulation;


import java.io.PrintStream;

import grid.ConstGrid;
import grid.GridView;
import grid.TileGrid;
import turn.TurnManager;

/**
 * Represents a simulation centralized around a grid of tiles.
 * Role: n/a
 */
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
            return new TileGridSimulation(this.rngSeed, this.tileGrid, this.turnSpec);
        }
    }

    private TileGrid tileGrid;
    
    private GridView gridView;
    private TurnManager turnManager;

    protected TileGridSimulation(long rngSeed, TileGrid tileGrid, TurnManager turnSpec) {
        super(rngSeed);
        this.tileGrid = tileGrid;
        this.gridView = new ConstGrid(this.tileGrid);
        this.turnManager = turnSpec;
    }

    @Override
    public void print(PrintStream out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'print'");
    }

    @Override
    public void run(int totalTurns) {
        for (int i = 0; i < totalTurns; i++ ) {
            turnManager.executeTurn(gridView, tileGrid);
            turn++;
        }
    }
}
