package tile;

import spreader.Spreader;

public class ExtraDefensesDecorator extends TileDecorator {
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
}
