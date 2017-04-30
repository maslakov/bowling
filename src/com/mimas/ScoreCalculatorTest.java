package com.mimas;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Mimas on 29.04.2017.
 */
class ScoreCalculatorTest {

    @org.junit.jupiter.api.Test
    @DisplayName("|5 / | 4 / 7| running in sequence one after one")
    void framesSpareCalculationAndDetailsTest() {

        ScoreTable table = new ScoreTable(2);
        GameController calculator = new GameController(table);
        calculator.runNextBall(5);
        List<Frame> frames = table.getFrames();

        assertEquals(2, frames.size());
        Frame frame = frames.get(0);
        assertEquals(FrameState.NOTCOUNTABLE, frame.getState());
        assertEquals(0, frame.getScore());
        assertEquals(2, frame.getAttemptPinsCount().length);
        assertEquals(5, frame.getAttemptPinsCount()[0]);
        assertEquals(0, frame.getAttemptPinsCount()[1]);

        calculator.runNextBall(5);
        assertEquals(FrameState.SPARE, frame.getState());
        assertEquals(0, frame.getScore());
        assertEquals(2, frame.getAttemptPinsCount().length);
        assertEquals(5, frame.getAttemptPinsCount()[0]);
        assertEquals(5, frame.getAttemptPinsCount()[1]);

        calculator.runNextBall(4);
        assertEquals(FrameState.SPARE, frame.getState());
        assertEquals(14, frame.getScore());
        assertEquals(2, frame.getAttemptPinsCount().length);
        assertEquals(5, frame.getAttemptPinsCount()[0]);
        assertEquals(5, frame.getAttemptPinsCount()[1]);

        Frame lastFrame = frames.get(1);
        assertEquals(FrameState.NOTCOUNTABLE, lastFrame.getState());
        assertEquals(0, lastFrame.getScore());
        assertEquals(3, lastFrame.getAttemptPinsCount().length);
        assertEquals(4, lastFrame.getAttemptPinsCount()[0]);
        assertEquals(0, lastFrame.getAttemptPinsCount()[1]);
        assertEquals(0, lastFrame.getAttemptPinsCount()[2]);

        calculator.runNextBall(6);
        assertEquals(FrameState.SPARE, lastFrame.getState());
        assertEquals(0, lastFrame.getScore());
        assertEquals(3, lastFrame.getAttemptPinsCount().length);
        assertEquals(4, lastFrame.getAttemptPinsCount()[0]);
        assertEquals(6, lastFrame.getAttemptPinsCount()[1]);
        assertEquals(0, lastFrame.getAttemptPinsCount()[2]);

        calculator.runNextBall(7);
        assertEquals(FrameState.SPARE, lastFrame.getState());
        assertEquals(31, lastFrame.getScore());
        assertEquals(3, lastFrame.getAttemptPinsCount().length);
        assertEquals(4, lastFrame.getAttemptPinsCount()[0]);
        assertEquals(6, lastFrame.getAttemptPinsCount()[1]);
        assertEquals(7, lastFrame.getAttemptPinsCount()[2]);

        assertThrows(IllegalStateException.class, () -> {
            calculator.runNextBall(1);
        });
    }

    @org.junit.jupiter.api.Test
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

        List<Frame> frames = table.getFrames();

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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

}