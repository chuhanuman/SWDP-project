package tests.turn;

import java.util.ArrayList;
import java.util.List;

import tile.ConstTile;
import turn.extract.ExtractScheduler;

public class FakeExtractScheduler implements ExtractScheduler {
	private record ExtractAction(ConstTile tile, double resourcesToExtract, double efficiency) {}
	private List<ExtractAction> extractActions;
	
	public FakeExtractScheduler() {
		extractActions = new ArrayList<ExtractAction>();
	}
	
	@Override
	public void queueExtract(ConstTile tile, double resourcesToExtract, double efficiency) {
		extractActions.add(new ExtractAction(tile, resourcesToExtract, efficiency));
	}
	
	public int getNumExtractActions() {
		return extractActions.size();
	}
	
	public boolean checkExtractAction(ConstTile tile, double resourcesToExtract, double efficiency) {
		for (ExtractAction extractAction : extractActions) {
			if (extractAction.tile == tile && 
				Math.abs(extractAction.resourcesToExtract() - resourcesToExtract) < 1e-7 &&
				Math.abs(extractAction.efficiency() - efficiency) < 1e-7) {
				return true;
			}
		}
		
		return false;
	}
	
	public void reset() {
		extractActions.clear();
	}
}
