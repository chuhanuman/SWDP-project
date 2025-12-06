package turn;

import java.util.LinkedList;
import java.util.List;

import grid.GridView;
import grid.TileGrid;

/**
 * Manages execution of a whole turn via an ordered list of stages
 * Role(s): product role in builder pattern, inner class builder plays the concrete builder role in the builder pattern
 */
public class TurnManager {
    public static class Builder {
        private List<TurnStage> stages;

        public Builder() {
            this.stages = new LinkedList<>();
        }

        public Builder nextStage(TurnStage turnStage) {
            stages.add(turnStage);
            return this;
        }

        public TurnManager build() {
            return new TurnManager(stages);
        }
    }

    private final List<TurnStage> stages;

    protected TurnManager(List<TurnStage> stages) {
        this.stages = stages;
    }

    public void executeTurn(GridView simulation, TileGrid grid) {
        for (TurnStage stage : stages) {
            stage.gatherActions(simulation);
            stage.executeStage(grid);
        }
    }
    
}
