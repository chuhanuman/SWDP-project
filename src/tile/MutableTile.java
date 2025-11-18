package tile;

import spreader.Spreader;

public abstract class MutableTile implements ViewableTile {
	public static abstract class Builder {
		/**
		 * Creates a {@code MutableTile} at {@code row, col}.
		 * @param row the row location of the tile
		 * @param col the column location of the tile
		 * @return the created {@code MutableTile}
		 */
		public abstract MutableTile build(int row, int col);
	}

	private int row;
	private int col;

	public MutableTile(int row, int col) {
		this.row = row;
		this.col = col;
	}

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

	@Override
	public int getRow() { return row; }

	@Override
	public int getCol() { return col; }
}
