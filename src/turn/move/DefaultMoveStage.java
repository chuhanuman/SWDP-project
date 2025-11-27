package turn.move;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import grid.TileGrid;
import simulation.TileGridSimulation.View;
import spreader.Spreader;
import tile.ConstTile;
import tile.Tile;
import turn.TurnStage;

public class DefaultMoveStage implements TurnStage, MoveScheduler {

    Map<UUID, Double> leavingPowerMap;
    Map<UUID, InfectAction> infectionMap;

    public DefaultMoveStage() {
        leavingPowerMap = new HashMap<>();
        infectionMap = new HashMap<>();
    }

    @Override
    public void queueMove(ConstTile fromTile, ConstTile toTile, double availablePower) {
        leavingPowerMap.compute(fromTile.getID(), (id, p) ->
            p == null ? availablePower : p + availablePower
        );

        infectionMap.computeIfAbsent(toTile.getID(), (id) -> new InfectAction(toTile));
        infectionMap.get(toTile.getID()).addSpreader(fromTile.getOccupier(), availablePower);
    }

    @Override
    public void executeStage(TileGrid tileGrid) {
        // occupiers leave original tiles
        Iterator<Entry<UUID, Double>> it = leavingPowerMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<UUID, Double> e = it.next();
            Tile tile = tileGrid.get(e.getKey());
            tile.changeOccupierPower(-e.getValue());
            it.remove();
        }

        // occupiers try to infect new tiles
        Iterator<InfectAction> it2 = infectionMap.values().iterator();
        while (it2.hasNext()) {
            InfectAction a = it2.next();
            a.execute(tileGrid);
            it2.remove();
        }
    }

    @Override
    public void gatherActions(View simulation) {
        for (Spreader s : simulation.spreaders()) {
            s.getMoveActions(simulation.grid(), this);
        }
    }
    
}
