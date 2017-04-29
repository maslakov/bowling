package com.mimas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mimas on 29.04.2017.
 */
class ScoreCalculatorTest {

    @org.junit.jupiter.api.Test
    @DisplayName("|5 / | 4 / 7| running in sequence one after one")
    void calculate() {

        ScoreCalculator calculator = new ScoreCalculator(2);
        calculator.putNext(5);
        List<Frame> frames = calculator.getFrames();

        assertEquals(2, frames.size());
        Frame frame = frames.get(0);
        assertEquals(FrameState.REGULAR,frame.getState());
        assertEquals(0,frame.getScore());
        assertEquals(2,frame.getHits().length);
        assertEquals(5,frame.getHits()[0]);
        assertEquals(0,frame.getHits()[1]);

        calculator.putNext(5);
        assertEquals(FrameState.SPARE,frame.getState());
        assertEquals(0,frame.getScore());
        assertEquals(2,frame.getHits().length);
        assertEquals(5,frame.getHits()[0]);
        assertEquals(5,frame.getHits()[1]);

        calculator.putNext(4);
        assertEquals(FrameState.SPARE,frame.getState());
        assertEquals(14,frame.getScore());
        assertEquals(2,frame.getHits().length);
        assertEquals(5,frame.getHits()[0]);
        assertEquals(5,frame.getHits()[1]);

        Frame lastFrame = frames.get(1);
        assertEquals(FrameState.REGULAR,lastFrame.getState());
        assertEquals(0,lastFrame.getScore());
        assertEquals(3,lastFrame.getHits().length);
        assertEquals(4,lastFrame.getHits()[0]);
        assertEquals(0,lastFrame.getHits()[1]);
        assertEquals(0,lastFrame.getHits()[2]);

        calculator.putNext(6);
        assertEquals(FrameState.SPARE,lastFrame.getState());
        assertEquals(0,lastFrame.getScore());
        assertEquals(3,lastFrame.getHits().length);
        assertEquals(4,lastFrame.getHits()[0]);
        assertEquals(6,lastFrame.getHits()[1]);
        assertEquals(0,lastFrame.getHits()[2]);

        calculator.putNext(7);
        assertEquals(FrameState.SPARE,lastFrame.getState());
        assertEquals(17,lastFrame.getScore());
        assertEquals(3,lastFrame.getHits().length);
        assertEquals(4,lastFrame.getHits()[0]);
        assertEquals(6,lastFrame.getHits()[1]);
        assertEquals(7,lastFrame.getHits()[2]);

        assertThrows(IllegalStateException.class,()->{calculator.putNext(1);});
    }

}