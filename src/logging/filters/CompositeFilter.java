package logging.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import logging.LogEvent;
import logging.LogFilter;

/**
 * Combines multiple filters using AND logic (all filters must pass).
 * Implements the Composite pattern for LogFilter.
 */
public class CompositeFilter implements LogFilter {
    private final List<LogFilter> filters;

    public CompositeFilter(LogFilter... filters) {
        this.filters = new ArrayList<>(Arrays.asList(filters));
    }

    @Override
    public void addFilter(LogFilter filter) {
        if (filter != null && !filters.contains(filter)) {
            filters.add(filter);
        }
    }

    @Override
    public boolean removeFilter(LogFilter filter) {
        return filters.remove(filter);
    }

    /**
     * Get all child filters
     * @return list of child filters
     */
    public List<LogFilter> getFilters() {
        return new ArrayList<>(filters);
    }

    @Override
    public boolean shouldLog(LogEvent event) {
        // AND logic: all filters must pass
        for (LogFilter filter : filters) {
            if (!filter.shouldLog(event)) {
                return false;
            }
        }
        return true;
    }
}
