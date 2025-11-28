package tests.turn;

import java.util.ArrayList;
import java.util.List;

import tile.ConstTile;
import turn.move.MoveScheduler;

public class FakeMoveScheduler implements MoveScheduler {
	private record MoveAction(ConstTile fromTile, ConstTile toTile, double availablePower) {}
	private List<MoveAction> moveActions;
	
	public FakeMoveScheduler() {
		moveActions = new ArrayList<MoveAction>();
	}
	
	@Override
	public void queueMove(ConstTile fromTile, ConstTile toTile, double availablePower) {
		moveActions.add(new MoveAction(fromTile, toTile, availablePower));
	}
	
	public int getNumMoveActions() {
		return moveActions.size();
	}
	
	public boolean checkMoveAction(ConstTile fromTile, ConstTile toTile, double availablePower) {
		for (MoveAction moveAction : moveActions) {
			if (moveAction.fromTile == fromTile && 
				moveAction.toTile == toTile &&
				Math.abs(moveAction.availablePower - availablePower) < 1e-7) {
				return true;
			}
		}
		
		return false;
	}
	
	public double checkMoveAction(ConstTile fromTile, ConstTile toTile) {
		for (MoveAction moveAction : moveActions) {
			if (moveAction.fromTile == fromTile && moveAction.toTile == toTile) {
				return moveAction.availablePower;
			}
		}
		
		return 0;
	}
	
	public void reset() {
		moveActions.clear();
	}
}
