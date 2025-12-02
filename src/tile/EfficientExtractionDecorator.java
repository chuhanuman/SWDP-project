package tile;

/**
 * Concrete implementation that decorates a tile to improve its extraction efficiency
 * Role(s): concrete decorator role in decorator pattern, concrete prototype in prototype pattern, the static field Applier is in the concrete command role in the command pattern
 */
public class EfficientExtractionDecorator extends TileDecorator {
	private static final TileDecorator.Applier APPLIER = EfficientExtractionDecorator::new;

	public EfficientExtractionDecorator(Tile child) {
		super(child);
	}
	
	@Override
	public Tile copy() {
		return new EfficientExtractionDecorator(child.copy());
	}
	
	@Override
	public double extract(double amountToExtract) {
		return child.extract(amountToExtract) * 1.5;
	}
	
	public static Applier getApplier() {
		return APPLIER;
	}
}
