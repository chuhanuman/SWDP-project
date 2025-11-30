package tests.spreader;

import grid.GridView;
import spreader.Spreader;
import turn.extract.ExtractScheduler;
import turn.move.MoveScheduler;

public class FakeSpreader implements Spreader {
	@Override
	public void getMoveActions(GridView grid, MoveScheduler scheduler) {
		return;
	}

	@Override
	public void getExtractActions(GridView grid, ExtractScheduler scheduler) {
		return;
	}
}
