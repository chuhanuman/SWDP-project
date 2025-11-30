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
import spreader.extraction_strategy.SlowExtraction;
import spreader.spreading_strategy.CowardSpreading;
import tile.ConstTile;
import tile.DefaultTile;
import tile.ViewableTile;
import tile.Tile;
import turn.move.InfectAction;

public class InfectActionTests {

    private Spreader s1, s2, s3;
    private Tile t1, t2, t3;
    private TileGrid tg;

    @BeforeEach
    public void setup() {
        this.s1 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        this.s2 = new DefaultSpreader(new CowardSpreading(), new InstantExtraction(0));
        this.s3 = new DefaultSpreader(new CowardSpreading(), new SlowExtraction(0, 0));

        this.t1 = new DefaultTile(1, 100, s1, 1);
        this.t2 = new DefaultTile(2, 2, s1, 2);
        this.t3 = new DefaultTile(3, 3, s1, 3);

        this.tg = new DefaultTileGrid.Builder(3, 6)
                      .setDefaultTile(new DefaultTile(100, 100))
                      .setTile(t1, new GridPos(0, 0))
                      .setTile(t2, new GridPos(0, 2))
                      .setTile(t3, new GridPos(1, 0))
                      .build();
    }

    @Test
    public void basicTests() {
    	ViewableTile tile = tg.getTile(new GridPos(0, 1)); // default tile specs
        InfectAction action = new InfectAction(tile);
        action.addSpreader(s1, 50);
        action.addSpreader(s1, 10);
        action.addSpreader(s1, 40);
        action.execute(tg);

        assertEquals(tile.getOccupier(), s1);
        assertEquals(tile.getOccupierPower(), 10);
        assertEquals(tile.getDifficulty(), 90);
    }

    @Test
    public void fightTests() {
        ViewableTile tile = tg.getTile(new GridPos(0, 1)); // default tile specs
        InfectAction action = new InfectAction(tile);
        action.addSpreader(s1, 200);
        action.addSpreader(s2, 100);
        action.addSpreader(s3, 99);
        action.execute(tg);

        assertEquals(tile.getOccupier(), s1);
        assertEquals(tile.getOccupierPower(), 10);
        assertEquals(tile.getDifficulty(), 90);
    }
}
