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
    private final LevelFilter filter;
    private final CollectionOutput collectionOutput;
    private final Map<String, Object> finalState;
    private PrintStream currentOutputStream;

    private SimulationLogger() {
        this.filter = new LevelFilter(LogLevel.DEFAULT);
        this.collectionOutput = new CollectionOutput();
        this.currentOutputStream = System.out;
        this.finalState = new HashMap<>();

        rebuildLogger();
    }

    private void rebuildLogger() {
        CompositeOutput compositeOutput = new CompositeOutput(
            new PrintStreamOutput(currentOutputStream),
            collectionOutput
        );
        this.logger = new SimulationEventLogger(filter, compositeOutput);
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
     * Set the logging level
     * @param level the log level to use
     */
    public void setLogLevel(LogLevel level) {
        filter.setLevel(level);
    }

    /**
     * Get the current logging level
     * @return the current log level
     */
    public LogLevel getLogLevel() {
        return filter.getLevel();
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
