package prng;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PRNG {
	private static PRNG instance = null;

	/**
	 * Returns the PRNG instance or null if unseeded
	 * @return the PRNG instance or null if unseeded
	 */
	public static PRNG getInstance() {
		return instance;
	}
	
	/**
	 * Initializes the PRNG with the given seed
	 */
	public static void seed(long seed) {
		instance = new PRNG(seed);
	}
	
	private Random generator;
	
	private PRNG(long seed) {
		generator = new Random(seed);
	}
	
	/**
	 * Randomly splits amount into n pieces 
	 * @param amount the total amount to split
	 * @param n number of pieces to split into
	 * @return list with the amount in each piece
	 */
	public List<Double> split(double amount, int n) {
		List<Double> positions = new ArrayList<Double>(n + 1);
		positions.add(0.);
		for (int i = 0; i < n - 1; i++) {
			positions.add(generator.nextDouble(0, amount));
		}
		positions.add(amount);
		
		positions.sort(Comparator.naturalOrder());
		
		List<Double> pieces = new ArrayList<Double>(n);
		for (int i = 1; i < n + 1; i++) {
			pieces.add(positions.get(i) - positions.get(i - 1));
		}
		
		return pieces;
	}

	public boolean chance(double probability) {
		return generator.nextDouble() < probability;
	}

	public <T> T choice(List<T> items) {
		return items.get(generator.nextInt(items.size()));
	}
}
