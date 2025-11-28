package turn;

import java.util.LinkedList;
import java.util.List;

import grid.TileGrid;
import simulation.TileGridSimulation;

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

    public void executeTurn(TileGridSimulation.View simulation, TileGrid grid) {
        for (TurnStage stage : stages) {
            stage.gatherActions(simulation);
            stage.executeStage(grid);
        }
    }
    
}
