package logging.events;

import java.util.UUID;
import logging.LogEvent;
import logging.SimulationLogger.LogLevel;

/**
 * Event logged when a tile is infected
 * Role(s): A concrete implementation for the Template Method pattern for LogEvent
 */
public class InfectionEvent implements LogEvent {
    private final UUID tileId;
    private final double power;
    private final String spreaderName;

    public InfectionEvent(UUID tileId, double power, String spreaderName) {
        this.tileId = tileId;
        this.power = power;
        this.spreaderName = spreaderName;
    }

    @Override
    public LogLevel getLevel() {
        return LogLevel.DEBUG;
    }

    @Override
    public String format() {
        return String.format("Infection: Tile %s infected by %s with power %.2f",
            tileId.toString().substring(0, 8),
            spreaderName,
            power);
    }
}
