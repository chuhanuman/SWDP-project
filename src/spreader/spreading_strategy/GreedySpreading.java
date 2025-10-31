package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;

import spreader.Spreader;
import tile.Tile;

public class GreedySpreading implements SpreadingStrategy {
	int range;
	
	GreedySpreading(int range) {
		this.range = range;
	}
	
	@Override
	public void getMoveActions(Simulation simulation, Spreader spreader) {
		for (Tile tile : simulation.getOccupiedTiles(spreader)) {
			double availablePower = tile.getOccupierPower();
			
			double totalDifficulty = 0;
			List<Tile> targetTiles = new ArrayList<Tile>();
			for (Tile potentialTarget : simulation.getAllTilesInRange(tile, range)) {
				if (potentialTarget.getOccupier() == null && potentialTarget.getResources() > 0) {
					targetTiles.add(potentialTarget);
					totalDifficulty += Math.max(1, potentialTarget.getDifficulty());
				}
			}
			
			for (Tile target : targetTiles) {
				simulation.queueMove(tile, target, availablePower * (Math.max(1, target.getDifficulty()) / totalDifficulty));
			}
		}
	}
}
