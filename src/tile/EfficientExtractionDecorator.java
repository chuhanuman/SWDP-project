package tile;

public class EfficientExtractionDecorator extends TileDecorator {
	public EfficientExtractionDecorator(Tile child) {
		super(child);
	}
	
	@Override
	public double extract(double amountToExtract) {
		return child.extract(amountToExtract) * 1.5;
	}
}
