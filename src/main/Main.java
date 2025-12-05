package main;

import java.io.IOException;

import io.FileIO;
import simulation.TileGridSimulation;

public class Main {
	public static void main(String[] args) {
		// Default config file path
        String configPath = "configs/example_config.json";
        
        if (args.length > 0) {
            configPath = args[0];
        }
        
        try {
            System.out.println(" ------ Alien Invasion Simulation System ------ ");
            System.out.println();
            
            // Step 1: Load configuration
            System.out.println("Loading configuration from: " + configPath);
            FileIO.SimulationConfig config = FileIO.readConfig(configPath);
            System.out.println("Configuration loaded successfully!");
            System.out.println(" - Grid size: " + config.width + "x" + config.height);
            System.out.println(" - Max duration: " + config.maxDuration + " turns");
            System.out.println(" - Number of alien forces: " + config.alienForces.size());
            
            if (config.seed != null) {
                System.out.println(" - Random seed: " + config.seed);
            }
            
            if (config.csvMapFile != null && !config.csvMapFile.isEmpty()) {
                System.out.println(" - Map file: " + config.csvMapFile);
            }
            
            if (config.logging != null) {
                System.out.println(" - Log level: " + config.logging.level);
                if (config.logging.outputFile != null) {
                    System.out.println(" - Output file: " + config.logging.outputFile);
                }
            }
            System.out.println();
            
            // Step 2: Build simulation from config
            System.out.println("Building simulation from configuration...");
            TileGridSimulation simulation = FileIO.buildSimulation(configPath);
            System.out.println("Simulation built successfully!");
            System.out.println();
            
            // Step 3: Run simulation
            System.out.println("Running simulation for " + config.maxDuration + " turns...");
            System.out.println("────────────────");
            simulation.run(config.maxDuration);
            System.out.println("────────────────");
            System.out.println("Simulation completed!");
            System.out.println();
            
            // Step 4: Print final state - print() will also write to the JSON file specified in config
            System.out.println("Final Simulation Results:");
            System.out.println("════════════════");
            simulation.print(System.out);
            System.out.println("════════════════");
            System.out.println();
            
            System.out.println("Simulation completed successfully!");
            
        } catch (IOException e) {
            System.err.println("Error reading/writing files:");
            System.err.println("   " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid configuration:");
            System.err.println("   " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
            
        } catch (Exception e) {
            System.err.println("Unexpected error occurred:");
            System.err.println("   " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
