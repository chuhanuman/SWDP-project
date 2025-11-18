package spreader.spreading_strategy;

import java.util.ArrayList;
import java.util.List;

import spreader.Spreader;
import simulation.GridView;
import simulation.TurnChange;
import tile.ViewableTile;

public class GreedySpreading implements SpreadingStrategy {
	int range;
	
	GreedySpreading(int range) {
		this.range = range;
	}
	
	@Override
	public void getMoveActions(GridView grid, TurnChange simulation, Spreader spreader) {
		for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
			double availablePower = tile.getOccupierPower();
			
			double totalDifficulty = 0;
			List<ViewableTile> targetTiles = new ArrayList<ViewableTile>();
			for (ViewableTile potentialTarget : grid.getAllTilesInRange(tile, range)) {
				if (potentialTarget.getOccupier() == null && potentialTarget.getResources() > 0) {
					targetTiles.add(potentialTarget);
					totalDifficulty += Math.max(1, potentialTarget.getDifficulty());
				}
			}
			
			for (ViewableTile target : targetTiles) {
				simulation.queueMove(tile, target, availablePower * (Math.max(1, target.getDifficulty()) / totalDifficulty));
			}
		}
	}
}
