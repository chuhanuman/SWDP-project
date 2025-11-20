package tile;

import spreader.Spreader;

public class DefaultTile implements Tile {
	private double difficulty;
	private double resources;
	private Spreader occupier;
	private double occupierPower;
	
	public DefaultTile(double difficulty, double resources) {
		this.difficulty = difficulty;
		this.resources = Math.max(0, resources);
		this.occupier = null;
		this.occupierPower = 0;
	}
	
	public DefaultTile(double difficulty, double resources, Spreader occupier, double occupierPower) {
		this.difficulty = difficulty;
		this.resources = Math.max(0, resources);
		this.occupier = occupier;
		this.occupierPower = occupierPower;
	}
	
	@Override
	public Tile copy() {
		return new DefaultTile(difficulty, resources, occupier, occupierPower);
	}
	
	@Override
	public void infect(double power, Spreader spreader) {
		power = Math.max(0, power);
		
		difficulty -= Math.sqrt(power);
		difficulty = Math.max(0, difficulty);
		
		occupierPower = Math.max(0, power - difficulty);
		if (occupierPower > 0) {
			occupier = spreader;
		}
	}

	@Override
	public double extract(double amountToExtract) {
		double amountExtracted = Math.min(resources, Math.max(0, amountToExtract));
		resources -= amountExtracted;
		return amountExtracted;
	}
	
	@Override
	public void changeOccupierPower(double amount) {
		occupierPower = Math.max(0, occupierPower + amount);
		if (occupierPower == 0) {
			occupier = null;
		}
	}

	@Override
	public double getDifficulty() {
		return difficulty;
	}

	@Override
	public double getResources() {
		return resources;
	}
	
	@Override
	public Spreader getOccupier() {
		return occupier;
	}
	
	@Override
	public double getOccupierPower() {
		return occupierPower;
	}
}
