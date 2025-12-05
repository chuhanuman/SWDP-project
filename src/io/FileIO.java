package io;

// requires Gson library - added to project dependencies
// Maven: <dependency><groupId>com.google.gson</groupId><artifactId>gson</artifactId><version>2.10.1</version></dependency>
// Gradle: implementation 'com.google.gson:gson:2.10.1'

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import grid.DefaultTileGrid;
import grid.TileGrid;
import grid.GridPos;
import tile.DefaultTile;
import tile.Tile;
import tile.ViewableTile;
import tile.EfficientExtractionDecorator;
import tile.ExtraDefensesDecorator;
import tile.StealthyDecorator;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.spreading_strategy.*;
import spreader.extraction_strategy.*;
import simulation.TileGridSimulation;
import turn.TurnManager;
import turn.extract.DefaultExtractStage;
import turn.move.DefaultMoveStage;
import turn.decorate.DecorateStage;
import turn.deplete.GrowStage;
import logging.SimulationLogger;

import java.io.*;
import java.util.*;

/**
handles input/output operations for the alien simulation
reads JSON configuration files, reads CSV map files, and writes simulation results
 
file I/O only
 */
public class FileIO {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * reads simulation configuration from a JSON file
     *  
     * @param configPath path to the JSON configuration file
     * @return SimulationConfig object containing all configuration parameters
     * @throws IOException if file reading fails
     */
    public static SimulationConfig readConfig(String configPath) throws IOException {
        try (Reader reader = new FileReader(configPath)) {
            return gson.fromJson(reader, SimulationConfig.class);
        }
    }

    /**
    reads a CSV map file and returns a 2D array of tile type identifiers
     
    @param csvPath path to CSV file
    @return 2D array where each cell contains a tile type identifier
    @throws IOException if file cannot be read
     */
    public static String[][] readCSVMap(String csvPath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Trim whitespace from each value
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }
                rows.add(values);
            }
        }
        
        return rows.toArray(new String[0][]);
    }

    /**
     * writes the final simulation state to a JSON file.
     
     * @param grid the tile grid after simulation
     * @param totalSteps total number of steps executed
     * @param outputPath path to write the output JSON file
     * @throws IOException if file writing fails
     */
    public static void writeFinalState(TileGrid grid, int totalSteps, String outputPath) 
            throws IOException {
        SimulationOutput output = buildOutput(grid, totalSteps);
        
        try (Writer writer = new FileWriter(outputPath)) {
            gson.toJson(output, writer);
        }
    }

    // builds the output object from the final simulation state - collects data from the grid to write to JSON
    private static SimulationOutput buildOutput(TileGrid grid, int totalSteps) {
        SimulationOutput output = new SimulationOutput();
        output.totalSteps = totalSteps;
        output.gridWidth = grid.getNumCols();
        output.gridHeight = grid.getNumRows();
        
        // collect tile states
        List<TileState> tileStates = new ArrayList<>();
        int occupiedCount = 0;
        double totalResources = 0;
        
        for (int row = 0; row < grid.getNumRows(); row++) {
            for (int col = 0; col < grid.getNumCols(); col++) {
                GridPos pos = new GridPos(col, row);
                ViewableTile tile = grid.getTile(pos);
                
                TileState state = new TileState();
                state.col = col;
                state.row = row;
                state.difficulty = tile.getDifficulty();
                state.resources = tile.getResources();
                state.occupierPower = tile.getOccupierPower();
                state.occupied = (tile.getOccupier() != null);
                
                if (state.occupied) {
                    occupiedCount++;
                }
                totalResources += tile.getResources();
                
                tileStates.add(state);
            }
        }
        
        output.tiles = tileStates;
        output.totalOccupiedTiles = occupiedCount;
        output.totalRemainingResources = totalResources;
        
        return output;
    }

    /**
     * Builds a complete simulation from a configuration file.
    
     * @param configPath Path to JSON configuration file
     * @return Fully constructed TileGridSimulation ready to run
     * @throws IOException If config file cannot be read
     * @throws IllegalArgumentException If config contains invalid data
     */
     
    public static TileGridSimulation buildSimulation(String configPath) throws IOException {
        SimulationConfig config = readConfig(configPath);
        
        // configure logging before building simulation
        configureLogger(config);
        
        // build tile grid from config
        TileGrid grid = buildTileGrid(config);
        
        // build turn manager from config
        TurnManager manager = buildTurnManager(config);
        
        // build simulation
        TileGridSimulation.Builder simBuilder = new TileGridSimulation.Builder(grid, manager);
        
        // set seed if provided
        if (config.seed != null) {
            simBuilder.setRNGSeed(config.seed);
        }
        
        return (TileGridSimulation) simBuilder.build();
    }

    // Configure SimulationLogger based on config settings
    private static void configureLogger(SimulationConfig config) {
        if (config.logging == null) {
            return;
        }
        
        SimulationLogger logger = SimulationLogger.getInstance();
        
        // Set log level
        if (config.logging.level != null) {
            try {
                SimulationLogger.LogLevel level = 
                    SimulationLogger.LogLevel.valueOf(config.logging.level.toUpperCase());
                logger.setLogLevel(level);
            } catch (IllegalArgumentException e) {
                System.err.println("Warning: Unknown log level '" + config.logging.level + 
                                   "', using DEFAULT");
            }
        }
        
        // Set output file path - storing for later use in print()
        if (config.logging.outputFile != null) {
            logger.setOutputFilePath(config.logging.outputFile);
        }
    }

    /**
     * Builds the TileGrid by orchestrating the DefaultTileGrid.Builder.
     * Reads CSV map if provided and places tiles accordingly.
     */
    private static TileGrid buildTileGrid(SimulationConfig config) throws IOException {
        DefaultTileGrid.Builder gridBuilder = new DefaultTileGrid.Builder(
            config.width, 
            config.height
        );
        
        // Set default tile
        Tile defaultTile = new DefaultTile(
            config.defaultTile.difficulty, 
            config.defaultTile.resources
        );
        gridBuilder.setDefaultTile(defaultTile);
        
        // Place specific tiles from CSV map if provided
        if (config.csvMapFile != null && !config.csvMapFile.isEmpty()) {
            String[][] tileMap = readCSVMap(config.csvMapFile);
            
            if (config.tileTypes != null) {
                for (int row = 0; row < tileMap.length && row < config.height; row++) {
                    for (int col = 0; col < tileMap[row].length && col < config.width; col++) {
                        String tileType = tileMap[row][col];
                        
                        // Skip if it's DEFAULT or not defined
                        if (!"DEFAULT".equals(tileType) && config.tileTypes.containsKey(tileType)) {
                            TileTypeConfig tileConfig = config.tileTypes.get(tileType);
                            Tile tile = new DefaultTile(
                                tileConfig.difficulty,
                                tileConfig.resources
                            );
                            gridBuilder.setTile(tile, new GridPos(col, row));
                        }
                    }
                }
            }
        }
        
        // Place alien forces at their starting positions
        if (config.alienForces != null) {
            for (AlienForceConfig alienConfig : config.alienForces) {
                Spreader spreader = createSpreader(alienConfig);
                
                // Place spreader at each starting position
                for (StartingPosition pos : alienConfig.startingPositions) {
                    Tile tile = new DefaultTile(
                        config.defaultTile.difficulty,
                        config.defaultTile.resources,
                        spreader,
                        pos.initialOccupierPower
                    );
                    gridBuilder.setTile(tile, new GridPos(pos.col, pos.row));
                }
            }
        }
        
        return gridBuilder.build();
    }

    /**
     * Builds the TurnManager by orchestrating the TurnManager.Builder
     * Sets up sequence of stages for each turn
     */
    private static TurnManager buildTurnManager(SimulationConfig config) {
        TurnManager.Builder builder = new TurnManager.Builder();
        
        // Adding standard stages in order
        builder.nextStage(new DefaultExtractStage());
        builder.nextStage(new DefaultMoveStage());
        
        // Add decorator stage with probability from config
        double decoratorProbability = (config.decoratorProbability != null) 
            ? config.decoratorProbability : 0.01;
        builder.nextStage(new DecorateStage(
            decoratorProbability,
            List.of(
                StealthyDecorator.getApplier(),
                ExtraDefensesDecorator.getApplier(),
                EfficientExtractionDecorator.getApplier()
            )
        ));
        
        // Add growth stage
        double growthRate = (config.growthRate != null) ? config.growthRate : 0.9;
        builder.nextStage(new GrowStage(growthRate));
        
        return builder.build();
    }

    // Factory method to create a Spreader from config - Delegates to strategy factory methods created by other team members
    private static Spreader createSpreader(AlienForceConfig config) {
        SpreadingStrategy spreadStrategy = createSpreadingStrategy(
            config.spreadingStrategy, 
            config.strategyParameter
        );
        
        ExtractionStrategy extractStrategy = createExtractionStrategy(
            config.extractionStrategy,
            config.strategyParameter
        );
        
        return new DefaultSpreader(spreadStrategy, extractStrategy);
    }

    /**
     * Factory method for spreading strategies.
     * Maps string names to actual strategy implementations.
     */
    private static SpreadingStrategy createSpreadingStrategy(String type, double parameter) {
        if (type == null) {
            type = "GREEDY"; // default
        }
        
        switch (type.toUpperCase()) {
            case "GREEDY":
                return new GreedySpreading((int) parameter);
            case "COWARD":
            case "LEAST_RESISTANCE":
                return new CowardSpreading();
            case "RANDOM":
                return new RandomSpreading();
            default:
                throw new IllegalArgumentException("Unknown spreading strategy: " + type);
        }
    }

    /**
     * Factory method for extraction strategies.
     * Maps string names to actual strategy implementations.
     */
    private static ExtractionStrategy createExtractionStrategy(String type, double parameter) {
        if (type == null) {
            type = "INSTANT";   // default
        }
        
        switch (type.toUpperCase()) {
            case "INSTANT":
                return new InstantExtraction(parameter);
            case "LEARNING":
                return new LearningExtraction(parameter, 1.1);
            case "SLOW":
                return new SlowExtraction(parameter, 100.0);
            default:
                throw new IllegalArgumentException("Unknown extraction strategy: " + type);
        }
    }

    // Config Classes (JSON input)

    // config class for simulation input - maps directly to JSON structure
    public static class SimulationConfig {
        public int width;
        public int height;
        public int maxDuration;
        public Long seed;
        public String csvMapFile;
        public TileTypeConfig defaultTile;
        public Map<String, TileTypeConfig> tileTypes;
        public List<AlienForceConfig> alienForces;
        public Double decoratorProbability;
        public Double growthRate;
        public LoggingConfig logging;
    }

    // config for a tile type
    public static class TileTypeConfig {
        public double difficulty;
        public double resources;
    }

    // config for alien forces - each alien force can occupy multiple tiles at start
    public static class AlienForceConfig {
        public String type;
        public String spreadingStrategy;
        public String extractionStrategy;
        public double strategyParameter;
        public List<StartingPosition> startingPositions;
    }
    
    // represents a starting position for an alien force
    public static class StartingPosition {
        public int col;
        public int row;
        public double initialOccupierPower;
    }

    // config for logging behavior
    public static class LoggingConfig {
        public String level; // "DEBUG" or "DEFAULT"
        public String outputFile;
    }

    // Output Classes (JSON output)

    // output class for final simulation state
    public static class SimulationOutput {
        public int totalSteps;
        public int gridWidth;
        public int gridHeight;
        public int totalOccupiedTiles;
        public double totalRemainingResources;
        public List<TileState> tiles;
    }

    // state of a single tile in the output
    public static class TileState {
        public int col;
        public int row;
        public double difficulty;
        public double resources;
        public double occupierPower;
        public boolean occupied;
    }
}