package tests.turn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import tile.ConstTile;
import tile.DefaultTile;
import tile.Tile;
import turn.move.DefaultMoveStage;

public class MoveTests {

    private Spreader s1, s2, s3;
    private Tile t1, t2, t3;
    private TileGrid tg;
    private DefaultMoveStage ms;

    @BeforeEach
    public void setup() {
        this.s1 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        this.s2 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        this.s3 = new DefaultSpreader(new CowardSpreading(), new SlowExtraction(0, 0));

        this.t1 = new DefaultTile(1, 100, s1, 100);
        this.t2 = new DefaultTile(2, 2, s2, 200);
        this.t3 = new DefaultTile(3, 3, s3, 300);

        this.tg = new DefaultTileGrid.Builder(3, 6)
                      .setDefaultTile(new DefaultTile(0, 0))
                      .setTile(t1, new GridPos(0, 0))
                      .setTile(t2, new GridPos(0, 2))
                      .setTile(t3, new GridPos(1, 0))
                      .build();

        ms = new DefaultMoveStage();
    }

    @Test
    public void basicTests() {
        Tile toTile = tg.get(new GridPos(0, 1));
        ms.queueMove(new ConstTile(t1), new ConstTile(toTile), 100);
        ms.queueMove(new ConstTile(t2), new ConstTile(toTile), 150);
        ms.executeStage(tg);

        assertEquals(0, t1.getOccupierPower());
        assertNull(t1.getOccupier());
        assertEquals(50, t2.getOccupierPower());
        assertEquals(s2, t2.getOccupier());
        assertEquals(50, toTile.getOccupierPower());
        assertEquals(s2, toTile.getOccupier());

        ms.executeStage(tg);

        assertEquals(0, t1.getOccupierPower());
        assertNull(t1.getOccupier());
        assertEquals(50, t2.getOccupierPower());
        assertEquals(s2, t2.getOccupier());
        assertEquals(50, toTile.getOccupierPower());
        assertEquals(s2, toTile.getOccupier());

        ms.queueMove(new ConstTile(t1), new ConstTile(toTile), 100);
        ms.queueMove(new ConstTile(t2), new ConstTile(toTile), 150);
        ms.executeStage(tg);

        assertEquals(0, t1.getOccupierPower());
        assertNull(t1.getOccupier());
        assertEquals(0, t2.getOccupierPower());
        assertNull(t2.getOccupier());
        assertEquals(50, toTile.getOccupierPower()); // TODO: should be 100?
        assertEquals(s2, toTile.getOccupier());
    }

    // TODO: maybe more tests
}