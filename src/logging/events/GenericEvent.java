package logging.events;

import logging.LogEvent;
import logging.SimulationLogger.LogLevel;

/**
 * Generic event for custom messages
 */
public class GenericEvent implements LogEvent {
    private final String message;
    private final LogLevel level;

    public GenericEvent(String message, LogLevel level) {
        this.message = message;
        this.level = level;
    }

    public GenericEvent(String message) {
        this(message, LogLevel.DEBUG);
    }

    @Override
    public LogLevel getLevel() {
        return level;
    }

    @Override
    public String format() {
        return message;
    }
}
