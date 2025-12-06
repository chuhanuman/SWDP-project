package logging.outputs;

import java.util.ArrayList;
import java.util.List;
import logging.LogOutput;

/**
 * Collects log messages in memory for later retrieval
 * Role(s): This is a concrete strategy implementing the abstract LogOutput strategy, part of the Strategy Pattern.
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
    @Override
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * Clear all collected messages
     */
    @Override
    public void clear() {
        messages.clear();
    }
}
