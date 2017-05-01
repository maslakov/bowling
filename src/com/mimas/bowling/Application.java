package com.mimas.bowling;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        for (String arg :
                args) {
            System.out.print(arg);
            System.out.print(" ");
        }

        if (args.length == 1){
            // TODO: read from file
        }else if (args.length > 1){
            // replay with numbers from command line
            FrameResult[] result = Game.replayGame(args);

            printResults(result);
        }else{
            System.out.println("Enter knocked down pins number one by one and press enter.");
            Scanner scan = new Scanner(System.in);
            Game game = Game.startNewGame(10);
            boolean gameOver =false;
            do {
                int in = scan.nextInt();
                gameOver = !game.tryRunTheBall(in);
                FrameResult[] result  = game.getResults();
                printResults(result);
            }while (!gameOver);

            System.out.println("GAME OVER.");
        }

    }

    private static void printResults(FrameResult[] result) {
        for (FrameResult frame : result) {
            System.out.println(frame.toString());
            System.out.println();
        }
    }
}
