package turn.extract;

import tile.ViewableTile;

public interface ExtractScheduler {

    /**
     * Queue a resource extraction action for the turn.
     * @param tile the tile to perform the action on
     * @param resourcesToExtract the amount of resources to extract
     * @param efficiency the efficiency at which resources are extracted
     */
    public void queueExtract(ViewableTile tile, double resourcesToExtract, double efficiency);
}
