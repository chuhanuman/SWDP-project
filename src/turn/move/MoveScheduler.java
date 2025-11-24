package turn.move;

import tile.ConstTile;

public interface MoveScheduler {

    /**
     * Queue a spreader movement action for the turn
     * @param fromTile the tile to move from
     * @param toTile the tile to move to
     * @param availablePower the amount of spreader power which will move
     */
    public void queueMove(ConstTile fromTile, ConstTile toTile, double availablePower);
}
