package logging;

import logging.SimulationLogger.LogLevel;

/**
 * Represents a loggable event in the simulation.
 */
public interface LogEvent {
    /**
     * Get the log level for this event
     * @return the log level
     */
    LogLevel getLevel();

    /**
     * Format this event as a string
     * @return formatted message
     */
    String format();
}
