package com.mimas;

import java.util.Arrays;

/**
 * Object to represent one frame of the bowling game.
 * Can have limited number of attempts (2 to 3 normally).
 * Each attempt supposes to have a number of pins hit.
 * Frame has a value of game score for this frame, calculated at some point in time according external rules.
 */
public class Frame {
    public static final int MAX_FINAL_FRAME_CAPACITY = 3;
    public static final int MAX_REGULAR_FRAME_HITS = 10;

    private int[] attemptPinsCount;
    private int score;
    private int attemptIndex;
    private FrameState state;


    /**
     * Creates a new game frame with specified {@code capacity}
     * @param capacity - maximal number of attempts in the frame.
     */
    public Frame(int capacity){
        attemptPinsCount = new int[capacity];
        state = FrameState.NOTCOUNTABLE;
        attemptIndex = 0;
    }

    /**
     * Get actual score of the frame if it is calculated, otherwise zero.
     * @return Stored score value or zero
     */
    public int getScore(){
        return score;
    }

    /**
     * Store externally calculated score for the frame.
     * @param value - new value of the score
     */
    public void setScore(int value){
        score = value;
    }

    /**
     * Get current kind of the game, played by the time of invocation.
     * Possible values are {@code FrameState} enum values.
     * This class of the game defines how the score must be calculated.
     * @return current kind of the game, played by the time of invocation (STRIKE, SPARE or REGULAR one).
     */
    public FrameState getState(){
        return state;
    }

    /**
     * Get an array, which contains the number of pins knocked down on every attempt in the frame.
     * Default value is zero.
     * @return array of int values, representing the number of pins knocked down.
     */
    public int[] getAttemptPinsCount(){
        return Arrays.copyOf(attemptPinsCount, attemptPinsCount.length);
    }

    /**
     * Method with main capability of {@code Frame} object.
     * Checks if we can put an attempt to hit pins into this frame.
     * We can if this frame has a capacity. In this case we change the state of the frame and set the kind
     * of game just played, based on combination of pins knocked down in previous attempts.
     * @param pinsKnockedDown - number of pins knocked down by the attempt.
     * @return {@code true} - if the frame had a capacity to accept an attempt. Otherwise {@code false}
     */
    public boolean tryHit(int pinsKnockedDown){
        if (attemptIndex < attemptPinsCount.length) {

            if (state == FrameState.NOTCOUNTABLE){
                // no final state yet, collect balls
                attemptPinsCount[attemptIndex] = pinsKnockedDown;
                attemptIndex++;

                // try to find new final state
                for (FrameState frameState: FrameState.values()){
                    int backBallsCount = frameState.getBallsCount();
                    if (attemptIndex >= backBallsCount){
                        int sum = 0;
                        for (int i = attemptIndex-1; i >= attemptIndex-frameState.getBallsCount() ; i--) {
                            sum += attemptPinsCount[i];
                        }
                        if (frameState.getExpectedSumHit()>0){
                            if (sum > 0 && sum == frameState.getExpectedSumHit())
                            {
                                state = frameState;
                            }
                        }else{
                            if (attemptPinsCount.length == MAX_FINAL_FRAME_CAPACITY){
                                if (frameState.getBallsCount() == attemptIndex){
                                    // enough for score calculation
                                    state = frameState;
                                }
                            }else if (attemptIndex >= attemptPinsCount.length){
                                //frame filled
                                state =frameState;
                            }
                        }
                    }
                }

                return true;

            }else{
                // final frame state is set already, no need to add anything in this frame
                // apart from the case with last frame
                // when followed balls go into the same frame, after state is entered to be calculated
                if (attemptPinsCount.length == MAX_FINAL_FRAME_CAPACITY){
                    attemptPinsCount[attemptIndex] = pinsKnockedDown;
                    attemptIndex++;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Represents a frame of the game state.
     * Number of pins knocked down in each attempt in the frame separated by {@literal |}
     * followed by calculated score for the frame after {@literal /}
     * @return textual representation of the frame state in one-liner string
     */
    @Override public String toString(){
        return String.format("%s / %s", String.join("|", Arrays.stream(attemptPinsCount)
                .mapToObj(String::valueOf)
                .toArray(String[]::new)),score);
    }
}
