package turn.move;

import tile.ViewableTile;

public interface MoveScheduler {

    /**
     * Queue a spreader movement action for the turn
     * @param fromTile the tile to move from
     * @param toTile the tile to move to
     * @param availablePower the amount of spreader power which will move
     */
    public void queueMove(ViewableTile fromTile, ViewableTile toTile, double availablePower);
}
