package logging.outputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import logging.LogOutput;

/**
 * Combines multiple LogOutput implementations to write to multiple destinations.
 * Implements the Composite pattern for LogOutput.
 */
public class CompositeOutput implements LogOutput {
    private final List<LogOutput> outputs;

    public CompositeOutput(LogOutput... outputs) {
        this.outputs = new ArrayList<>(Arrays.asList(outputs));
    }

    @Override
    public void addOutput(LogOutput output) {
        if (output != null && !outputs.contains(output)) {
            outputs.add(output);
        }
    }

    @Override
    public boolean removeOutput(LogOutput output) {
        return outputs.remove(output);
    }

    /**
     * Get all child outputs
     * @return list of child outputs
     */
    public List<LogOutput> getOutputs() {
        return new ArrayList<>(outputs);
    }

    @Override
    public void write(String message) {
        for (LogOutput output : outputs) {
            output.write(message);
        }
    }

    @Override
    public List<String> getMessages() {
        // Collect messages from all child outputs
        List<String> allMessages = new ArrayList<>();
        for (LogOutput output : outputs) {
            allMessages.addAll(output.getMessages());
        }
        return allMessages;
    }

    @Override
    public void clear() {
        // Clear all child outputs
        for (LogOutput output : outputs) {
            output.clear();
        }
    }
}
