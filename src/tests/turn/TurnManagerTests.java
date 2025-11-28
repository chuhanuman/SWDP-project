package tests.turn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grid.DefaultGridView;
import grid.DefaultTileGrid;
import grid.GridPos;
import grid.TileGrid;
import simulation.TileGridSimulation;
import spreader.DefaultSpreader;
import spreader.Spreader;
import spreader.extraction_strategy.InstantExtraction;
import spreader.extraction_strategy.SlowExtraction;
import spreader.spreading_strategy.CowardSpreading;
import spreader.spreading_strategy.GreedySpreading;
import tile.DefaultTile;
import tile.Tile;
import turn.extract.DefaultExtractStage;
import turn.extract.ExtractScheduler;
import turn.move.DefaultMoveStage;
import turn.move.MoveScheduler;
import turn.TurnManager;
import turn.TurnStage;

public class TurnManagerTests {

    private Spreader s1, s2, s3;
    private Tile t1, t2, t3, resTile;
    private TileGrid tg;

    @BeforeEach
    public void setup() {
        this.s1 = new DefaultSpreader(new GreedySpreading(10), new InstantExtraction(1));
        this.s2 = new DefaultSpreader(new GreedySpreading(10), new InstantExtraction(1));
        this.s3 = new DefaultSpreader(new GreedySpreading(10), new InstantExtraction(1));

        this.t1 = new DefaultTile(1, 100, s1, 10);
        this.t2 = new DefaultTile(2, 200, s2, 20);
        this.t3 = new DefaultTile(3, 300, s3, 30);
        this.resTile = new DefaultTile(0, 1000);

        this.tg = new DefaultTileGrid.Builder(3, 6)
                      .setDefaultTile(new DefaultTile(0, 0))
                      .setTile(t1, new GridPos(0, 0))
                      .setTile(t2, new GridPos(0, 2))
                      .setTile(t3, new GridPos(1, 0))
                      .setTile(resTile, new GridPos(2, 3))
                      .build();
    }
    
    @Test
    public void simpleExtractTests() {
        TurnManager manager = new TurnManager.Builder()
                            .nextStage(new DefaultExtractStage())
                            .build();
        
        manager.executeTurn(new TileGridSimulation.View(new DefaultGridView(tg), List.of(s1, s2, s3)), tg);

        assertEquals(110, t1.getOccupierPower());
        assertEquals(0, t1.getResources());
        assertEquals(220, t2.getOccupierPower());
        assertEquals(0, t2.getResources());
        assertEquals(330, t3.getOccupierPower());
        assertEquals(0, t3.getResources());
    }

    @Test
    public void simpleOrderTests1() {
        TurnManager manager = new TurnManager.Builder()
                            .nextStage(new DefaultExtractStage())
                            .nextStage(new DefaultMoveStage())
                            .build();
        
        manager.executeTurn(new TileGridSimulation.View(new DefaultGridView(tg), List.of(s1, s2, s3)), tg);

        assertEquals(resTile.getOccupier(), s3);
        assertEquals(resTile.getOccupierPower(), 110);
    }

    @Test
    public void simpleOrderTests2() {
        TurnManager manager = new TurnManager.Builder()
                            .nextStage(new DefaultMoveStage())
                            .nextStage(new DefaultExtractStage())
                            .build();
        
        manager.executeTurn(new TileGridSimulation.View(new DefaultGridView(tg), List.of(s1, s2, s3)), tg);

        assertEquals(resTile.getOccupier(), s3);
        assertEquals(resTile.getOccupierPower(), 1000 + 5./6);
    }

    // TODO: add more tests
}
