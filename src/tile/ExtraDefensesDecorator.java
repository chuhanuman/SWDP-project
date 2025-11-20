package tile;

import spreader.Spreader;

public class ExtraDefensesDecorator extends TileDecorator {
	public ExtraDefensesDecorator(MutableTile child) {
		super(child);
	}
	
	@Override
	public MutableTile copy() {
		return new ExtraDefensesDecorator(child.copy());
	}
	
	@Override
	public void infect(double power, Spreader spreader) {
		child.infect(power - 5, spreader);
	}
}
