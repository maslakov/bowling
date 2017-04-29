package com.mimas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mimas on 29.04.2017.
 */
public class Frame {
    public static final int MAX_FINAL_FRAME_CAPACITY = 3;
    public static final int MAX_REGULAR_FRAME_HITS = 10;

    private int[] hits;
    private int score;
    private int count;
    private FrameState state;

    public Frame(int capacity){
        hits = new int[capacity];
        state = FrameState.REGULAR;
        count = 0;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int value){
        score = value;
    }

    public FrameState getState(){
        return state;
    }

    public int[] getHits(){
        return Arrays.copyOf(hits,hits.length);
    }

    public boolean tryHit(int pins){
        if (count < hits.length) {
            if (count == 0) {
                if (pins == MAX_REGULAR_FRAME_HITS) {
                    state = FrameState.STRIKE;
                }
            } else {
                if (state == FrameState.STRIKE && hits.length != MAX_FINAL_FRAME_CAPACITY){
                    return false;
                }

                if (hits[count - 1] + pins == MAX_REGULAR_FRAME_HITS && pins > 0) {
                    state = FrameState.SPARE;
                }
            }
            hits[count] = pins;
            count++;
            return true;
        }
        return false;
    }

    @Override public String toString(){
        return String.format("%s / %s", String.join("|", Arrays.stream(hits)
                .mapToObj(String::valueOf)
                .toArray(String[]::new)),score);
    }
}
