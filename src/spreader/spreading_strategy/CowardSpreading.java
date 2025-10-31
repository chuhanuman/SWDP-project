package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import spreader.Spreader;
import tile.Tile;

public class CowardSpreading implements SpreadingStrategy {
	PriorityQueue<Tile> potentialTargets = null;
	List<Tile> occupiedResourceTiles = null;
	
	@Override
	public void getMoveActions(Simulation simulation, Spreader spreader) {
		if (potentialTargets == null) {
			potentialTargets = new PriorityQueue<Tile>(
				(Tile left, Tile right) -> Double.compare(right.getDifficulty(), left.getDifficulty())
			);
			potentialTargets.addAll(simulation.getUnoccupiedResourceTiles());
			occupiedResourceTiles = new ArrayList<Tile>();
		} else {
			List<Tile> tilesToRemove = new ArrayList<Tile>();
			for (Tile tile : occupiedResourceTiles) {
				if (tile.getResources() == 0) {
					tilesToRemove.add(tile);
				} else if (tile.getOccupier() == null) {
					tilesToRemove.add(tile);
					potentialTargets.add(tile);
				}
			}
			
			occupiedResourceTiles.removeAll(tilesToRemove);
		}
		
		Tile target = null;
		while (target == null && !potentialTargets.isEmpty()) {
			if (potentialTargets.peek().getResources() == 0) {
				potentialTargets.remove();
			} else if (potentialTargets.peek().getOccupier() != null) {
				occupiedResourceTiles.add(potentialTargets.poll());
			}
		}
		
		if (target != null) {
			for (Tile tile : simulation.getOccupiedTiles(spreader)) {
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
