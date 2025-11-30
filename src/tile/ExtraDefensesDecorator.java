package tile;

import spreader.Spreader;

public class ExtraDefensesDecorator extends TileDecorator {
	private static final TileDecorator.Applier APPLIER = ExtraDefensesDecorator::new;

	public ExtraDefensesDecorator(Tile child) {
		super(child);
	}
	
	@Override
	public Tile copy() {
		return new ExtraDefensesDecorator(child.copy());
	}
	
	@Override
	public void infect(double power, Spreader spreader) {
		child.infect(power - 5, spreader);
	}
	
	public static Applier getApplier() {
		return APPLIER;
	}
}
