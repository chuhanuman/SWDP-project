package main;

import java.util.List;

import grid.DefaultTileGrid;
import grid.GridPos;
import grid.TileGrid;
import logging.SimulationLogger;
import logging.SimulationLogger.LogLevel;
import simulation.Simulation;
import simulation.TileGridSimulation;
import spreader.*;
import spreader.extraction_strategy.InstantExtraction;
import spreader.spreading_strategy.GreedySpreading;
import tile.DefaultTile;
import tile.EfficientExtractionDecorator;
import tile.ExtraDefensesDecorator;
import tile.StealthyDecorator;
import turn.TurnManager;
import turn.decorate.DecorateStage;
import turn.deplete.GrowStage;
import turn.extract.DefaultExtractStage;
import turn.move.DefaultMoveStage;

public class ExampleSimRun {
    public static void main(String[] args) {
        // EXAMPLE SIMULATION

        SimulationLogger.getInstance().setLogLevel(LogLevel.DEBUG);

        // spreader example, as many as you want can be there
        Spreader s1 = new DefaultSpreader(new GreedySpreading(1), new InstantExtraction(1));

        // example tile creation
        TileGrid tg = new DefaultTileGrid.Builder(10, 10)
                      .setTile(
                        new DefaultTile(0, 100, s1, 100), 
                        new GridPos(0, 0))
                      .setDefaultTile(new DefaultTile(10, 1000))
                      .build();

        TurnManager manager = new TurnManager.Builder()
                              .nextStage(new DefaultExtractStage())
                              .nextStage(new DefaultMoveStage())
                              .nextStage(new DecorateStage(
                            0.01, 
                                List.of(StealthyDecorator.getApplier(), 
                                ExtraDefensesDecorator.getApplier(),
                                EfficientExtractionDecorator.getApplier())))
                              .nextStage(new GrowStage(0.9))
                              .build();
        
        Simulation sim = new TileGridSimulation.Builder(tg, manager).build();
        sim.run(100);
        return;
    }
}
