package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;

import grid.GridView;
import spreader.Spreader;
import turn.move.MoveScheduler;
import tile.ConstTile;

public class GreedySpreading implements SpreadingStrategy {
	int range;
	
	GreedySpreading(int range) {
		this.range = range;
	}
	
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler, Spreader spreader) {
		for (ConstTile tile : grid.getOccupiedTiles(spreader)) {
			double availablePower = tile.getOccupierPower();
			
			double totalDifficulty = 0;
			List<ConstTile> targetTiles = new ArrayList<ConstTile>();
			for (ConstTile potentialTarget : grid.getAllTilesInRange(tile, range)) {
				if (potentialTarget.getOccupier() == null && potentialTarget.getResources() > 0) {
					targetTiles.add(potentialTarget);
					totalDifficulty += Math.max(1, potentialTarget.getDifficulty());
				}
			}
			
			for (ConstTile target : targetTiles) {
				scheduler.queueMove(tile, target, availablePower * (Math.max(1, target.getDifficulty()) / totalDifficulty));
			}
		}
	}
}
