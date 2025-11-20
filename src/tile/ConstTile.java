package tile;

import java.util.UUID;

import spreader.Spreader;

public class ConstTile implements ViewableTile {
	private Tile child;
	
	public ConstTile(Tile child) {
		this.child = child;
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
	public UUID getID() {
		return child.getID();
	}
}
