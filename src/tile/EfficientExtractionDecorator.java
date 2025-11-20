package tile;

public class EfficientExtractionDecorator extends TileDecorator {
	public EfficientExtractionDecorator(MutableTile child) {
		super(child);
	}
	
	@Override
	public MutableTile copy() {
		return new EfficientExtractionDecorator(child.copy());
	}
	
	@Override
	public double extract(double amountToExtract) {
		return child.extract(amountToExtract) * 1.5;
	}
}
