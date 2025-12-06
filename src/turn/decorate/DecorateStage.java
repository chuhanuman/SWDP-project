package turn.decorate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import grid.GridPos;
import grid.GridView;
import grid.TileGrid;
import prng.PRNG;
import tile.TileDecorator;
import tile.ViewableTile;
import turn.TurnStage;

/**
 * Implementation for a stage where tiles are randomly decorated.
 * Role(s): client role in decorator pattern, client role in singleton pattern, client role in iterator pattern
 */
public class DecorateStage implements TurnStage {

    private double decorateChance;
    private List<TileDecorator.Applier> appliers;
    private Map<GridPos, TileDecorator.Applier> decorations;

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
    public void gatherActions(GridView gridView) {
        PRNG rng = PRNG.getInstance();
        for (ViewableTile t : gridView.getAllTiles()) {
            if (rng.chance(decorateChance)) {
                decorations.put(gridView.getPos(t), rng.choice(appliers));
            }
        }
    }

    @Override
    public void executeStage(TileGrid tileGrid) {
        Iterator<Entry<GridPos, TileDecorator.Applier>> it = decorations.entrySet().iterator();
        while (it.hasNext()) {
            Entry<GridPos, TileDecorator.Applier> e = it.next();
            tileGrid.decorateTile(e.getKey(), e.getValue());
            it.remove();
        }
    }
    
}
