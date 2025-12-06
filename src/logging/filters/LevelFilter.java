package logging.filters;

import logging.LogEvent;
import logging.LogFilter;
import logging.SimulationLogger.LogLevel;

/**
 * Filters log events based on log level.
 * Role(s): This is a concrete strategy implementing the abstract LogFilter strategy, part of the Strategy Pattern.
 */
public class LevelFilter implements LogFilter {
    private LogLevel currentLevel;

    public LevelFilter(LogLevel level) {
        this.currentLevel = level;
    }

    public void setLevel(LogLevel level) {
        this.currentLevel = level;
    }

    public LogLevel getLevel() {
        return currentLevel;
    }

    @Override
    public boolean shouldLog(LogEvent event) {
        if (currentLevel == LogLevel.SILENT) {
            return false;
        }
        if (currentLevel == LogLevel.DEBUG) {
            return true;
        }
        // DEFAULT level: only log DEFAULT messages, not DEBUG
        return event.getLevel() == LogLevel.DEFAULT;
    }
}
