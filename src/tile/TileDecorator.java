package tile;

import java.util.UUID;

import spreader.Spreader;

/**
 * Abstract class that decorates a tile
 * Role(s): decorator role in decorator pattern, the inner class Applier plays the command role in the command pattern
 */
public abstract class TileDecorator implements Tile {
	/**
	 * Functional interface for applying a decorator to an inner tile
	 * Role(s): command role in command pattern
	 */
	public static interface Applier {
		TileDecorator apply(Tile inner);
	}

	protected Tile child;
	
	public TileDecorator(Tile child) {
		this.child = child;
	}

	@Override
	public void infect(double power, Spreader spreader) {
		child.infect(power, spreader);
	}

	@Override
	public double extract(double amountToExtract) {
		return child.extract(amountToExtract);
	}
	
	@Override
	public void addFlatOccupierPower(double amount) {
		child.addFlatOccupierPower(amount);
	}
	
	@Override
	public void multiplyOccupierPower(double amount) {
		child.multiplyOccupierPower(amount);
	}

	@Override
	public double getDifficulty() {
		return child.getDifficulty();
	}

	@Override
	public double getResources() {
		return child.getResources();
	}

	@Override
	public Spreader getOccupier() {
		return child.getOccupier();
	}

	@Override
	public double getOccupierPower() {
		return child.getOccupierPower();
	}
	
	@Override
	public final UUID getID() {
		return child.getID();
	}
}
