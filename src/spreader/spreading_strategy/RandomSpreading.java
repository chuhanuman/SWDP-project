package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;

import prng.PRNG;
import spreader.Spreader;
import simulation.GridView;
import simulation.TurnChange;
import tile.ViewableTile;

public class RandomSpreading implements SpreadingStrategy {
	@Override
	public void getMoveActions(GridView grid, TurnChange simulation, Spreader spreader) {
		for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
			double availablePower = tile.getOccupierPower() / 2;
			
			List<ViewableTile> targetTiles = new ArrayList<ViewableTile>();
			for (ViewableTile potentialTarget : grid.getAllTilesInRange(tile, 1)) {
				if (potentialTarget.getOccupier() == null && potentialTarget != tile) {
					targetTiles.add(potentialTarget);
				}
			}
			
			List<Double> powerAllocations = PRNG.getInstance().split(availablePower, targetTiles.size());
			
			for (int i = 0; i < targetTiles.size(); i++) {
				simulation.queueMove(tile, targetTiles.get(i), powerAllocations.get(i));
			}
		}
	}
}
