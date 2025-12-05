package logging.outputs;

import java.util.ArrayList;
import java.util.List;
import logging.LogOutput;

/**
 * Collects log messages in memory for later retrieval
 */
public class CollectionOutput implements LogOutput {
    private final List<String> messages;

    public CollectionOutput() {
        this.messages = new ArrayList<>();
    }

    @Override
    public void write(String message) {
        messages.add(message);
    }

    /**
     * Get all collected messages
     * @return a copy of the message list
     */
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * Clear all collected messages
     */
    public void clear() {
        messages.clear();
    }
}
