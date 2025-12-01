package tile;

import java.util.UUID;

import spreader.Spreader;

public class DefaultTile implements Tile {
	private UUID id;

	private double difficulty;
	private double resources;
	private Spreader occupier;
	private double occupierPower;
	
	public DefaultTile(double difficulty, double resources) {
		this.setRequired(difficulty, resources, null, 0);
		this.occupier = null;
		this.occupierPower = 0;
	}
	
	public DefaultTile(double difficulty, double resources, Spreader occupier, double occupierPower) {
		this.setRequired(difficulty, resources, occupier, occupierPower);
	}
	
	@Override
	public Tile copy() {
		return new DefaultTile(difficulty, resources, occupier, occupierPower);
	}
	
	@Override
	public void infect(double power, Spreader spreader) {
		power = Math.max(0, power);
		
		if (occupier == spreader) {
			addFlatOccupierPower(power);
			return;
		}
		
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
	public void addFlatOccupierPower(double amount) {
		if (occupier == null) {
			return;
		}
		
		occupierPower = Math.max(0, occupierPower + amount);
		if (occupierPower == 0) {
			occupier = null;
		}
	}
	
	@Override
	public void multiplyOccupierPower(double amount) {
		if (amount >= 0) {
			occupierPower *= amount;
			
			if (occupierPower == 0) {
				occupier = null;
			}
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

	@Override
	public UUID getID() {
		return this.id;
	}
	
	private void setRequired(double difficulty, double resources, Spreader occupier, double occupierPower) {
		this.id = UUID.randomUUID();
		
		this.difficulty = difficulty;
		this.resources = Math.max(0, resources);
		this.occupier = occupier;
		this.occupierPower = (this.occupier != null) ? Math.max(0, occupierPower) : 0;
	}
}
