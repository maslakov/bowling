package com.mimas;

/**
 * Created by Mimas on 30.04.2017.
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
    public Game startNewGame(int framesCount){
        return new Game(framesCount);
    }

    /**
     * Make next ball throw with {@code pinHit} pins knocked down in result
     * @param pinsHit - number of pins knocked down in the attempt
     * @return - the value indicating if the game finished
     */
    public boolean tryRunTheBall(int pinsHit){
        try {
            controller.runNextBall(pinsHit);
        } catch (IllegalStateException e) {
            // game over
            return false;
        }
        return true;
    }

    /**
     * Builds results score table for the game defined in text format with X and / signs
     * @param gameProtocol - in a text format: e.g. 1 4 6 / 5 / X 0 1...
     * @return
     */
    public static int[] replayGame(String[] gameProtocol){
        //validate string
        int framesCount = getFramesNumber(gameProtocol);

        int[] gamePins = convertProtocol(gameProtocol);
        GameController controller = new GameController(framesCount);
        for (int pins:gamePins) {
            controller.runNextBall(pins);
        }

        return null;
    }

    private static int[] convertProtocol(String[] gameProtocol) {
        return new int[0];
    }

    /**
     * Builds results score table for the game defined in numeric format.
     * Every number in input array represent the number of pins knocked down by the ball
     * @param gamePins - in a text format: e.g. 1 4 6 4 5 5 10 0 1...
     * @return
     */
    public static int[] replayGame(int[] gamePins){
        // validate input
        int framesCount = getFramesNumber(gamePins);

        GameController controller = new GameController(framesCount);
        for (int pins:gamePins) {
            controller.runNextBall(pins);
        }
        return null;
    }

    private static int getFramesNumber(int[] gamePins){
        return 0;
    }

    private static int getFramesNumber(String[] gameProtocol){
        return 0;
    }
}
