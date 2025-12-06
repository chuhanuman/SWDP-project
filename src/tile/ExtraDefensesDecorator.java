package tile;

import spreader.Spreader;

/**
 * Concrete implementation that decorates a tile to make it harder to infect
 * Role(s): concrete decorator role in decorator pattern, concrete prototype in prototype pattern, receiver role in command pattern, the static field Applier is in the concrete command role in the command pattern
 */
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
