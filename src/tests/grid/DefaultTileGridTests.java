package tests.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grid.DefaultTileGrid;
import grid.GridPos;
import grid.TileGrid;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.extraction_strategy.InstantExtraction;
import spreader.extraction_strategy.SlowExtraction;
import spreader.spreading_strategy.CowardSpreading;
import tile.DefaultTile;
import tile.StealthyDecorator;
import tile.Tile;

public class DefaultTileGridTests {

    private Spreader s1, s2, s3;
    private Tile t1, t2, t3;
    private TileGrid tg;

    @BeforeEach
    public void setup() {
        this.s1 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        this.s2 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        this.s3 = new DefaultSpreader(new CowardSpreading(), new SlowExtraction(0, 0));

        this.t1 = new DefaultTile(1, 1, s1, 1);
        this.t2 = new DefaultTile(2, 2, s2, 2);
        this.t3 = new DefaultTile(3, 3, s3, 3);

        this.tg = new DefaultTileGrid.Builder(3, 6)
                      .setDefaultTile(new DefaultTile(0, 0))
                      .setTile(t1, new GridPos(0, 0))
                      .setTile(t2, new GridPos(0, 2))
                      .setTile(t3, new GridPos(1, 0))
                      .build();
    }

    @Test
    public void getTests() {
        DefaultTileGridBuildTests.checkTile(tg.get(new GridPos(0, 0)), 1, 1, s1, 1);
        DefaultTileGridBuildTests.checkTile(tg.get(new GridPos(0, 2)), 2, 2, s2, 2);
        DefaultTileGridBuildTests.checkTile(tg.get(new GridPos(1, 0)), 3, 3, s3, 3);
    }

    @Test
    public void getPosTests() {
        assertEquals(new GridPos(0, 0), tg.getPos(t1));
        assertEquals(new GridPos(0, 2), tg.getPos(t2));
        assertEquals(new GridPos(1, 0), tg.getPos(t3));
    }

    @Test
    public void decorateTileTests() {
        tg.decorateTile(new GridPos(0, 0), StealthyDecorator.APPLIER);
        Tile dTile = tg.get(new GridPos(0, 0));
        assertEquals(t1.getID(), dTile.getID());
        assert(dTile instanceof StealthyDecorator);
        assert(!(t1 instanceof StealthyDecorator));
    }
}
