package spreader.spreading_strategy;

import grid.GridView;
import spreader.Spreader;
import turn.move.MoveScheduler;
import tile.ViewableTile;

/**
 * Concrete implementation of a spreading strategy that spreads to easier to infect places first
 * Role(s): concrete strategy role in strategy pattern, client role in iterator pattern
 */
public class CowardSpreading implements SpreadingStrategy {
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler, Spreader spreader) {
		ViewableTile target = grid.getEasiestUnoccupiedResourceTile();
		if (target != null) {
			for (ViewableTile tile : grid.getOccupiedTiles(spreader)) {
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
