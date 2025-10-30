package tile;

import spreader.Spreader;

public interface Tile {
	/**
	 * Attempts to infect the tile
	 * @param power the power of the infection attempt
	 * @param spreader the spreader doing the infection
	 */
	public abstract void infect(double power, Spreader spreader);
	
	/**
	 * Attempts to extract resources from the tile
	 * @param amountToExtract the number of resources to extract
	 * @return the amount of resources extracted
	 */
	public abstract double extract(double amountToExtract);
	
	/**
	 * Changes occupier power on the tile
	 * @param amount the increase in power if positive and the decrease in power if negative
	 */
	public abstract void changeOccupierPower(double amount);
	
	/**
	 * Returns the difficulty of infecting the tile
	 * @return the difficulty of infecting the tile, in general higher values mean more losses 
	 * when infecting but this is implementation specific
	 */
	public abstract double getDifficulty();
	
	/**
	 * Returns the number of resources in the tile
	 * @return the number of resources in the tile
	 */
	public abstract double getResources();
	
	/**
	 * Returns the occupier of the tile or null if there are no occupiers
	 * @return the occupier of the tile or null if there are no occupiers
	 */
	public abstract Spreader getOccupier();
	
	/**
	 * Returns the power of the occupiers on the tile
	 * @return the power of the occupiers on the tile
	 */
	public abstract double getOccupierPower();
}
