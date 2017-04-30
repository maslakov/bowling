package com.mimas;

import java.util.ArrayList;
import java.util.List;

/**
 * Object which knows how to calculate total score for frames based on the rules.
 * It takes into account knocked down pins, scores, supposed to be added due to the frame result type
 * and pins from following frames.
 * Assumes we have an object which holds all the data and which can be used as data source
 * and to which we can write score after it is calculated.
 */
public class ScoreCalculator {

    private final List<Frame> frames;
    private final List<Integer> bufferedPins;
    private int calculatingFrameIndex = 0;

    ScoreCalculator(List<Frame> frames){
        bufferedPins = new ArrayList<>(3);
        this.frames = frames;
    }

    private int getFollowersScore(){
        return bufferedPins.stream().mapToInt(Integer::intValue).sum();
    }

    public void addToCalculation(int pins){

        bufferedPins.add(pins);

        while(bufferedPins.size() > 1 && calculatingFrameIndex < frames.size()){
            Frame calculatedFrame = frames.get(calculatingFrameIndex);
            FrameState state = calculatedFrame.getState();
            int currentScore = 0;

            if (calculatingFrameIndex >0)
            {
                currentScore = frames.get(calculatingFrameIndex-1).getScore();
            }

            if (bufferedPins.size() == state.getBallsCount()+state.getFollowersNumber()){
                int reward = state.getScore();
                for (int i = 0; i <state.getBallsCount();i++) {
                    if (state.getScore() == 0){
                        // no extra reward - use pins as score for the frame
                        reward+= bufferedPins.get(0);
                    }
                    // delete past items from the buffer: their score already calculated or will be replaced with a const value.
                    bufferedPins.remove(0);
                }
                calculatedFrame.setScore(currentScore + reward + getFollowersScore());
                calculatingFrameIndex++;

            }else{
                // need more input
                break;
            }
        }

    }

}
