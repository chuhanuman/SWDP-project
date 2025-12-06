package grid;

import java.util.UUID;

import spreader.Spreader;
import tile.TileDecorator;

/**
 * Represents a viewable and modifiable 2D grid of tiles.
 * Role(s): product role in builder pattern, inner class builder plays the builder role in the builder pattern
 */
public interface TileGrid extends GridView {
    public static abstract class Builder {
        public abstract TileGrid build();
    }

    /**
     * Decorate the tile at (row, col) with the provided decorator applier
     * @param pos the (row, col) position of the tile to decorate
     * @param decoratorFunc the function which applies the decorator to the tile
     */
    public abstract void decorateTile(GridPos pos, TileDecorator.Applier decoratorFunc);
    
    /**
     * Infects the given tile
     * @param id id of the tile to infect
     * @param power the power of the infection attempt
	 * @param spreader the spreader doing the infection
     */
	public abstract void infectTile(UUID id, double power, Spreader spreader);

	/**
	 * Turns resources from the given tile into power
	 * @param id id of the tile to infect
	 * @param amountToExtract the number of resources to extract
	 * @param efficiency the efficiency of the conversion from resources to power
	 */
	public abstract void extractTile(UUID id, double amountToExtract, double efficiency);
	
	/**
	 * Changes occupier power on the given tile by a flat amount
	 * @param id id of the tile to infect
	 * @param power the increase in power if positive and the decrease in power if negative
	 */
	public abstract void addFlatOccupierPower(UUID id, double power);

	/**
	 * Changes occupier power on the given tile by a proportion amount
	 * @param id id of the tile to infect
	 * @param amount the proportional increase in power if > 1 and the decrease in power if < 1
	 */
	public abstract void multiplyOccupierPower(UUID id, double amount);
}
