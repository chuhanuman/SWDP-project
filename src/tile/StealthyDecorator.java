package tile;

public class StealthyDecorator extends TileDecorator {
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
}
