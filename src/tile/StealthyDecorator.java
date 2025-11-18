package tile;

public class StealthyDecorator extends TileDecorator {
	public StealthyDecorator(MutableTile child) {
		super(child);
	}
	
	@Override
	public double getDifficulty() {
		return child.getDifficulty() - 100;
	}
}
