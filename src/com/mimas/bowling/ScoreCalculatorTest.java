package com.mimas.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Basic test set for step by step executions
 */
class ScoreCalculatorTest {

    @Test
    @DisplayName("|5 / | 4 / 7| running in sequence one after one")
    void framesSpareCalculationAndDetailsTest() {

        ScoreTable table = new ScoreTable(2);
        GameController calculator = new GameController(table);
        calculator.runNextBall(5);
        List<FrameResult> frames = table.getFrameResults();

        assertEquals(2, frames.size());
        FrameResult frame = frames.get(0);
        assertEquals(FrameState.NOTCOUNTABLE, table.getFrameState(0));
        assertEquals(0, frame.getScore());
        assertEquals(2, frame.getPins().length);
        assertEquals(5, frame.getPins()[0]);
        assertEquals(0, frame.getPins()[1]);

        calculator.runNextBall(5);
        frame = table.getFrameResults().get(0);
        assertEquals(FrameState.SPARE, table.getFrameState(0));
        assertEquals(0, frame.getScore());
        assertEquals(2, frame.getPins().length);
        assertEquals(5, frame.getPins()[0]);
        assertEquals(5, frame.getPins()[1]);

        calculator.runNextBall(4);
        frame = table.getFrameResults().get(0);
        assertEquals(FrameState.SPARE, table.getFrameState(0));
        assertEquals(14, frame.getScore());
        assertEquals(2, frame.getPins().length);
        assertEquals(5, frame.getPins()[0]);
        assertEquals(5, frame.getPins()[1]);

        FrameResult lastFrame = table.getFrameResults().get(1);
        assertEquals(FrameState.NOTCOUNTABLE, table.getFrameState(1));
        assertEquals(0, lastFrame.getScore());
        assertEquals(3, lastFrame.getPins().length);
        assertEquals(4, lastFrame.getPins()[0]);
        assertEquals(0, lastFrame.getPins()[1]);
        assertEquals(0, lastFrame.getPins()[2]);

        calculator.runNextBall(6);
        lastFrame = table.getFrameResults().get(1);
        assertEquals(FrameState.SPARE, table.getFrameState(1));
        assertEquals(0, lastFrame.getScore());
        assertEquals(3, lastFrame.getPins().length);
        assertEquals(4, lastFrame.getPins()[0]);
        assertEquals(6, lastFrame.getPins()[1]);
        assertEquals(0, lastFrame.getPins()[2]);

        calculator.runNextBall(7);
        lastFrame = table.getFrameResults().get(1);
        assertEquals(FrameState.SPARE, table.getFrameState(1));
        assertEquals(31, lastFrame.getScore());
        assertEquals(3, lastFrame.getPins().length);
        assertEquals(4, lastFrame.getPins()[0]);
        assertEquals(6, lastFrame.getPins()[1]);
        assertEquals(7, lastFrame.getPins()[2]);

        assertThrows(IllegalStateException.class, () -> {
            calculator.runNextBall(1);
        });
    }

    @Test
    @DisplayName("Score calculation check from task sample.")
    void scoreTableTest() {
        ScoreTable table = new ScoreTable(10);
        GameController gameController = new GameController(table);
        gameController.runNextBall(1);
        gameController.runNextBall(4);

        gameController.runNextBall(4);
        gameController.runNextBall(5);

        gameController.runNextBall(6);
        gameController.runNextBall(4);

        gameController.runNextBall(5);
        gameController.runNextBall(5);

        gameController.runNextBall(10);

        gameController.runNextBall(0);
        gameController.runNextBall(1);

        gameController.runNextBall(7);
        gameController.runNextBall(3);

        gameController.runNextBall(6);
        gameController.runNextBall(4);

        gameController.runNextBall(10);

        gameController.runNextBall(2);
        gameController.runNextBall(8);
        gameController.runNextBall(6);

        List<FrameResult> frames = table.getFrameResults();

        assertEquals(10, frames.size());

        assertEquals(5, frames.get(0).getScore());
        assertEquals(14, frames.get(1).getScore());
        assertEquals(29, frames.get(2).getScore());
        assertEquals(49, frames.get(3).getScore());
        assertEquals(60, frames.get(4).getScore());
        assertEquals(61, frames.get(5).getScore());
        assertEquals(77, frames.get(6).getScore());
        assertEquals(97, frames.get(7).getScore());
        assertEquals(117, frames.get(8).getScore());
        assertEquals(133, frames.get(9).getScore());
    }

    @Test
    @DisplayName("Task example with strikes step by step. Score calculation check.")
    void scoreTableStrikesExtremeTest() {
        GameController gameController = new GameController(10);
        for (int i = 0; i < 12; i++) {
            gameController.runNextBall(10);
        }

        int[] scoreTable = gameController.getScores();

        for (int i = 1; i <= 10; i++) {
            assertEquals(30 * i, scoreTable[i - 1]);
        }
    }

    @Test
    @DisplayName("Score calculation for regular cases without strike/spare.")
    void regularScoreTableTest() {
        GameController gameController = new GameController(4);
        gameController.runNextBall(1);
        gameController.runNextBall(3);

        gameController.runNextBall(0);
        gameController.runNextBall(0);

        gameController.runNextBall(4);
        gameController.runNextBall(5);

        gameController.runNextBall(1);
        gameController.runNextBall(2);

        int[] scores = gameController.getScores();

        assertEquals(4, scores.length);

        assertEquals(4, scores[0]);
        assertEquals(4, scores[1]);
        assertEquals(13, scores[2]);
        assertEquals(16, scores[3]);
    }

    @Test
    @DisplayName("Score test for frames with spare")
    void spareHitsScoreTableTest(){
        GameController gameController = new GameController(10);

       int[] input1 = new int[]{9,0,3,7,6,1,3,7,8,1,5,5,0,10,8,0,7,3,8,2,8};

        for (int pins: input1) {
            gameController.runNextBall(pins);
        }
        int[] scores = gameController.getScores();
        assertArrayEquals(new int[]{9,25,32,50,59,69,87,95,113,131}, scores);
    }

    @Test
    @DisplayName("Score test for frames without spare and strikes")
    void regularHitsScoreTableTest(){
        GameController gameController = new GameController(10);

        int[] input1 = new int[]{9,0,3,5,6,1,3,6,8,1,5,3,2,5,8,0,7,1,8,1};

        for (int pins: input1) {
            gameController.runNextBall(pins);
        }
        int[] scores = gameController.getScores();
        assertArrayEquals(new int[]{9,17,24,33,42,50,57,65,73,82}, scores);
    }

    @Test
    @DisplayName("Score test for frames with strikes")
    void strikesHitsScoreTableTest(){
        GameController gameController = new GameController(10);

        int[] input1 = new int[]{10,3,7,6,1,10,10,10,2,8,9,0,7,3,10,10,10};

        for (int pins: input1) {
            gameController.runNextBall(pins);
        }
        int[] scores = gameController.getScores();
        assertArrayEquals(new int[]{20,36,43,73,95,115,134,143,163,193}, scores);
    }

}