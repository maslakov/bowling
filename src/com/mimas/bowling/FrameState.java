package com.mimas.bowling;

/**
 * Enum to describe a type of the game in one specific frame.
 */
public enum FrameState {
    NOTCOUNTABLE(0, 0, 0, 0),
    /**
     * Less than maximal number of pins knocked down in the frame.
     */
    REGULAR(0, 2, 0, 0),
    /**
     * All pins knocked down from second attempt
     */
    SPARE(10, 2, 1, 10),
    /**
     * All pins knocked down from the first attempt
     */
    STRIKE(10, 1, 2, 10);

    /**
     * score which will be taken instead of last {@code ballsCount} hit results for total calculation
     */
    private int score;
    /**
     * number of already thrown balls to be checked to match the state
     * e.g. for STRIKE 1 ball should be thrown and hit 10 pins
     * for SPARE 2 balls should be thrown and sum of these two hits in one frame must be 10.
     *
     * That can be extended, like frames have 4 attempts and new XSPARE allows to reach 10 in 3 attempts
     * and you will get 500 score for that
     */
    private int ballsCount;
    /**
     * number of following balls after the state was entered, which is required for total score calculation
     */
    private int followersNumber;
    /**
     * Expected number of pins knocked down by last {@code ballsCount} balls to match the state and enter it
     */
    private int expectedSumHit;

    /**
     * Creates new game frame type.
     * @param score - score, which will substitute the score for balls, led to this state
     * @param ballsCount - number of balls to be thrown to enter this state
     * @param followers - number of following balls required to calculate the total score for this state,
     * once frame entered the state.
     */
    FrameState(int score, int ballsCount, int followers, int expectedSum){

        this.score = score;
        this.ballsCount = ballsCount;
        this.followersNumber = followers;
        this.expectedSumHit = expectedSum;
    }

    public int getScore() {
        return score;
    }

    public int getBallsCount() {
        return ballsCount;
    }

    public int getFollowersNumber() {
        return followersNumber;
    }

    public int getExpectedSumHit() {
        return expectedSumHit;
    }
}
