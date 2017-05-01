package com.mimas.bowling;

import java.util.*;

/**
 * Object which drives the game, it holds results table and results calculator.
 * */
public class GameController {

    private final ScoreCalculator calculator;
    private final ScoreTable table;
    private int playingFrameIndex = 0;

    public GameController(ScoreTable table) {
        this.table = table;
        calculator = new ScoreCalculator(table);
    }

    public GameController(int framesCount) {
        table = new ScoreTable(framesCount);
        calculator = new ScoreCalculator(table);
    }

    public int[] getScores(){
        int size = table.getSize();
        int[] scores = new int[size];
        for (int i=0 ; i < size; i++){
            scores[i] = table.getFrameResults().get(i).getScore();
        }
        return scores;
    }

    public FrameResult[] getResults(){
        return table.getFrameResults().toArray(new FrameResult[0]);
    }

    /**
     * Stores results of one game attempt in appropriate frame.
     * @param pins - knocked downs pins number
     */
    public void runNextBall(int pins){
        if (pins> GameHelper.MAX_FRAME_PINS || pins<0)
        {
            throw new IllegalArgumentException("must be a number between 0 and 10");
        }
        while(!table.trySavePinsInFrame(playingFrameIndex,pins)) {
            if (playingFrameIndex+1 >= table.getSize())
                throw new IllegalStateException("max number of frames achieved");
            playingFrameIndex++;
        }
        calculator.addToCalculation(pins);
    }
}
