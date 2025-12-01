package logging;

/**
 * Interface for filtering log events.
 */
public interface LogFilter {
    /**
     * Determine if an event should be logged
     * @param event the event to check
     * @return true if the event should be logged
     */
    boolean shouldLog(LogEvent event);
}
