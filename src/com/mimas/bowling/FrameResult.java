package com.mimas.bowling;

import java.util.Arrays;
import java.util.Collections;

/**
 * Immutable representation of game frame result
 */
public class FrameResult {
    private int[] pins;
    private int score;

    FrameResult(int[] pins, int score)
    {
        this.pins = pins;
        this.score = score;
    }

    public int[] getPins() {
        return pins;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        String pinsString = String.join("|", Arrays.stream(pins)
                .mapToObj(String::valueOf)
                .toArray(String[]::new));
        return String.format("%s %n%s %n%s", pinsString,
                String.join("", Collections.nCopies(pinsString.length(), "-")),
                score);

    }
}
