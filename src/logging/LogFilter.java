package logging;

/**
 * Interface for filtering log events.
 * Role(s): This is the abstraction for the log filter strategy, contributing to the Strategy pattern.
 */
public interface LogFilter {
    /**
     * Determine if an event should be logged
     * @param event the event to check
     * @return true if the event should be logged
     */
    boolean shouldLog(LogEvent event);

    /**
     * Add a child filter
     * Default implementation throws an exception
     * @param filter the filter to add
     */
    default void addFilter(LogFilter filter) {
        // Leaf nodes don't support adding children
        throw new UnsupportedOperationException(
            "This filter does not support adding child filters. "
        );
    }

    /**
     * Remove a child filter
     * Default implementation does nothing
     * @param filter the filter to remove
     * @return true if the filter was removed
     */
    default boolean removeFilter(LogFilter filter) {
        // Leaf nodes don't support removing children
        return false;
    }
}
