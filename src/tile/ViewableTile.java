package tile;

import java.util.UUID;

import spreader.Spreader;

/**
 * Handles the interface for an unmodifiable tile
 * Role(s): N/A
 */
public interface ViewableTile {
    
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
    
    /**
     * Returns the UUID for the tile
     * @return the UUID for the tile
     */
    public abstract UUID getID();
}
