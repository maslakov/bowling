package com.mimas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public List<Frame> getFrames(){
        return Collections.unmodifiableList(Arrays.asList(frames));
    }

}
