package tile;

public class TileDecorator implements Tile {
	protected Tile child;
	
	public TileDecorator(Tile child) {
		this.child = child;
	}

	@Override
	public double infect(double power) {
		return child.infect(power);
	}

	@Override
	public double extract(double amountToExtract) {
		return child.extract(amountToExtract);
	}

	@Override
	public double getDifficulty() {
		return child.getDifficulty();
	}

	@Override
	public double getResources() {
		return child.getResources();
	}
}
