package tile;

public class DefaultTile implements Tile {
	/**
	 * The difficulty of infecting a tile, the higher the value the more difficult it is to infect
	 */
	private double difficulty;
	/**
	 * The number of resources on a tile, guaranteed non negative
	 */
	private double resources;
	
	public DefaultTile(double difficulty, double resources) {
		this.difficulty = difficulty;
		this.resources = Math.max(resources, 0);
	}
	
	@Override
	public double infect(double power) {
		if (power < 0) {
			power = 0;
		}
		
		difficulty -= Math.sqrt(power);
		if (difficulty <= 0) {
			difficulty = 0;
		}
		
		return power - difficulty;
	}

	@Override
	public double extract(double amountToExtract) {
		double amountExtracted = Math.min(resources, Math.max(0, amountToExtract));
		resources -= amountExtracted;
		return amountExtracted;
	}

	@Override
	public double getDifficulty() {
		return difficulty;
	}

	@Override
	public double getResources() {
		return resources;
	}
}
