package turn.decorate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import grid.GridView;
import grid.TileGrid;
import prng.PRNG;
import simulation.TileGridSimulation.View;
import tile.ConstTile;
import tile.TileDecorator;
import turn.TurnStage;

public class DecorateStage implements TurnStage {

    private double decorateChance;
    private List<TileDecorator.Applier> appliers;
    private Map<UUID, TileDecorator.Applier> decorations;

    /**
     * Stage which randomly decorates tiles
     * @param decorateChance the probability (0-1) that a tile is decorated on a turn
     * @param appliers the list of available decorators to randomly choose from
     */
    public DecorateStage(double decorateChance, List<TileDecorator.Applier> appliers) {
        this.decorateChance = decorateChance;
        this.appliers = appliers;
        decorations = new HashMap<>();
    }

    @Override
    public void gatherActions(View simulation) {
        PRNG rng = PRNG.getInstance();
        GridView grid = simulation.grid();
        for (ConstTile t : grid) {
            if (rng.chance(decorateChance)) {
                decorations.put(t.getID(), rng.choice(appliers));
            }
        }
    }

    @Override
    public void executeStage(TileGrid tileGrid) {
        Iterator<Entry<UUID, TileDecorator.Applier>> it = decorations.entrySet().iterator();
        while (it.hasNext()) {
            Entry<UUID, TileDecorator.Applier> e = it.next();
            tileGrid.decorateTile(tileGrid.getPos(tileGrid.get(e.getKey())), e.getValue());
            it.remove();
        }
    }
    
}
