package tile;

public class ExtraDefensesDecorator extends TileDecorator {
	public ExtraDefensesDecorator(Tile child) {
		super(child);
	}
	
	@Override
	public double infect(double power) {
		return child.infect(power - 5);
	}
}
