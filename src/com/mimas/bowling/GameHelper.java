package com.mimas.bowling;

/**
 * Helper methods to process replay sequences
 */
public class GameHelper {

    public static int MAX_NUMBER_FRAMES = 10;
    public static int MAX_FRAME_PINS = 10;


    public static int validateAndGetFramesNumber(int[] gamePins){
        int framesNumber = 0;
        int index = 0;

        if (gamePins.length < 2){throw new IllegalArgumentException("gamePins length");}

        while(index < gamePins.length){

            if (gamePins[index]>MAX_FRAME_PINS){
                throw new IllegalArgumentException("all values must be less than 10");
            }

            if (gamePins[index]==MAX_FRAME_PINS){
                if (framesNumber< MAX_NUMBER_FRAMES)
                {
                    framesNumber++;
                }
                index++;
                continue;
            }


            if (index+1 < gamePins.length){
                if (gamePins[index]+gamePins[index+1] <= MAX_FRAME_PINS){
                    //all good
                    if (framesNumber< MAX_NUMBER_FRAMES)
                    {
                        framesNumber++;
                    }
                    index+=2;
                    continue;
                }else throw new IllegalArgumentException("wrong number of pins in attempt within frame. Max sum of all attempts is 10");
            }else{
                if (framesNumber != MAX_NUMBER_FRAMES){
                    throw new IllegalArgumentException("wrong number of attempts in frame");
                }else{
                    index++;
                }
            }

        }
        return framesNumber;
    }


    public static int[] convertProtocol(String[] gameProtocol) {
        int[] pins = new int[gameProtocol.length];
        for (int i = 0; i < gameProtocol.length; i++) {
            String pinsString = gameProtocol[i];

            if (pinsString.equalsIgnoreCase("/")){
                if (i == 0) throw new IllegalArgumentException("Spare cannot be first in a series");
                if (pins[i-1] == 10) throw new IllegalArgumentException("Spare cannot be after strike");
                if (gameProtocol[i-1].equalsIgnoreCase("/")) throw new IllegalArgumentException("Spare cannot be after spare");
                pins[i]= MAX_FRAME_PINS - pins[i-1]; // SPARE
                continue;
            }
            if (pinsString.equalsIgnoreCase("X")){
                pins[i]= MAX_FRAME_PINS; // STRIKE
                continue;
            }

            int pinsValue = Integer.parseInt(pinsString);
            if (pinsValue > MAX_FRAME_PINS) throw new IllegalArgumentException("all values must be less than 10");
            pins[i] = pinsValue;
        }

        return pins;
    }

}
