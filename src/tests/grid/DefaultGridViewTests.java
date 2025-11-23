package tests.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grid.DefaultGridView;
import grid.DefaultTileGrid;
import grid.GridPos;
import grid.GridView;
import grid.TileGrid;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.extraction_strategy.InstantExtraction;
import spreader.extraction_strategy.SlowExtraction;
import spreader.spreading_strategy.CowardSpreading;
import tile.ConstTile;
import tile.DefaultTile;
import tile.Tile;

public class DefaultGridViewTests {
    private Spreader s1, s2, s3;
    private Tile t1, t2, t3;
    private TileGrid tg;
    private GridView gv;

    @BeforeEach
    public void setup() {
        this.s1 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        this.s2 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        this.s3 = new DefaultSpreader(new CowardSpreading(), new SlowExtraction(0, 0));

        this.t1 = new DefaultTile(1, 1, s1, 1);
        this.t2 = new DefaultTile(2, 2, s1, 2);
        this.t3 = new DefaultTile(3, 3, s1, 3);

        this.tg = new DefaultTileGrid.Builder(3, 6)
                      .setDefaultTile(new DefaultTile(0, 0))
                      .setTile(t1, new GridPos(0, 0))
                      .setTile(t2, new GridPos(0, 2))
                      .setTile(t3, new GridPos(1, 0))
                      .build();
        gv = new DefaultGridView(tg);
    }

    @Test
    public void getOccupiedTilesTest() {
        Iterator<ConstTile> it = gv.getOccupiedTiles(s1).iterator();

        assertEquals(it.next().getID(), t1.getID());
        assertEquals(it.next().getID(), t2.getID());
        assertEquals(it.next().getID(), t3.getID());
        assertFalse(it.hasNext());

        it = gv.getOccupiedTiles(s2).iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void getUnoccupiedTilesTest() {
        int count = 0;
        for (ConstTile t : gv.getUnoccupiedResourceTiles()) {
            count++;
            assertNotEquals(t.getID(), t1.getID());
            assertNotEquals(t.getID(), t2.getID());
            assertNotEquals(t.getID(), t3.getID());
        }
        assertEquals(15, count);
    }

    @Test public void getTilesInRangeTest() {
        int count = 0;
        for (ConstTile t : gv.getAllTilesInRange(tg.get(new GridPos(1, 0)), 4)) {
            count++;
            assertNotEquals(tg.get(new GridPos(1, 5)).getID(), t.getID());
        }
        assertEquals(15, count);
    }
}
