package logging.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import logging.LogEvent;
import logging.LogFilter;

/**
 * Combines multiple filters
 */
public class CompositeFilter implements LogFilter {
    private final List<LogFilter> filters;

    public CompositeFilter(LogFilter... filters) {
        this.filters = new ArrayList<>(Arrays.asList(filters));
    }

    public void addFilter(LogFilter filter) {
        filters.add(filter);
    }

    @Override
    public boolean shouldLog(LogEvent event) {
        for (LogFilter filter : filters) {
            if (!filter.shouldLog(event)) {
                return false;
            }
        }
        return true;
    }
}
