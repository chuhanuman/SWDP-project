package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import grid.GridView;
import spreader.Spreader;
import turn.move.MoveScheduler;
import tile.ConstTile;

public class CowardSpreading implements SpreadingStrategy {
	PriorityQueue<ConstTile> potentialTargets = null;
	List<ConstTile> occupiedResourceTiles = null;
	
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler, Spreader spreader) {
		if (potentialTargets == null) {
			potentialTargets = new PriorityQueue<ConstTile>(
				(ConstTile left, ConstTile right) -> Double.compare(right.getDifficulty(), left.getDifficulty())
			);
			// loop because addAll doesn't accept Iterables
			for (ConstTile t : grid.getUnoccupiedResourceTiles()) {
				potentialTargets.add(t);
			}
			occupiedResourceTiles = new ArrayList<ConstTile>();
		} else {
			List<ConstTile> tilesToRemove = new ArrayList<ConstTile>();
			for (ConstTile tile : occupiedResourceTiles) {
				if (tile.getResources() == 0) {
					tilesToRemove.add(tile);
				} else if (tile.getOccupier() == null) {
					tilesToRemove.add(tile);
					potentialTargets.add(tile);
				}
			}
			
			occupiedResourceTiles.removeAll(tilesToRemove);
		}
		
		ConstTile target = null;
		while (target == null && !potentialTargets.isEmpty()) {
			if (potentialTargets.peek().getResources() == 0) {
				potentialTargets.remove();
			} else if (potentialTargets.peek().getOccupier() != null) {
				occupiedResourceTiles.add(potentialTargets.poll());
			} else {
				target = potentialTargets.peek();
			}
		}
		
		if (target != null) {
			for (ConstTile tile : grid.getOccupiedTiles(spreader)) {
				double availablePower = tile.getOccupierPower();
				if (tile.getResources() > 0) {
					if (availablePower <= 1) {
						continue;
					}
					
					availablePower -= 1;
				}
				
				scheduler.queueMove(tile, target, availablePower);
			}
		}
	}
}
