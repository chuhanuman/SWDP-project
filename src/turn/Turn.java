package turn;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import grid.TileGrid;

public class Turn {
    public static class Builder {
        private final Map<Class<?>, TurnStage> activeTurnStages;
        private final List<TurnStage> stagesInOrder;

        public Builder() {
            activeTurnStages = new HashMap<>();
            stagesInOrder = new LinkedList<>();
        }
        
        /**
         * set the next stage for the turn.
         * @param <C> the scheduler type for this stage (e.g. ExtractScheduler)
         * @param schedulerType the scheduler class for this stage (e.g. ExtractScheduler.class)
         * @param turnStage the concrete object handling this turn stage
         * @return self
         */
        public <C> Builder nextStage(Class<C> schedulerType, TurnStage turnStage) {
            if (!schedulerType.isInstance(turnStage)) {
                throw new IllegalArgumentException(
                    "turnStage must be an instance of " + schedulerType.getSimpleName()
                );
            }

            activeTurnStages.put(schedulerType, turnStage);
            stagesInOrder.add(turnStage);
            return this;
        }

        public Turn build() {
            return new Turn(activeTurnStages, stagesInOrder);
        }
    }
    
    /**
     * Map of class type to concrete turn stage object
     */
    private final Map<Class<?>, TurnStage> activeTurnStages;

    /**
     * The list of stages in order from first executed to last executed in a turn
     */
    private final List<TurnStage> stagesInOrder;

    protected Turn(Map<Class<?>, TurnStage> activeTurnStages, List<TurnStage> stagesInOrder) {
        this.activeTurnStages = activeTurnStages;
        this.stagesInOrder = stagesInOrder;
    }

    /**
     * Gets the scheduler object of the corresponding schedulerType
     * @param <C> the scheduler type (e.g. ExtractScheduler)
     * @param schedulerType the scheduler class (e.g. ExtractScheduler.class)
     * @return the turn stage scheduler object
     */
    public <C> C getTurnStageScheduler(Class<C> schedulerType) {
        Object obj = activeTurnStages.get(schedulerType);
        if (obj == null) {
            throw new IllegalArgumentException(
                "No such scheduler for type " + schedulerType.getSimpleName()
            );
        }
        return schedulerType.cast(obj);
    }

    public void execute(TileGrid tileGrid) {
        stagesInOrder.forEach(ts -> ts.execute(tileGrid));
    }
    
}
