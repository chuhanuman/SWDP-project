package simulation;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import grid.ConstGrid;
import grid.GridView;
import grid.TileGrid;
import logging.SimulationLogger;
import spreader.Spreader;
import tile.ViewableTile;
import turn.TurnManager;

/**
 * Represents a simulation centralized around a grid of tiles.
 * Role(s): client role in iterator pattern, inner class builder plays the concrete builder role in the builder pattern
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
        SimulationLogger logger = SimulationLogger.getInstance();

        out.println("--- Final Simulation State ---");
        out.println("Total Turns: " + turn);

        // Collect and print tile information
        Iterable<ViewableTile> tiles = gridView.getAllTiles();

        Map<Spreader, Integer> spreaderCounts = new HashMap<>();
        int tileCount = 0;
        int occupiedTiles = 0;
        double totalResources = 0;
        double totalPower = 0;
        List<Map<String, Object>> tileDetails = new ArrayList<>();

        for (ViewableTile tile : tiles) {
            tileCount++;
            totalResources += tile.getResources();
            if (tile.getOccupier() != null) {
                occupiedTiles++;
                totalPower += tile.getOccupierPower();
                spreaderCounts.merge(tile.getOccupier(), 1, Integer::sum);
            }

            // Collect detailed tile information
            Map<String, Object> tileInfo = new HashMap<>();
            tileInfo.put("id", tile.getID().toString());
            tileInfo.put("resources", tile.getResources());
            tileInfo.put("difficulty", tile.getDifficulty());
            tileInfo.put("occupierPower", tile.getOccupierPower());
            tileInfo.put("occupier", tile.getOccupier() != null ?
                tile.getOccupier().getName() : null);
            tileDetails.add(tileInfo);
        }

        out.println("Total Tiles: " + tileCount);
        out.println("Occupied Tiles: " + occupiedTiles);
        out.println("Total Resources: " + String.format("%.2f", totalResources));
        out.println("Total Power: " + String.format("%.2f", totalPower));
        out.println("\nSpreader Distribution:");

        for (Map.Entry<Spreader, Integer> entry : spreaderCounts.entrySet()) {
            out.println("  " + entry.getKey().getName() + ": " + entry.getValue() + " tiles");
        }

        logger.addFinalStateInfo("totalTurns", turn);
        logger.addFinalStateInfo("totalTiles", tileCount);
        logger.addFinalStateInfo("occupiedTiles", occupiedTiles);
        logger.addFinalStateInfo("totalResources", totalResources);
        logger.addFinalStateInfo("totalPower", totalPower);

        Map<String, Integer> spreaderDistribution = new HashMap<>();
        for (Map.Entry<Spreader, Integer> entry : spreaderCounts.entrySet()) {
            spreaderDistribution.put(entry.getKey().getName(), entry.getValue());
        }
        logger.addFinalStateInfo("spreaderDistribution", spreaderDistribution);
        logger.addFinalStateInfo("tiles", tileDetails);

        // Write final state to JSON file
        String outputPath = logger.getOutputFilePath();
        if (outputPath != null && !outputPath.isEmpty()) {
            try {
                out.println("\nWriting final state to: " + outputPath);
                io.FileIO.writeFinalState(this.tileGrid, this.turn, outputPath);
                out.println("Final state written successfully!");
            } catch (java.io.IOException e) {
                out.println("Warning: Failed to write final state to file: " + e.getMessage());
                e.printStackTrace(out);
            }
        } else {
            out.println("\nNo output file specified in configuration - skipping file write.");
        }
    }

    @Override
    public void run(int totalTurns) {
        SimulationLogger logger = SimulationLogger.getInstance();
        for (int i = 0; i < totalTurns; i++ ) {
            logger.log(new logging.events.TimeStepEvent(turn));
            turnManager.executeTurn(gridView, tileGrid);
            turn++;
        }
    }
}
