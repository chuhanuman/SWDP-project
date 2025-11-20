package simulation;

import prng.PRNG;

public abstract class Simulation {
    public static abstract class Builder {
        protected long rngSeed;
        protected double powerLossRate;

        public Builder() {
            rngSeed = 0;
            powerLossRate = 0;
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
         * sets the simulation's occupier power loss rate per turn
         * @param powerLossRate the amount of power occupiers globally lose each turn
         */
        public Builder setPowerLossRate(double powerLossRate) { 
            this.powerLossRate = powerLossRate; 
            return this;
        }

        /**
         * builds the Simulation object based on the builder's specifications
         * @return the created Simulation object
         */
        public abstract Simulation build();
    }

    protected double powerLossRate;
    
    protected Simulation(long rngSeed, double powerLossRate) {
        PRNG.seed(rngSeed);
        this.powerLossRate = powerLossRate;
        // TODO: add more simulation parameters
    }
}
