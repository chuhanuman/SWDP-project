package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import spreader.Spreader;
import simulation.GridView;
import simulation.TurnChange;
import tile.ViewableTile;

public class CowardSpreading implements SpreadingStrategy {
	PriorityQueue<ViewableTile> potentialTargets = null;
	List<ViewableTile> occupiedResourceTiles = null;
	
	@Override
	public void getMoveActions(GridView grid, TurnChange simulation, Spreader spreader) {
		if (potentialTargets == null) {
			potentialTargets = new PriorityQueue<ViewableTile>(
				(ViewableTile left, ViewableTile right) -> Double.compare(right.getDifficulty(), left.getDifficulty())
			);
			// loop because addAll doesn't accept Iterables
			for (ViewableTile t : grid.getUnoccupiedResourceTiles()) {
				potentialTargets.add(t);
			}
			occupiedResourceTiles = new ArrayList<ViewableTile>();
		} else {
			List<ViewableTile> tilesToRemove = new ArrayList<ViewableTile>();
			for (ViewableTile tile : occupiedResourceTiles) {
				if (tile.getResources() == 0) {
					tilesToRemove.add(tile);
				} else if (tile.getOccupier() == null) {
					tilesToRemove.add(tile);
					potentialTargets.add(tile);
				}
			}
			
			occupiedResourceTiles.removeAll(tilesToRemove);
		}
		
		ViewableTile target = null;
		while (target == null && !potentialTargets.isEmpty()) {
			if (potentialTargets.peek().getResources() == 0) {
				potentialTargets.remove();
			} else if (potentialTargets.peek().getOccupier() != null) {
				occupiedResourceTiles.add(potentialTargets.poll());
			}
		}
		
		if (target != null) {
			for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
				double availablePower = tile.getOccupierPower();
				if (tile.getResources() > 0) {
					if (availablePower <= 1) {
						continue;
					}
					
					availablePower -= 1;
				}
				
				simulation.queueMove(tile, target, availablePower);
			}
		}
	}
}
