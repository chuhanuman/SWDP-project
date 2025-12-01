package logging.outputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import logging.LogOutput;

/**
 * Combines multiple LogOutput implementations to write to multiple destinations
 */
public class CompositeOutput implements LogOutput {
    private final List<LogOutput> outputs;

    public CompositeOutput(LogOutput... outputs) {
        this.outputs = new ArrayList<>(Arrays.asList(outputs));
    }

    public void addOutput(LogOutput output) {
        outputs.add(output);
    }

    @Override
    public void write(String message) {
        for (LogOutput output : outputs) {
            output.write(message);
        }
    }
}
