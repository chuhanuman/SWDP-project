package turn.move;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import grid.GridView;
import grid.TileGrid;
import logging.SimulationLogger;
import spreader.Spreader;
import tile.ViewableTile;
import turn.TurnStage;

/**
 * Implementation of a spreader movement scheduler and turn stage.
 * Role(s): client role in iterator pattern
 */
public class DefaultMoveStage implements TurnStage, MoveScheduler {

    Map<UUID, Double> leavingPowerMap;
    Map<UUID, InfectAction> infectionMap;

    public DefaultMoveStage() {
        leavingPowerMap = new HashMap<>();
        infectionMap = new HashMap<>();
    }

    @Override
    public void queueMove(ViewableTile fromTile, ViewableTile toTile, double availablePower) {
        leavingPowerMap.compute(fromTile.getID(), (id, p) ->
            p == null ? availablePower : p + availablePower
        );

        infectionMap.computeIfAbsent(toTile.getID(), (id) -> new InfectAction(toTile));
        infectionMap.get(toTile.getID()).addSpreader(fromTile.getOccupier(), availablePower);

        SimulationLogger.getInstance().log(
            new logging.events.SpreadEvent(
                fromTile.getOccupier() == null ? null : fromTile.getOccupier().getClass().getSimpleName(),
                fromTile.getID(),
                toTile.getID(),
                availablePower
            )
        );
    }

    @Override
    public void executeStage(TileGrid tileGrid) {
        // occupiers leave original tiles
        Iterator<Entry<UUID, Double>> it = leavingPowerMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<UUID, Double> e = it.next();
            tileGrid.addFlatOccupierPower(e.getKey(), -e.getValue());
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
    public void gatherActions(GridView gridView) {
        for (Spreader s : gridView.getSpreaders()) {
            s.getMoveActions(gridView, this);
        }
    }
    
}
