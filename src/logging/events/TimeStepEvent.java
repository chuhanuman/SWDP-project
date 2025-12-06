package logging.events;

import logging.LogEvent;
import logging.SimulationLogger.LogLevel;

/**
 * Event logged at the start of each time step
 * Role(s): A concrete implementation for the Template Method pattern for LogEvent
 */
public class TimeStepEvent implements LogEvent {
    private final int turn;

    public TimeStepEvent(int turn) {
        this.turn = turn;
    }

    @Override
    public LogLevel getLevel() {
        return LogLevel.DEFAULT;
    }

    @Override
    public String format() {
        return String.format("--- Turn %d ---", turn);
    }
}
