package tile;

import spreader.Spreader;

public abstract class TileDecorator implements MutableTile {
	protected MutableTile child;
	
	public TileDecorator(MutableTile child) {
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
	public void changeOccupierPower(double amount) {
		child.changeOccupierPower(amount);
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
}
