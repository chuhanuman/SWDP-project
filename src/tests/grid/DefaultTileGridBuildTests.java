package tests.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import grid.*;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.extraction_strategy.InstantExtraction;
import spreader.extraction_strategy.SlowExtraction;
import spreader.spreading_strategy.CowardSpreading;
import tile.ConstTile;
import tile.DefaultTile;
import tile.Tile;
import tile.ViewableTile;

public class DefaultTileGridBuildTests {

    @Test
    public void BasicTests() {
        Spreader s = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        TileGrid tg = new DefaultTileGrid.Builder(3, 3)
                      .setDefaultTile(new DefaultTile(1, 2, s, 3))
                      .build();

        Set<UUID> idSet = new HashSet<>();
        for (ViewableTile t : tg.getAllTiles()) {
            assertTrue(!idSet.contains(t.getID()));
            idSet.add(t.getID());

            checkTile(t, 1, 2, s, 3);
        }
        assertEquals(9, idSet.size());
    }

    @Test
    public void setTests() {
        Spreader s1 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        Spreader s2 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        Spreader s3 = new DefaultSpreader(new CowardSpreading(), new SlowExtraction(0, 0));

        TileGrid tg = new DefaultTileGrid.Builder(3, 6)
                      .setDefaultTile(new DefaultTile(0, 0))
                      .setTile(new DefaultTile(1, 1, s1, 1), new GridPos(0, 0))
                      .setTile(new DefaultTile(2, 2, s2, 2), new GridPos(0, 2))
                      .setTile(new DefaultTile(3, 3, s3, 3), new GridPos(1, 0))
                      .build();

        Set<UUID> idSet = new HashSet<>();
        for (ViewableTile t : tg.getAllTiles()) {
            assertTrue(!idSet.contains(t.getID()));
            idSet.add(t.getID());
            
            GridPos pos = tg.getPos(t);
            switch (pos) {
                case GridPos(int r, int c) when r == 0 && c == 0 -> 
                    checkTile(t, 1, 1, s1, 1);
                case GridPos(int r, int c) when r == 0 && c == 2 -> 
                    checkTile(t, 2, 2, s2, 2);
                case GridPos(int r, int c) when r == 1 && c == 0 -> 
                    checkTile(t, 3, 3, s3, 3);
                default -> 
                    checkTile(t, 0, 0, null, 0);
            }
        }
        assertEquals(18, idSet.size());
    }

    public static void checkTile(ViewableTile t, double difficulty, double resources, Spreader occupier, double occupierPower) {
        assertEquals(difficulty, t.getDifficulty());
        assertEquals(resources, t.getResources());
        assertEquals(occupier, t.getOccupier());
        assertEquals(occupierPower, t.getOccupierPower());
    }
}
