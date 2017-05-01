package com.mimas.bowling;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mimas on 01.05.2017.
 */
class GameTest {
    @Test
    void tryRunTheBallTest() {
        Game game = Game.startNewGame(10);

        int[] input1 = new int[]{9,0,3,5,6,1,3,6,8,1,5,3,2,5,8,0,7,1,8,1};

        for (int pins: input1) {
            boolean attempt = game.tryRunTheBall(pins);
            assertTrue(attempt);
        }

        boolean lastattempt = game.tryRunTheBall(1);
        assertFalse(lastattempt);

        FrameResult[] scores = game.getResults();
        assertArrayEquals(new Integer[]{9,17,24,33,42,50,57,65,73,82},
                Arrays.stream(scores).map(FrameResult::getScore).toArray(size->new Integer[size]));

    }

    @Test
    void replayGame() {
        int[] input = new int[]{9,0,3,5,6,1,3,6,8,1,5,3,2,5,8,0,7,1,8,1};
        FrameResult[] results = Game.replayGame(input);

        assertArrayEquals(new Integer[]{9,17,24,33,42,50,57,65,73,82},
                Arrays.stream(results).map(FrameResult::getScore).toArray(size->new Integer[size]));

    }

    @Test
    void replayGameProtocol() {
        String[] input =new String[]{"X","3","/",
                "6","1","X","X","X","2","/","9","0","7","/","X","X","X"};

        FrameResult[] results = Game.replayGame(input);

        assertArrayEquals(new Integer[]{20,36,43,73,95,115,134,143,163,193},
                Arrays.stream(results).map(FrameResult::getScore).toArray(size->new Integer[size]));

    }

}