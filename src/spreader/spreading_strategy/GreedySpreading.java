package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;

import grid.GridView;
import spreader.Spreader;
import turn.move.MoveScheduler;
import tile.ViewableTile;

/**
 * Concrete implementation of a spreading strategy that spreads everywhere it can exploit
 * Role(s): concrete strategy role in strategy pattern
 */
public class GreedySpreading implements SpreadingStrategy {
	int range;
	
	public GreedySpreading(int range) {
		this.range = range;
	}
	
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler, Spreader spreader) {
		for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
			double availablePower = tile.getOccupierPower();
			
			double totalDifficulty = 0;
			List<ViewableTile> targetTiles = new ArrayList<ViewableTile>();
			for (ViewableTile potentialTarget : grid.getAllTilesInRange(tile, range)) {
				if ((potentialTarget.getOccupier() == null || potentialTarget.getOccupier() == spreader) && potentialTarget.getResources() > 0) {
					targetTiles.add(potentialTarget);
					totalDifficulty += Math.max(1, potentialTarget.getDifficulty());
				}
			}
			
			for (ViewableTile target : targetTiles) {
				scheduler.queueMove(tile, target, availablePower * (Math.max(1, target.getDifficulty()) / totalDifficulty));
			}
		}
	}
}
