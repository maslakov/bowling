package com.mimas;

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
        calculator = new ScoreCalculator(table.getFrames());
    }

    public GameController(int framesCount) {
        table = new ScoreTable(framesCount);
        calculator = new ScoreCalculator(table.getFrames());
    }

    public int[] getScores(){
        int size = table.getFrames().size();
        int[] scores = new int[size];
        for (int i=0 ; i < size; i++){
            scores[i] = table.getFrames().get(i).getScore();
        }
        return scores;
    }

    public void runNextBall(int pins){
        List<Frame> frames = table.getFrames();
        Frame frameToPutPins = frames.get(playingFrameIndex);
        while(!frameToPutPins.tryHit(pins)) {
            if (playingFrameIndex+1 >= frames.size())
                throw new IllegalStateException("max number of frames achieved");
            playingFrameIndex++;
            frameToPutPins = frames.get(playingFrameIndex);
        }
        calculator.addToCalculation(pins);
    }

    public int[] Replay(String[] balls){
        return null;
    }
}
