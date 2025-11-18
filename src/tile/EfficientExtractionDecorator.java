package tile;

public class EfficientExtractionDecorator extends TileDecorator {
	public EfficientExtractionDecorator(MutableTile child) {
		super(child);
	}
	
	@Override
	public double extract(double amountToExtract) {
		return child.extract(amountToExtract) * 1.5;
	}
}
