package logging.events;

import logging.LogEvent;
import logging.SimulationLogger.LogLevel;

/**
 * Event logged when a tile is decorated
 */
public class DecorationEvent implements LogEvent {
    private final String position;
    private final String decoratorName;

    public DecorationEvent(String position, String decoratorName) {
        this.position = position;
        this.decoratorName = decoratorName;
    }

    @Override
    public LogLevel getLevel() {
        return LogLevel.DEBUG;
    }

    @Override
    public String format() {
        return String.format("Decoration: Tile at %s decorated with %s", position, decoratorName);
    }
}
