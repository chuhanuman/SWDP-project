package logging;

/**
 * Interface for outputting log messages.
 * Supports the Composite pattern for combining multiple outputs.
 */
public interface LogOutput {
    /**
     * Write a log message
     * @param message the message to write
     */
    void write(String message);

    /**
     * Add a child output
     * Default implementation does nothing
     * @param output the output to add
     */
    default void addOutput(LogOutput output) {
        // Leaf nodes don't support adding children
        throw new UnsupportedOperationException(
            "This output does not support adding child outputs. "
        );
    }

    /**
     * Remove a child output
     * Default implementation does nothing
     * @param output the output to remove
     * @return true if the output was removed
     */
    default boolean removeOutput(LogOutput output) {
        // Leaf nodes don't support removing children
        return false;
    }
}
