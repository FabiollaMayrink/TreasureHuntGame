package treasurehunt;

import java.io.IOException;
import java.util.ArrayList;

import java.util.InputMismatchException;

import java.util.List;

import java.util.Random;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 *
 * @author fabi
 *
 */
public class TreasureHuntGame {

    private static int playerFullname;

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {

        Scanner kBoard = new Scanner(System.in); //keyboard

        int qntPlayers = 0; //number of players
        myPlayers[] players = new myPlayers[1];

        System.out.println("Ahoy, matey, you´re welcome to Treasure Hunt Game, let's have fun!!!");

        while (qntPlayers == 0) {

            try {

                System.out.println("How many pirates we have in this treasure hunt?");

                qntPlayers = kBoard.nextInt();

                if (qntPlayers > 4 || qntPlayers < 2) {

                    qntPlayers = 0;

                    throw new InputMismatchException();

                }

            } catch (InputMismatchException ex) {

                kBoard.nextLine();

                System.out.println("Avast ye, the number of pirates has to be between 2 and 4!!!");

            }

            players = new myPlayers[qntPlayers];
        }

        int j = 0;

        int userAge = 0;

        do {

            players[j] = new myPlayers();

            kBoard.nextLine();

            String playerFullName;

            Boolean found = false;

            do {

                System.out.println("What is the full name of this pirate? ");

                playerFullName = kBoard.nextLine();

                //Pattern ptrn = Pattern.compile("(\\w)\\s+(\\w+)");
                Pattern ptrn = Pattern.compile("[a-zA-Z]+\\s+[a-zA-Z]+");
                Matcher matcher = ptrn.matcher(playerFullName);
                found = matcher.find();
                if (!found) {
                    System.out.println("I need your real full name. Just letters in this camp. Enter a valid name and second name.");

                }

            } while (!found);

            players[j].setPlayerFullname(playerFullName);

            System.out.println("How old are you pirate?");

            userAge = kBoard.nextInt();

            while (userAge < 12) {

                try {
                    if (userAge < 12) {

                        userAge = 0;

                        throw new InputMismatchException();

                    }

                } catch (InputMismatchException ex) {

                    kBoard.nextLine();

                    System.out.println("Invalide age, you have to be over 12 to play. Just digits in this camp. Enter a valid age.");

                }
            }

            players[j].setUserAge(userAge);

            int playerStatus;

            Random random = new Random();

            int[] digPoints = {4, 5, 6, 7}; //Each player has to receve digpoints at the start. Min of 4 and max of 7

            playerStatus = digPoints[random.nextInt(digPoints.length)] * (-5) + 100;

            players[j].setPlayerStatus(playerStatus);

            j++;
            System.out.println((playerFullName) + "'s DigPoints are " + (playerStatus) + "\n");

        } while (j < qntPlayers || userAge < 12);
        System.out.println("RULES:" + "\n"
                + "1) Each player may only select a square empty and each square can only be selected once (a used quared shows a X on the map)" + "\n"
                + "2) Each treasure found will add 20 DigPoints to the player" + "\n"
                + "3) When you select a square 1 DigPoint will be subtract from your points" + "\n"
                + "4) If a Player has no “Dig Points” left, then they miss their turn" + "\n"
                + "5) When all the treasure is found, then the game is over." + "\n"
                + "6) If no player has any “Dig Points” left, then the game is over. \n");

        myGame game = new myGame(players);

        do {

            game.printHeader();

            game.turnCounter();

            game.printBoard();

            game.getUserSquare();

        } while ((!game.gameOver()));

       // game.printBoard(); //print the board at the end of the game
        game.getWinners();

    }
}
