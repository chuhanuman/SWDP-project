package logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for outputting log messages.
 * Role(s): Supports the Composite pattern for combining multiple outputs. This is also a part of the Strategy pattern also.
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

    /**
     * Get collected messages (for outputs that collect messages)
     * Default implementation returns empty list
     * @return list of collected messages
     */
    default List<String> getMessages() {
        // Leaf nodes that don't collect return empty list
        return new ArrayList<>();
    }

    /**
     * Clear any collected state (for outputs that maintain state)
     * Default implementation does nothing
     */
    default void clear() {
        // Leaf nodes with no state don't need to clear
    }
}
