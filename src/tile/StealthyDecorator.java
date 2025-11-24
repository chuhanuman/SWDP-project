package tile;

public class StealthyDecorator extends TileDecorator {
	private static final TileDecorator.Applier APPLIER = StealthyDecorator::new;

	public StealthyDecorator(Tile child) {
		super(child);
	}
	
	@Override
	public Tile copy() {
		return new StealthyDecorator(child.copy());
	}
	
	@Override
	public double getDifficulty() {
		return child.getDifficulty() - 100;
	}
	public static Applier getApplier() {
		return APPLIER;
	}
}
