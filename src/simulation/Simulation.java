package simulation;

import java.io.PrintStream;

import prng.PRNG;

/**
 * Represents a turn-based simulation
 * Role: n/a
 */
public abstract class Simulation {
    public static abstract class Builder {
        protected long rngSeed;

        public Builder() {
            rngSeed = 0;
        }

        /**
         * sets the simulation's seed used for randomness
         * @param rngSeed the seed value
         */
        public Builder setRNGSeed(long rngSeed) { 
            this.rngSeed = rngSeed; 
            return this;
        }

        /**
         * builds the Simulation object based on the builder's specifications
         * @return the created Simulation object
         */
        public abstract Simulation build();
    }

    protected int turn;
    
    protected Simulation(long rngSeed) {
        PRNG.seed(rngSeed);
        turn = 0;
        // TODO: add more simulation parameters?
    }

    /**
     * Output the simulation state
     * @param out the printstream to output to
     */
    public abstract void print(PrintStream out);

    /**
     * Run the simulation for the specified number of turns.
     * @param totalTurns the number of turns to run for.
     */
    public abstract void run(int totalTurns);
}
