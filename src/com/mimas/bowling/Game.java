package com.mimas.bowling;

/**
 * Game object with capability to play step by step or replay whole game based on balls records.
 */
public class Game {
    private final GameController controller;

    private Game(int frames){
        controller = new GameController(frames);
    }

    /**
     * Start new game with defined number of frames.
     * @param framesCount - number of frames in bowling game
     * @return a game object
     */
    public static Game startNewGame(int framesCount){
        return new Game(framesCount);
    }

    /**
     * Make next ball throw with {@code pinHit} pins knocked down in result
     * @param pinsHit - number of pins knocked down in the attempt
     * @return - the value indicating if the game finished
     */
    public boolean tryRunTheBall(int pinsHit){
        try
        {
            controller.runNextBall(pinsHit);
        } catch (Exception e) {
            // game over
            return false;
        }
        return true;
    }

    /**
     * Returns the results of this game.
     * @return array with records about pins knocked down and score
     */
    public FrameResult[] getResults(){
        return controller.getResults();
    }

    /**
     * Builds results score table for the game defined in text format with X and / signs
     * @param gameProtocol - in a text format: e.g. 1 4 6 / 5 / X 0 1...
     * @return array with records about pins knocked down and score
     */
    public static FrameResult[] replayGame(String[] gameProtocol){

        int[] gamePins = GameHelper.convertProtocol(gameProtocol);
        int framesCount = GameHelper.validateAndGetFramesNumber(gamePins);

        GameController controller = new GameController(framesCount);
        for (int pins:gamePins) {
            controller.runNextBall(pins);
        }

        return controller.getResults();
    }


    /**
     * Builds results score table for the game defined in numeric format.
     * Every number in input array represent the number of pins knocked down by the ball
     * @param gamePins - in a text format: e.g. 1 4 6 4 5 5 10 0 1...
     * @return array with records about pins knocked down and score
     */
    public static FrameResult[] replayGame(int[] gamePins){

        int framesCount = GameHelper.validateAndGetFramesNumber(gamePins);

        GameController controller = new GameController(framesCount);
        for (int pins:gamePins) {
            controller.runNextBall(pins);
        }
        return controller.getResults();
    }

}
