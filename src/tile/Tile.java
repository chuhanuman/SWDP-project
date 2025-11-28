package tile;

import spreader.Spreader;

public interface Tile extends ViewableTile{
	/**
	 * Performs a deep copy of the tile
	 * @return a deep copy of the tile
	 */
	public abstract Tile copy();
	
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
	 * Changes occupier power on the tile by a flat amount
	 * @param amount the increase in power if positive and the decrease in power if negative
	 */
	public abstract void addFlatOccupierPower(double amount);
	
	/**
	 * Changes occupier power by a multiplier
	 * @param amount the number to multiply power by, only non negative amounts are accepted
	 */
	public abstract void multiplyOccupierPower(double amount);
}
