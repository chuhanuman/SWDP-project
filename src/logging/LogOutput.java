package logging;

/**
 * Interface for outputting log messages.
 */
public interface LogOutput {
    /**
     * Write a log message
     * @param message the message to write
     */
    void write(String message);
}
