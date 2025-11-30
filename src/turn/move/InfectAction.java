package turn.move;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import grid.TileGrid;
import spreader.Spreader;
import tile.ConstTile;
import tile.ViewableTile;
import turn.SimulationAction;

public class InfectAction implements SimulationAction {
    
    /**
     * key: spreader trying to infect this tile
     * value: power of spreader infecting the tile
     */
    private final Map<Spreader, Double> spreaderMap;
    private final ViewableTile tile;

    public InfectAction(ViewableTile tile) {
        this.tile = tile;
        spreaderMap = new HashMap<>();
    }

    public void addSpreader(Spreader spreader, double power) {
        // combine powers if the same spreader
        spreaderMap.compute(spreader, (s, p) ->
            (p == null) ? power : p + power
        );
    }

    @Override
    public void execute(TileGrid tileGrid) {
        if (spreaderMap.isEmpty()) {
            return;
        }

        Entry<Spreader, Double> result = computeSpreaderCancellation();

        tileGrid.infectTile(this.tile.getID(), result.getValue(), result.getKey());
    }

    /**
     * All spreaders "fight" for territory, resulting in the strongest spreader 
     * surviving with its power difference compared to the next strongest.
     * @return the winning spreader, remaining power pair
     */
    public Entry<Spreader, Double> computeSpreaderCancellation() {
        Entry<Spreader, Double> highestEntry = null;
        Entry<Spreader, Double> secondHighestEntry = null;
        for (Entry<Spreader, Double> e : spreaderMap.entrySet()) {
            if (highestEntry == null || e.getValue() > highestEntry.getValue()) {
                secondHighestEntry = highestEntry;
                highestEntry = e;
            } else if (secondHighestEntry == null || e.getValue() > secondHighestEntry.getValue()) {
                secondHighestEntry = e;
            }
        }
        double secondHighestPower = secondHighestEntry == null 
                                    ? 0 
                                    : secondHighestEntry.getValue();
        
        double newPower = highestEntry.getValue() - secondHighestPower;

        return new SimpleEntry<>(highestEntry.getKey(), newPower);
    }
}
