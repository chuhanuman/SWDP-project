package tile;

import spreader.Spreader;

public class ConstTile {
	private Tile child;
	
	public ConstTile(Tile child) {
		this.child = child;
	}
	
    /**
	 * Returns the difficulty of infecting the tile
	 * @return the difficulty of infecting the tile, in general higher values mean more losses 
	 * when infecting but this is implementation specific
	 */
	public double getDifficulty() {
		return child.getDifficulty();
	}
	
	/**
	 * Returns the number of resources in the tile
	 * @return the number of resources in the tile
	 */
	public double getResources() {
		return child.getResources();
	}
	
	/**
	 * Returns the occupier of the tile or null if there are no occupiers
	 * @return the occupier of the tile or null if there are no occupiers
	 */
	public Spreader getOccupier() {
		return child.getOccupier();
	}
	
	/**
	 * Returns the power of the occupiers on the tile
	 * @return the power of the occupiers on the tile
	 */
	public double getOccupierPower() {
		return child.getOccupierPower();
	}
}
