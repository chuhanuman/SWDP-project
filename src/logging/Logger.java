package logging;

/**
 * Logger interface for recording simulation events.
 * Role(s): This is the abstraction for the logger itself, in case there is a need for any other loggers to replace the SimulationLogger, this could contribute to the Strategy pattern
 */
public interface Logger {
    /**
     * Log an event
     * @param event the event to log
     */
    void log(LogEvent event);

    /**
     * Reset the logger state
     */
    void reset();
}
