package logging;

/**
 * Logger interface for recording simulation events.
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
