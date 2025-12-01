package logging.outputs;

import java.io.PrintStream;
import logging.LogOutput;

/**
 * Outputs log messages to a PrintStream (e.g., System.out)
 */
public class PrintStreamOutput implements LogOutput {
    private final PrintStream stream;

    public PrintStreamOutput(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void write(String message) {
        stream.println(message);
    }
}
