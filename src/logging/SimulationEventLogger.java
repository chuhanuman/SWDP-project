package logging;

import java.util.HashMap;
import java.util.Map;

/**
 * Logger implementation for simulation events.
 */
public class SimulationEventLogger implements Logger {
    private final LogFilter filter;
    private final LogOutput output;
    private final Map<String, Object> finalState;

    /**
     * Create a logger with custom filter and output
     * @param filter the filter to use
     * @param output the output destination
     */
    public SimulationEventLogger(LogFilter filter, LogOutput output) {
        this.filter = filter;
        this.output = output;
        this.finalState = new HashMap<>();
    }

    @Override
    public void log(LogEvent event) {
        if (filter.shouldLog(event)) {
            output.write(event.format());
        }
    }

    @Override
    public void reset() {
        finalState.clear();
    }

    /**
     * Store final state information
     * @param key the state key
     * @param value the state value
     */
    public void addFinalStateInfo(String key, Object value) {
        finalState.put(key, value);
    }

    /**
     * Get final state information
     * @return a copy of the final state map
     */
    public Map<String, Object> getFinalState() {
        return new HashMap<>(finalState);
    }

    /**
     * Get the filter being used
     * @return the log filter
     */
    public LogFilter getFilter() {
        return filter;
    }
}
