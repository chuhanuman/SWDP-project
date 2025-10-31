package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;

import spreader.Spreader;
import tile.Tile;

public class RandomSpreading implements SpreadingStrategy {
	@Override
	public void getMoveActions(Simulation simulation, Spreader spreader) {
		for (Tile tile : simulation.getOccupiedTiles(spreader)) {
			double availablePower = tile.getOccupierPower() / 2;
			
			List<Tile> targetTiles = new ArrayList<Tile>();
			for (Tile potentialTarget : simulation.getAllTilesInRange(tile, 1)) {
				if (potentialTarget.getOccupier() == null && potentialTarget != tile) {
					targetTiles.add(potentialTarget);
				}
			}
			
			List<Double> powerAllocations = PRNG::getInstance().split(availablePower);
			
			for (int i = 0; i < targetTiles.size(); i++) {
				simulation.queueMove(tile, targetTiles.get(i), powerAllocations.get(i));
			}
		}
	}
}
