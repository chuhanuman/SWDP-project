package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;

import grid.GridView;
import prng.PRNG;
import spreader.Spreader;
import turn.move.MoveScheduler;
import tile.ViewableTile;

/**
 * Concrete implementation of a spreading strategy that spreads randomly
 * Role(s): concrete strategy role in strategy pattern, client role in singleton pattern, client role in iterator pattern
 */
public class RandomSpreading implements SpreadingStrategy {
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler, Spreader spreader) {
		for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
			List<ViewableTile> targetTiles = new ArrayList<ViewableTile>();
			for (ViewableTile potentialTarget : grid.getAllTilesInRange(tile, 1)) {
				if (potentialTarget.getOccupier() == null || potentialTarget.getOccupier() == spreader) {
					targetTiles.add(potentialTarget);
				}
			}
			
			List<Double> powerAllocations = PRNG.getInstance().split(tile.getOccupierPower(), targetTiles.size());
			
			for (int i = 0; i < targetTiles.size(); i++) {
				scheduler.queueMove(tile, targetTiles.get(i), powerAllocations.get(i));
			}
		}
	}
}
