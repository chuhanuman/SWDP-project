package simulation;

import java.util.List;
import java.util.Queue;

import spreader.Spreader;
import tile.MutableTile;
import tile.ViewableTile;

public class TurnChange {

    protected record ExtractAction(Spreader spreader, double resourcesToExtract, double efficiency) {}
    protected Queue<ExtractAction> extractQueue;

    protected record MoveAction(ViewableTile fromTile, ViewableTile toTile, double availablePower) { }
    protected Queue<MoveAction> moveQueue;

    /**
     * Queue a resource extraction action for the turn.
     * @param spreader the spreader which will perform the action
     * @param resourcesToExtract the amount of resources to extract
     * @param efficiency the efficiency at which resources are extracted
     */
    public void queueExtract(Spreader spreader, double resourcesToExtract, double efficiency) {
        this.extractQueue.add(new ExtractAction(spreader, resourcesToExtract, efficiency));
    }

    /**
     * Queue a spreader movement action for the turn
     * @param fromTile the tile to move from
     * @param toTile the tile to move to
     * @param availablePower the amount of spreader power which will move
     */
    public void queueMove(ViewableTile fromTile, ViewableTile toTile, double availablePower) {
        this.moveQueue.add(new MoveAction(fromTile, toTile, availablePower));
    }

    /**
     * Executes all queued changes upon the provided {@code tileGrid}
     * @param tileGrid the grid to execute all queued changes upon
     */
    public void executeTurn(List<List<MutableTile>> tileGrid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeTurn'");
    }
}
