package logging.events;

import java.util.UUID;
import logging.LogEvent;
import logging.SimulationLogger.LogLevel;

/**
 * Event logged when resources are extracted from a tile
 * Role(s): A concrete implementation for the Template Method pattern for LogEvent
 */
public class ExtractionEvent implements LogEvent {
    private final UUID tileId;
    private final double amount;
    private final double efficiency;

    public ExtractionEvent(UUID tileId, double amount, double efficiency) {
        this.tileId = tileId;
        this.amount = amount;
        this.efficiency = efficiency;
    }

    @Override
    public LogLevel getLevel() {
        return LogLevel.DEBUG;
    }

    @Override
    public String format() {
        return String.format("Extraction: Tile %s extracted %.2f resources with %.2f efficiency",
            tileId.toString().substring(0, 8),
            amount,
            efficiency);
    }
}
