package com.mimas.bowling;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Hold a list of ordered {@code Frame} objects.
 * Is a storage for all rounds data like numbers of pins knocked down and calculated score.
 */
public class ScoreTable {
    private final Frame[] frames;

    ScoreTable(int framesCount){
        frames = new Frame[framesCount];
        for (int i = 0; i < framesCount - 1; i++) {
            frames[i] = new Frame(2);
        }
        frames[framesCount-1] = new Frame(3);
    }

    public FrameState getFrameState(int i){
        if (i< frames.length) {
            return frames[i].getState();
        }
        return null;
    }

    public List<FrameResult> getFrameResults(){
        return Collections.unmodifiableList(Arrays.stream(frames).map(f->f.toFrameResult()).collect(toList()));
    }

    public FrameResult getFrameResult(int frameIndex){
        if (frameIndex< frames.length){
            return frames[frameIndex].toFrameResult();
        }
        return null;
    }

    public int getSize(){
        return frames.length;
    }

    public boolean trySavePinsInFrame(int frameIndex, int pinsCount){
        if (frameIndex < frames.length){
            Frame frameToPutPins = frames[frameIndex];
            return frameToPutPins.tryHit(pinsCount);
        }
        throw new ArrayIndexOutOfBoundsException("wrong frame index");
    }

    public void setFrameScore(int frameIndex, int score) {
        if (frameIndex< frames.length){
            frames[frameIndex].setScore(score);
        }
    }
}
