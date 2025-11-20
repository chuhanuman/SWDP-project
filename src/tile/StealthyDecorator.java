package tile;

public class StealthyDecorator extends TileDecorator {
	public StealthyDecorator(MutableTile child) {
		super(child);
	}
	
	@Override
	public MutableTile copy() {
		return new StealthyDecorator(child.copy());
	}
	
	@Override
	public double getDifficulty() {
		return child.getDifficulty() - 100;
	}
}
