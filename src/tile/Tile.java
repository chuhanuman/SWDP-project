package tile;

public interface Tile {
	/**
	 * Attempts to infect the tile
	 * @param power the power of the infection attempt
	 * @return the power left over from the attempt, a negative value means that the attempt failed
	 */
	public abstract double infect(double power);
	
	/**
	 * Attempts to extract resources from the tile
	 * @param amountToExtract the number of resources to extract
	 * @return the amount of resources extracted
	 */
	public abstract double extract(double amountToExtract);
	
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
}
