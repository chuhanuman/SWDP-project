package simulation;

import java.util.List;
import java.util.Queue;

import grid.TileGrid;
import spreader.Spreader;
import tile.Tile;
import tile.ConstTile;

public class TurnChange {

    protected record ExtractAction(ConstTile tile, double resourcesToExtract, double efficiency) {}
    protected Queue<ExtractAction> extractQueue;

    protected record MoveAction(ConstTile fromTile, ConstTile toTile, double availablePower) { }
    protected Queue<MoveAction> moveQueue;

    /**
     * Queue a resource extraction action for the turn.
     * @param tile the tile to perform the action on
     * @param resourcesToExtract the amount of resources to extract
     * @param efficiency the efficiency at which resources are extracted
     */
    public void queueExtract(ConstTile tile, double resourcesToExtract, double efficiency) {
        this.extractQueue.add(new ExtractAction(tile, resourcesToExtract, efficiency));
    }

    /**
     * Queue a spreader movement action for the turn
     * @param fromTile the tile to move from
     * @param toTile the tile to move to
     * @param availablePower the amount of spreader power which will move
     */
    public void queueMove(ConstTile fromTile, ConstTile toTile, double availablePower) {
        this.moveQueue.add(new MoveAction(fromTile, toTile, availablePower));
    }

    /**
     * Executes all queued changes upon the provided {@code tileGrid}
     * @param tileGrid the grid to execute all queued changes upon
     */
    public void executeTurn(TileGrid tileGrid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeTurn'");
    }
}
