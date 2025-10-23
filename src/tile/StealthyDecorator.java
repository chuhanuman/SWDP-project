package tile;

public class StealthyDecorator extends TileDecorator {
	public StealthyDecorator(Tile child) {
		super(child);
	}
	
	@Override
	public double getDifficulty() {
		return child.getDifficulty() - 100;
	}
}
