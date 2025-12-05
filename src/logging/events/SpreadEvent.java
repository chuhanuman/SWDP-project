package logging.events;

import java.util.UUID;
import logging.LogEvent;
import logging.SimulationLogger.LogLevel;

/**
 * Event logged when a spreader spreads to a new tile
 */
public class SpreadEvent implements LogEvent {
    private final String spreaderName;
    private final UUID fromTileId;
    private final UUID toTileId;
    private final double power;

    public SpreadEvent(String spreaderName, UUID fromTileId, UUID toTileId, double power) {
        this.spreaderName = spreaderName;
        this.fromTileId = fromTileId;
        this.toTileId = toTileId;
        this.power = power;
    }

    @Override
    public LogLevel getLevel() {
        return LogLevel.DEFAULT;
    }

    @Override
    public String format() {
        return String.format("Spread: %s from tile %s to tile %s with power %.2f",
            spreaderName,
            getTileIdentifier(fromTileId),
            getTileIdentifier(toTileId),
            power);
    }

    private String getTileIdentifier(UUID tileId) {
        return tileId.toString().substring(0, 8);
    }
}
