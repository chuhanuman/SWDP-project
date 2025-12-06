package logging.events;

import java.util.UUID;
import logging.LogEvent;
import logging.SimulationLogger.LogLevel;

/**
 * Event logged when a tile's power changes
 * Role(s): A concrete implementation for the Template Method pattern for LogEvent
 */
public class PowerChangeEvent implements LogEvent {
    private final UUID tileId;
    private final double oldPower;
    private final double newPower;
    private final String changeType;

    public PowerChangeEvent(UUID tileId, double oldPower, double newPower, String changeType) {
        this.tileId = tileId;
        this.oldPower = oldPower;
        this.newPower = newPower;
        this.changeType = changeType;
    }

    @Override
    public LogLevel getLevel() {
        return LogLevel.DEBUG;
    }

    @Override
    public String format() {
        return String.format("Power Change (%s): Tile %s power changed from %.2f to %.2f",
            changeType,
            tileId.toString().substring(0, 8),
            oldPower,
            newPower);
    }
}
