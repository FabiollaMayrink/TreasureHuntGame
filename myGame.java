/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasurehunt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author gabri
 */
public class myGame {

    boolean treasureFound = false;
    
    private int turn;

    private int playerTurn;

    private int[][] board;

    private myPlayers[] players;

    public myGame(myPlayers[] myPlayers) {

        turn = 1;

        playerTurn = 0;

        players = myPlayers;

        setUpBoard();

    }

    public void setUpBoard() {

        int rows;

        int columns;

        board = new int[10][10];

        for (rows = 0; rows < board.length; rows++) {

            for (columns = 0; columns < board[rows].length; columns++) {

                board[rows][columns] = -1;

            }

        }

    }

    public void printHeader() {

        System.out.println("GAMEBOARD \n");

    }

    public void turnCounter() {

        System.out.println("This is turn: " + this.turn + "\n");

        if (this.players[playerTurn].getPlayerStatus() <= 0) {
            System.out.println("Argh, Captain, me shovel has broken!");
            moveToNextPlayer();

        }

        System.out.println("Argh..Pirate " + this.players[playerTurn].getPlayerFullname() + "it be your turn to dig for me treasure.");
        System.out.println("You have:" + this.players[playerTurn].getPlayerStatus() + " DigPoints.");

        this.turn++;

    }

    public void printBoard() {

        //go through the rows and columns
        int rows = 10;

        int columns = 10;

        //same count loop as board set up!
        System.out.println("   A B C D E F G H I J");

        for (rows = 0; rows < board.length; rows++) {

            if (rows < 9) {
                System.out.print(" " + (rows + 1) + "|");
            } else {
                System.out.print((rows + 1) + "|");
            }

            for (columns = 0; columns < board[rows].length; columns++) {

                if (board[rows][columns] == -1) {

                    //empty slot
                    System.out.print("_|");

                } else if (board[rows][columns] == 0) {

                    //should be an  O here
                    System.out.print("O|");

                } else {

                    //must be a 1 --- so X
                    System.out.print("O|");

                }

            }

            System.out.println("");

        }

    }

    public boolean gameOver() {

        
        boolean isGameOver = true;

        for (int i = 0; i < players.length; i++) {
            if (players[i].getPlayerStatus() > 0) {
                isGameOver = false;
            }
        }
           
        

        if (isGameOver || treasureFound) {
            System.out.println("GAME OVER");
        }

        return isGameOver || treasureFound;

    }

    public void getUserSquare() throws IOException {

        int userRow, userColumn;

        do {

            Scanner myScanner = new Scanner(System.in);
            String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
            //String userRowString;
            String userColumnString;
            do {

                System.out.println("Please enter column");

                //userRowString = myScanner.next();
                userColumnString = myScanner.next();

                //if (!userRowString.matches("[a-jA-J]")) {
                if (!userColumnString.matches("[a-jA-J]")) {
                    System.out.println("Invalid row. Just letters between A and J in this camp. Enter a valid column.");
                    
                }

            //} while (!userRowString.matches("[a-jA-J]"));
            } while (!userColumnString.matches("[a-jA-J]"));

            
            
            //userRow = -1;
            userColumn = -1;
            for (int i = 0; i < letters.length; i++) {
                //if (letters[i].equalsIgnoreCase(userRowString)) {
                if (letters[i].equalsIgnoreCase(userColumnString)) {
                    //userRow = i;
                    userColumn = i;
                    break;
                }
            }

            try {
                System.out.println("Please enter row");
                //userColumn = myScanner.nextInt() - 1;
                userRow = myScanner.nextInt() - 1;
            } catch (java.util.InputMismatchException e) {

                System.out.println("Invalide. Enter a valid row.");

                return;
            }

            //if (board[userRow][userColumn] != -1) {
            if (board[userColumn][userRow] != -1) {

                System.out.println("That square has been used. Pick again");

            }

            players[playerTurn].setPlayerStatus(players[playerTurn].getPlayerStatus() - 1);
            

            String treasure = readFile("C:\\Users\\gabri\\Documents\\NetBeansProjects\\TreasureHuntGame\\PiratePete.txt");

            //if ((userRow + 1) == Character.getNumericValue(treasure.charAt(0)) && (userColumn + 1) == Character.getNumericValue(treasure.charAt(0))) {
            if ((userColumn + 1)== Character.getNumericValue(treasure.charAt(0)) && (userRow + 1) == Character.getNumericValue(treasure.charAt(0))) {

                treasureFound = true;
                
                System.out.println("Yo-ho-ho and a bottle of rum!!!n/ "
                        + "I found me some pieces of eight!!! 20 DigPoints for you!");

                players[playerTurn].setPlayerStatus(players[playerTurn].getPlayerStatus() + 20);
                System.out.println("Now you have:" + this.players[playerTurn].getPlayerStatus() + " DigPoints. \n");
                return;

           }
            else{
                System.out.println("Walk the plank! There be no treasure here!");
                System.out.println("Now you have:" + this.players[playerTurn].getPlayerStatus() + " DigPoints. \n");
                moveToNextPlayer();
            }

            

        } while (board[userRow][userColumn] != -1);

        if ((turn % 2) == 1) {

            board[userRow][userColumn] = 0;

        } else {

            board[userRow][userColumn] = 1;

        }

    }

    public int getTurn() {

        return turn;

    }

    public String readFile(String filePath) throws FileNotFoundException, IOException {

        String treasure = "";
        // pass the path to the file as a parameter 
        FileReader fr
                = new FileReader(filePath);

        int i;
        while ((i = fr.read()) != -1) {
            treasure += (char) i;
        }
        return treasure;
    }

    private void moveToNextPlayer() {
        if (this.playerTurn == this.players.length - 1) {
            this.playerTurn = 0;
        } else {

            this.playerTurn++;

        }
    }

    public void getWinners() {
        ArrayList<String> l = new ArrayList<String>();
        int maxPoints = -1; //Assuming it is not possible to have negative points

        for (int i = 0; i < players.length; i++) {
            int points = players[i].getPlayerStatus();
            if (points >= maxPoints) {
                if (points > maxPoints) {
                    l.clear(); //Clear the return list, since a new "best score" was found
                }
                maxPoints = points;
                l.add(players[i].getPlayerFullname());
            }
        }
        String names = String.join(", ", l);
        System.out.println("Shiver me Timbers, me hearties, sure hasn’t" + names + "won the game. Keelhaul the rest of them!");
    }

}
