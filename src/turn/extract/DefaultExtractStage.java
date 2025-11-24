package turn.extract;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

import grid.TileGrid;
import tile.ConstTile;
import turn.TurnStage;

public class DefaultExtractStage implements TurnStage, ExtractScheduler {

    private Map<UUID, Collection<ExtractAction>> extractMap;

    public DefaultExtractStage() {
        extractMap = new HashMap<>();
    }

    @Override
    public void queueExtract(ConstTile tile, double resourcesToExtract, double efficiency) {
        // priority queue on efficiency (highest first)
        Collection<ExtractAction> existingActions = extractMap.computeIfAbsent(tile.getID(), 
                                                                                  k -> new PriorityQueue<>(new ExtractComparator()));
        existingActions.add(new ExtractAction(tile, resourcesToExtract, efficiency));
    }

    @Override
    public void execute(TileGrid tileGrid) {
        for (Collection<ExtractAction> actions : extractMap.values()) {
            Iterator<ExtractAction> it = actions.iterator();
            while (it.hasNext()) {
                ExtractAction action = it.next();
                action.execute(tileGrid);
                it.remove();
            }
        }
    }
    
    private class ExtractComparator implements Comparator<ExtractAction> {
        @Override
        public int compare(ExtractAction o1, ExtractAction o2) {
            if (o1.efficiency() < o2.efficiency()) return 1;
            if (o1.efficiency() > o2.efficiency()) return -1;
            return 0;
        }
    }
}
