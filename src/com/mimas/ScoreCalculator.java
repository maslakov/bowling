package com.mimas;

import java.util.*;

/**
 * Created by Mimas on 29.04.2017.
 */
public class ScoreCalculator {

    private final Frame[] frames;
    private final List<Integer> bufferedPins;

    private int playingFrameIndex = 0;
    private int calculatingFrameIndex = 0;

    public ScoreCalculator(int framesCount) {
        frames = new Frame[framesCount];
        for (int i = 0; i < framesCount - 1; i++) {
            frames[i] = new Frame(2);
        }
        frames[framesCount-1] = new Frame(3);
        bufferedPins = new ArrayList<>(3);
    }

    public List<Frame> getFrames(){
        return Collections.unmodifiableList(Arrays.asList(frames));
    }

    public void putNext(int pins){

        Frame frameToPutPins = frames[playingFrameIndex];

        while(!frameToPutPins.tryHit(pins)) {
            if (playingFrameIndex+1 >= frames.length)
                throw new IllegalStateException("max number of frames achieved");
            playingFrameIndex++;
            frameToPutPins = frames[playingFrameIndex];
        }

        bufferedPins.add(pins);

        Frame calculatedFrame = frames[calculatingFrameIndex];
        FrameState state = calculatedFrame.getState();

        if (state == FrameState.STRIKE){
            if (bufferedPins.size() == 3){
                calculatedFrame.setScore(getBufferedScore());
                calculatingFrameIndex++;
                bufferedPins.remove(0);
            }
        }else if (state == FrameState.SPARE){
            if (bufferedPins.size() == 3){
                calculatedFrame.setScore(getBufferedScore());
                calculatingFrameIndex++;
                bufferedPins.remove(0);
                bufferedPins.remove(0);
            }
        }else if (state == FrameState.REGULAR){
            if (bufferedPins.size() == 2){
                calculatedFrame.setScore(getBufferedScore());
                calculatingFrameIndex++;
                bufferedPins.clear();
            }
        }
    }

    private int getBufferedScore(){
        return bufferedPins.stream().mapToInt(Integer::intValue).sum();
    }
}
