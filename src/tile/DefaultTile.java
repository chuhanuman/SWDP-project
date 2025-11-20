package tile;

import spreader.Spreader;

public class DefaultTile implements MutableTile {
	public static class Builder extends MutableTile.Builder {
		private double difficulty, resources;
		private Spreader occupier;
		private double occupierPower;

		public Builder() {
			this.difficulty = 0;
			this.resources = 0;
			this.occupier = null;
			this.occupierPower = 0;
		}

		/**
		 * Sets the required tile attributes.
		 * @param difficulty the spreading difficulty of the tile
		 * @param resources the available resources of the tile
		 * @return self reference
		 */
		public Builder setRequired(double difficulty, double resources) {
			this.difficulty = difficulty;
			this.resources = resources;

			return this;
		}

		/**
		 * sets the occupier and its power for the tile to build
		 * @param occupier the spreader occupying the tile
		 * @param power the occupier power of the spreader
		 * @return self reference
		 */
		public Builder setOccupier(Spreader occupier, double power) {
			this.occupier = occupier;
			this.occupierPower = power;

			return this;
		}

		@Override
		public MutableTile build() {
			return new DefaultTile(this.difficulty, this.resources, this.occupier, this.occupierPower);
		}
	}

	private double difficulty;
	private double resources;
	private Spreader occupier;
	private double occupierPower;
	
	protected DefaultTile(double difficulty, double resources, Spreader occupier, double occupierPower) {
		this.difficulty = difficulty;
		this.resources = Math.max(0, resources);
		this.occupier = occupier;
		this.occupierPower = occupierPower;
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
