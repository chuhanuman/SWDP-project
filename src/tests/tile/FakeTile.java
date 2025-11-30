package tests.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import spreader.Spreader;
import tile.Tile;
import tile.ViewableTile;

public class FakeTile implements Tile {
	public double difficulty, resources, occupierPower, resourcesToExtract;
	public Spreader occupier;
	public Tile copy;
	public UUID id;
	public record InfectCall(double power, Spreader spreader) {}
	public List<InfectCall> infectCalls;
	public List<Double> extractCalls, addFlatCalls, multiplyCalls;
	
	public FakeTile() {
		infectCalls = new ArrayList<>();
		extractCalls = new ArrayList<>();
		addFlatCalls = new ArrayList<>();
		multiplyCalls = new ArrayList<>();
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
		return id;
	}

	@Override
	public Tile copy() {
		return copy;
	}

	@Override
	public void infect(double power, Spreader spreader) {
		infectCalls.add(new InfectCall(power, spreader));
	}

	@Override
	public double extract(double amountToExtract) {
		extractCalls.add(amountToExtract);
		return resourcesToExtract;
	}

	@Override
	public void addFlatOccupierPower(double amount) {
		addFlatCalls.add(amount);
	}

	@Override
	public void multiplyOccupierPower(double amount) {
		multiplyCalls.add(amount);
	}
}
