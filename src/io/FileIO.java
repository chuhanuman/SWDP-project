package io;

// requires Gson library - add to your project dependencies
// Maven: <dependency><groupId>com.google.gson</groupId><artifactId>gson</artifactId><version>2.10.1</version></dependency>
// Gradle: implementation 'com.google.gson:gson:2.10.1'

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import grid.TileGrid;
import grid.GridPos;
import tile.ViewableTile;

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
    reads simulation configuration from a JSON file
     
    @param configPath path to the JSON configuration file
    @return SimulationConfig object containing all configuration parameters
    @throws IOException if file reading fails
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
    writes the final simulation state to a JSON file.
     
    @param grid the tile grid after simulation
    @param totalSteps total number of steps executed
    @param outputPath path to write the output JSON file
    @throws IOException if file writing fails
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
        output.gridWidth = grid.getWidth();
        output.gridHeight = grid.getHeight();
        
        // collect tile states
        List<TileState> tileStates = new ArrayList<>();
        int occupiedCount = 0;
        double totalResources = 0;
        
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
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