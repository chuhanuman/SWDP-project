package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;

import grid.GridView;
import prng.PRNG;
import spreader.Spreader;
import turn.move.MoveScheduler;
import tile.ConstTile;

public class RandomSpreading implements SpreadingStrategy {
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler, Spreader spreader) {
		for (ConstTile tile : grid.getOccupiedTiles(spreader)) {
			double availablePower = tile.getOccupierPower() / 2;
			
			List<ConstTile> targetTiles = new ArrayList<ConstTile>();
			for (ConstTile potentialTarget : grid.getAllTilesInRange(tile, 1)) {
				if (potentialTarget.getOccupier() == null || potentialTarget.getOccupier() == spreader) {
					targetTiles.add(potentialTarget);
				}
			}
			
			List<Double> powerAllocations = PRNG.getInstance().split(availablePower, targetTiles.size());
			
			for (int i = 0; i < targetTiles.size(); i++) {
				scheduler.queueMove(tile, targetTiles.get(i), powerAllocations.get(i));
			}
		}
	}
}
