package logging;

import logging.SimulationLogger.LogLevel;

/**
 * Represents a loggable event in the simulation.
 * Role(s): This is the base interface for the template method in logging different events, this contributes to the Template Method pattern.
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
