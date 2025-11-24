package tests.turn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import turn.extract.DefaultExtractStage;
import turn.extract.ExtractScheduler;
import turn.move.DefaultMoveStage;
import turn.move.MoveScheduler;
import turn.Turn;

public class TurnTests {
    
    @Test
    public void getSchedulerTests() {
        DefaultExtractStage s1 = new DefaultExtractStage();
        DefaultMoveStage s2 = new DefaultMoveStage();
        Turn turn = new Turn.Builder()
                           .nextStage(ExtractScheduler.class, s1)
                           .nextStage(MoveScheduler.class, s2)
                           .build();
        
        ExtractScheduler outS1 = turn.getTurnStageScheduler(ExtractScheduler.class);
        assertEquals(s1, outS1);
        MoveScheduler outS2 = turn.getTurnStageScheduler(MoveScheduler.class);
        assertEquals(s2, outS2);
    }

    // TODO: add execute tests
}
