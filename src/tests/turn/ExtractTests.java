package tests.turn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grid.DefaultTileGrid;
import grid.GridPos;
import grid.TileGrid;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.extraction_strategy.InstantExtraction;
import spreader.spreading_strategy.CowardSpreading;
import tile.ConstTile;
import tile.DefaultTile;
import tile.Tile;
import turn.extract.DefaultExtractStage;

public class ExtractTests {

    private Spreader s1;
    private Tile t1, t2, t3;
    private TileGrid tg;
    private DefaultExtractStage es;

    @BeforeEach
    public void setup() {
        this.s1 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        // this.s2 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        // this.s3 = new DefaultSpreader(new CowardSpreading(), new SlowExtraction(0, 0));

        this.t1 = new DefaultTile(1, 100, s1, 1);
        this.t2 = new DefaultTile(2, 2, s1, 2);
        this.t3 = new DefaultTile(3, 3, s1, 3);

        this.tg = new DefaultTileGrid.Builder(3, 6)
                      .setDefaultTile(new DefaultTile(0, 0))
                      .setTile(t1, new GridPos(0, 0))
                      .setTile(t2, new GridPos(0, 2))
                      .setTile(t3, new GridPos(1, 0))
                      .build();

        es = new DefaultExtractStage();
    }

    @Test
    public void basicTests() {
        es.queueExtract(new ConstTile(t1), 50, 1);
        es.queueExtract(new ConstTile(t2), 50, 1);
        es.execute(tg);

        assertEquals(50.0, t1.getResources());
        assertEquals(51, t1.getOccupierPower());
        assertEquals(0, t2.getResources());
        assertEquals(4, t2.getOccupierPower());

        es.execute(tg);

        assertEquals(50.0, t1.getResources());
        assertEquals(51, t1.getOccupierPower());
        assertEquals(0, t2.getResources());
        assertEquals(4, t2.getOccupierPower());

        es.queueExtract(new ConstTile(t1), 50, 1);
        es.queueExtract(new ConstTile(t2), 50, 1);
        es.execute(tg);

        assertEquals(0, t1.getResources());
        assertEquals(101, t1.getOccupierPower());
        assertEquals(0, t2.getResources());
        assertEquals(4, t2.getOccupierPower());
    }

    // TODO: maybe more tests
}