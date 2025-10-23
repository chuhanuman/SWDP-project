package tile;

public class DefaultTile implements Tile {
	private double difficulty;
	private double resources;
	
	public DefaultTile(double difficulty, double resources) {
		this.difficulty = difficulty;
		this.resources = Math.max(0, resources);
	}
	
	@Override
	public double infect(double power) {
		power = Math.max(0, power);
		
		difficulty -= Math.sqrt(power);
		difficulty = Math.max(0, difficulty);
		
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
