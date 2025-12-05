package tests.logging;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.UUID;

import org.junit.jupiter.api.*;

import logging.SimulationLogger;
import logging.events.*;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.extraction_strategy.InstantExtraction;
import spreader.spreading_strategy.RandomSpreading;
import tile.DefaultTile;
import tile.ViewableTile;

public class SimulationLoggerTests {

    private ByteArrayOutputStream outputStream;

    // Setup by reseting the logger's stateful vars and also creating a new output stream
    @BeforeEach
    public void setUp() {
        SimulationLogger logger = SimulationLogger.getInstance();
        logger.reset();
        outputStream = new ByteArrayOutputStream();
        logger.setOutputStream(new PrintStream(outputStream));
    }

    // Teardown by resetting the output stream to sys.out
    @AfterEach
    public void tearDown() {
        SimulationLogger.getInstance().setOutputStream(System.out);
    }

    /**
     * Check whether or not the singleton pattern is properly implemented
     */
    @Test
    public void testSingletonInstance() {
        SimulationLogger logger1 = SimulationLogger.getInstance();
        SimulationLogger logger2 = SimulationLogger.getInstance();
        assertSame(logger1, logger2, "Should return the same singleton instance");
    }

    /**
     * Test setting log levels work.
     */
    @Test
    public void testSetLogLevel() {
        SimulationLogger logger = SimulationLogger.getInstance();

        logger.setLogLevel(SimulationLogger.LogLevel.DEBUG);
        assertEquals(SimulationLogger.LogLevel.DEBUG, 
            logger.getLogLevel(),
            "Log level should be DEBUG"
        );

        logger.setLogLevel(SimulationLogger.LogLevel.SILENT);
        assertEquals(SimulationLogger.LogLevel.SILENT,
            logger.getLogLevel(),
            "Log level should be SILENT"
        );

        logger.setLogLevel(SimulationLogger.LogLevel.DEFAULT);
        assertEquals(
            SimulationLogger.LogLevel.DEFAULT,
            logger.getLogLevel(),
            "Default log level should be DEFAULT"
        );
    }

    /**
     * Test that the log time step function works
     */
    @Test
    public void testLogTimeStep() {
        SimulationLogger logger = SimulationLogger.getInstance();

        logger.setLogLevel(SimulationLogger.LogLevel.DEFAULT);
        logger.log(new TimeStepEvent(5));

        String output = outputStream.toString();
        assertTrue(output.contains("--- Turn 5 ---"), "Should log time step");
    }

    /**
     * Testing spreading on default logging level
     */
    @Test
    public void testLogSpreadDefaultLevel() {
        SimulationLogger logger = SimulationLogger.getInstance();

        Spreader testSpreader = new DefaultSpreader(
            new RandomSpreading(), new InstantExtraction(0.8));
        ViewableTile testTile1 = new DefaultTile(10.0, 5.0);
        ViewableTile testTile2 = new DefaultTile(20.0, 8.0);

        logger.setLogLevel(SimulationLogger.LogLevel.DEFAULT);
        logger.log(new SpreadEvent(
            testSpreader.getClass().getSimpleName(),
            testTile1.getID(),
            testTile2.getID(),
            15.5
        ));

        String output = outputStream.toString();
        assertTrue(output.contains("Spread:"), "Should log spread in DEFAULT mode");
        assertTrue(output.contains("DefaultSpreader"), "Should contain spreader name");
        assertTrue(output.contains("15.5"), "Should contain power");
    }

    /**
     * Testing infection, should only be on debug logs
     */
    @Test
    public void testLogInfectionDebugOnly() {
        SimulationLogger logger = SimulationLogger.getInstance();

        Spreader testSpreader = new DefaultSpreader(
            new RandomSpreading(), new InstantExtraction(0.8));
        UUID tileId = UUID.randomUUID();

        // Test DEBUG level, should log
        logger.setLogLevel(SimulationLogger.LogLevel.DEBUG);
        logger.log(new InfectionEvent(tileId, 10.0, testSpreader.getClass().getSimpleName()));

        String output = outputStream.toString();
        assertTrue(output.contains("Infection:"), "Should log infection in DEBUG mode");

        // Reset and test DEFAULT level, should not log
        outputStream.reset();
        logger.setLogLevel(SimulationLogger.LogLevel.DEFAULT);
        logger.log(new InfectionEvent(tileId, 10.0, testSpreader.getClass().getSimpleName()));

        output = outputStream.toString();
        assertFalse(output.contains("Infection:"), "Should NOT log infection in DEFAULT mode");
    }

    /**
     * testing extraction, should only be on debug logs
     */
    @Test
    public void testLogExtractionDebugOnly() {
        SimulationLogger logger = SimulationLogger.getInstance();

        UUID tileId = UUID.randomUUID();

        // Test DEBUG level - should log
        logger.setLogLevel(SimulationLogger.LogLevel.DEBUG);
        logger.log(new ExtractionEvent(tileId, 5.0, 0.8));

        String output = outputStream.toString();
        assertTrue(output.contains("Extraction:"), "Should log extraction in DEBUG mode");
        assertTrue(output.contains("5.0"), "Should contain amount");
        assertTrue(output.contains("0.8"), "Should contain efficiency");

        // Reset and test DEFAULT level - should NOT log
        outputStream.reset();
        logger.setLogLevel(SimulationLogger.LogLevel.DEFAULT);
        logger.log(new ExtractionEvent(tileId, 5.0, 0.8));

        output = outputStream.toString();
        assertFalse(output.contains("Extraction:"), "Should NOT log extraction in DEFAULT mode");
    }

    /**
     * Testing silent mode, it should be... silent...
     */
    @Test
    public void testSilentMode() {
        SimulationLogger logger = SimulationLogger.getInstance();

        Spreader testSpreader = new DefaultSpreader(
            new RandomSpreading(), new InstantExtraction(0.8));
        ViewableTile testTile1 = new DefaultTile(10.0, 5.0);
        ViewableTile testTile2 = new DefaultTile(20.0, 8.0);

        logger.setLogLevel(SimulationLogger.LogLevel.SILENT);

        logger.log(new TimeStepEvent(1));
        logger.log(new SpreadEvent(
            testSpreader.getClass().getSimpleName(),
            testTile1.getID(),
            testTile2.getID(),
            10.0
        ));
        logger.log(new InfectionEvent(UUID.randomUUID(), 5.0, testSpreader.getClass().getSimpleName()));
        logger.log(new ExtractionEvent(UUID.randomUUID(), 3.0, 0.5));

        String output = outputStream.toString();
        assertEquals("", output, "Should not log anything in SILENT mode");
    }

    /**
     * Tests that the state properly gets reset via reset()
     */
    @Test
    public void testResetClearsState() {
        SimulationLogger logger = SimulationLogger.getInstance();

        logger.log(new TimeStepEvent(1));
        logger.addFinalStateInfo("test", "value");

        logger.reset();

        assertEquals(0, logger.getSimulationLog().size(), "Log should be empty after reset");
    }

    /**
     * Test that the get log function is working
     */
    @Test
    public void testGetSimulationLog() {
        SimulationLogger logger = SimulationLogger.getInstance();

        Spreader testSpreader = new DefaultSpreader(
            new RandomSpreading(), new InstantExtraction(0.8));
        ViewableTile testTile1 = new DefaultTile(10.0, 5.0);
        ViewableTile testTile2 = new DefaultTile(20.0, 8.0);

        logger.setLogLevel(SimulationLogger.LogLevel.DEFAULT);

        logger.log(new TimeStepEvent(1));
        logger.log(new TimeStepEvent(2));
        logger.log(new SpreadEvent(
            testSpreader.getClass().getSimpleName(),
            testTile1.getID(),
            testTile2.getID(),
            10.0
        ));

        assertEquals(3, logger.getSimulationLog().size(), "Should have 3 log entries");
        assertTrue(logger.getSimulationLog().get(0).contains("Turn 1"),
            "First entry should be Turn 1");
        assertTrue(logger.getSimulationLog().get(1).contains("Turn 2"),
            "Second entry should be Turn 2");
        assertTrue(logger.getSimulationLog().get(2).contains("Spread"),
            "Third entry should be Spread");
    }

    /**
     * Test that multiple time steps work
     */
    @Test
    public void testMultipleTimeSteps() {
        SimulationLogger logger = SimulationLogger.getInstance();

        logger.setLogLevel(SimulationLogger.LogLevel.DEFAULT);

        for (int i = 0; i < 5; i++) {
            logger.log(new TimeStepEvent(i));
        }

        String output = outputStream.toString();
        assertTrue(output.contains("--- Turn 0 ---"), "Should log turn 0");
        assertTrue(output.contains("--- Turn 4 ---"), "Should log turn 4");
    }
}
