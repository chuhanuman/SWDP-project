package logging;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logging.filters.LevelFilter;
import logging.outputs.CollectionOutput;
import logging.outputs.CompositeOutput;
import logging.outputs.PrintStreamOutput;

/**
 * Singleton logger for simulation events.
 * Supports different logging levels and stores final state information.
 */
public class SimulationLogger {

    public enum LogLevel {
        DEBUG,   // Logs every change
        DEFAULT, // Logs spreads only
        SILENT   // No logging during simulation
    }

    private static SimulationLogger instance;
    private Logger logger;
    private LogFilter filter;
    private LogOutput output;
    private final CollectionOutput collectionOutput;
    private final Map<String, Object> finalState;
    private PrintStream currentOutputStream;
    private String outputFilePath;

    private SimulationLogger() {
        this.filter = new LevelFilter(LogLevel.DEFAULT);
        this.collectionOutput = new CollectionOutput();
        this.currentOutputStream = System.out;
        this.finalState = new HashMap<>();
        this.outputFilePath = null;

        rebuildLogger();
    }

    private void rebuildLogger() {
        this.output = new CompositeOutput(
            new PrintStreamOutput(currentOutputStream),
            collectionOutput
        );
        this.logger = new SimulationEventLogger(filter, output);
    }

    /**
     * Get the singleton instance of SimulationLogger
     * @return the singleton instance
     */
    public static synchronized SimulationLogger getInstance() {
        if (instance == null) {
            instance = new SimulationLogger();
        }
        return instance;
    }

    /**
     * Set the logging level (only works if using LevelFilter)
     * @param level the log level to use
     */
    public void setLogLevel(LogLevel level) {
        if (filter instanceof LevelFilter) {
            ((LevelFilter) filter).setLevel(level);
        }
    }

    /**
     * Get the current logging level (only works if using LevelFilter)
     * @return the current log level, or null if not using LevelFilter
     */
    public LogLevel getLogLevel() {
        if (filter instanceof LevelFilter) {
            return ((LevelFilter) filter).getLevel();
        }
        return null;
    }

    /**
     * Replace the current filter with a new one
     * @param newFilter the filter to use
     */
    public void setFilter(LogFilter newFilter) {
        this.filter = newFilter;
        rebuildLogger();
    }

    /**
     * Get the current filter
     * @return the current filter
     */
    public LogFilter getFilter() {
        return this.filter;
    }

    /**
     * Add an output to the current output
     * @param newOutput the output to add
     */
    public void addOutput(LogOutput newOutput) {
        if (output != null) {
            output.addOutput(newOutput);
        }
    }

    /**
     * Replace all outputs with new ones (rebuilds the logger)
     * Note: This will keep the collectionOutput for getSimulationLog() to work
     * @param outputs the outputs to use
     */
    public void setOutputs(LogOutput... outputs) {
        this.output = new CompositeOutput(outputs);
        // Always add collection output to maintain getSimulationLog() functionality
        this.output.addOutput(collectionOutput);
        rebuildLogger();
    }

    /**
     * Get the current output
     * @return the current output
     */
    public LogOutput getOutput() {
        return this.output;
    }

    /**
     * Set the output stream for logging
     * @param stream the PrintStream to use for output
     */
    public void setOutputStream(PrintStream stream) {
        this.currentOutputStream = stream;
        rebuildLogger();
    }

    /**
     * Set the output file path for logging
     * @param path the file path to use for output
     */
    public void setOutputFilePath(String path) {
        this.outputFilePath = path;
    }

    /**
     * Get the output file path for logging
     * @return the file path used for output
     */
    public String getOutputFilePath() {
        return this.outputFilePath;
    }

    /**
     * Reset the logger state (useful between simulation runs)
     */
    public void reset() {
        logger.reset();
        collectionOutput.clear();
        finalState.clear();
    }

    /**
     * Log an event
     * @param event the event to log
     */
    public void log(LogEvent event) {
        logger.log(event);
    }

    /**
     * Store final state information
     * @param key the state key
     * @param value the state value
     */
    public void addFinalStateInfo(String key, Object value) {
        finalState.put(key, value);
    }

    /**
     * Get the complete simulation log
     * @return list of all logged messages
     */
    public List<String> getSimulationLog() {
        return collectionOutput.getMessages();
    }
}
