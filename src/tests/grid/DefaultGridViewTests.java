package tests.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grid.ConstGrid;
import grid.DefaultTileGrid;
import grid.GridPos;
import grid.GridView;
import grid.TileGrid;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.extraction_strategy.InstantExtraction;
import spreader.extraction_strategy.SlowExtraction;
import spreader.spreading_strategy.CowardSpreading;
import tile.ViewableTile;
import tile.DefaultTile;
import tile.StealthyDecorator;
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
                      .setDefaultTile(new DefaultTile(0, 1))
                      .setTile(t1, new GridPos(0, 0))
                      .setTile(t2, new GridPos(0, 2))
                      .setTile(t3, new GridPos(1, 0))
                      .build();
        gv = new ConstGrid(tg);
    }

    @Test
    public void getOccupiedTilesTest() {
    	List<UUID> expectedIDs = List.of(t1.getID(), t2.getID(), t3.getID());
        List<UUID> actualIDs = new ArrayList<>();
        for (ViewableTile occupiedTile : gv.getOccupiedTiles(s1)) {
        	actualIDs.add(occupiedTile.getID());
        }
        assertEquals(expectedIDs.size(), actualIDs.size());
        assertTrue(actualIDs.containsAll(expectedIDs));
        
        assertFalse(gv.getOccupiedTiles(s2).iterator().hasNext());
    }

    @Test
    public void getEasiestUnoccupiedResourceTileTest() {
        for (int i = 0; i < 7; i++) {
        	ViewableTile t = gv.getEasiestUnoccupiedResourceTile();
        	assertEquals(null, t.getOccupier());
        	assertEquals(0, t.getDifficulty());
        	assertEquals(1, t.getResources());
            assertNotEquals(t.getID(), t1.getID());
            assertNotEquals(t.getID(), t2.getID());
            assertNotEquals(t.getID(), t3.getID());
            
            tg.extractTile(t.getID(), t.getResources(), i);
        }
        
        for (int i = 0; i < 7; i++) {
        	ViewableTile t = gv.getEasiestUnoccupiedResourceTile();
        	assertEquals(null, t.getOccupier());
        	assertEquals(0, t.getDifficulty());
        	assertEquals(1, t.getResources());
            assertNotEquals(t.getID(), t1.getID());
            assertNotEquals(t.getID(), t2.getID());
            assertNotEquals(t.getID(), t3.getID());
            
            tg.infectTile(t.getID(), 10000, s1);
        }
        
        ViewableTile t = gv.getEasiestUnoccupiedResourceTile();
        tg.decorateTile(gv.getPos(t), StealthyDecorator.getApplier());
        t = gv.getEasiestUnoccupiedResourceTile();
        assertEquals(null, t.getOccupier());
    	assertEquals(-100, t.getDifficulty());
    	assertEquals(1, t.getResources());
        assertNotEquals(t.getID(), t1.getID());
        assertNotEquals(t.getID(), t2.getID());
        assertNotEquals(t.getID(), t3.getID());
        
        tg.infectTile(t.getID(), 10000, s1);
        assertEquals(null, gv.getEasiestUnoccupiedResourceTile());
    }

    @Test public void getTilesInRangeTest() {
        int count = 0;
        for (ViewableTile t : gv.getAllTilesInRange(tg.getTile(new GridPos(1, 0)), 4)) {
            count++;
            assertNotEquals(gv.getTile(new GridPos(1, 5)).getID(), t.getID());
        }
        assertEquals(15, count);
    }
}
