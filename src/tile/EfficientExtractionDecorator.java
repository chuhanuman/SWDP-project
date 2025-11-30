package tile;

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
