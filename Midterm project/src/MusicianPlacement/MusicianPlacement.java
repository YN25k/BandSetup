package MusicianPlacement;

import java.util.Scanner;
/**
 * A console application for managing the placement of musicians in a band setup.
 * It allows users to add and remove musicians from specified positions within rows,
 * each with weight constraints to ensure fair distribution.
 */
public class MusicianPlacement {
    private static Scanner keyboard = new Scanner(System.in);
    private static final int MAXROW = 10;
    private static final int MAXPOSITIONS =  8;
    private static final int MAXWEIGHTPERROW= 500;
    /**
     * Main method to run the Musician Placement application.
     * It initializes the band setup and processes user commands.
     * @param args Command line arguments (not used).
     */

    public static void main(String[] args) {
        int numRows;
        int numPositions;
        double[][] rowPositionArray;
        char[] positionCorrespondingArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        System.out.println("Welcome to the Band of the Hour\n" +
                "-------------------------------");
        System.out.print("Please enter number of rows               : ");
        numRows = keyboard.nextInt();
        while (numRows <= 0 || numRows > MAXROW) {
            System.out.print("ERROR: Out of range, try again            : ");
            numRows = keyboard.nextInt();
        }
        rowPositionArray = new double[numRows][];

        for (int i = 0; i < numRows; i++) {
            System.out.print("Please enter number of positions in row " + positionCorrespondingArray[i] + " : ");
            numPositions = keyboard.nextInt();
            while (numPositions <= 0 || numPositions > MAXPOSITIONS) {
                System.out.print("ERROR: Out of range, try again            : ");
                numPositions = keyboard.nextInt();
            }
            rowPositionArray[i] = new double[numPositions];
            for (int j = 0; j < numPositions; j++) {
                rowPositionArray[i][j] = 0.0;
            }
        }

        boolean exit = false;
        while (!exit) {
            System.out.print("\n(A)dd, (R)emove, (P)rint,          e(X)it : ");
            char choice = keyboard.next().charAt(0);
            switch (choice) {
                case 'P':
                case 'p':
                    printPositions(rowPositionArray, positionCorrespondingArray);
                    break;
                case 'A':
                case 'a':
                    addMusicianToPosition(rowPositionArray, positionCorrespondingArray);
                    break;
                case 'R':
                case 'r':
                    removeMusicianFromPosition(rowPositionArray, positionCorrespondingArray);
                    break;
                case 'X':
                case 'x':
                    exit = true;
                    break;
                default:
                    System.out.print("ERROR: Invalid option, try again          : ");
                    break;
            }
        }
    }
    /**
     * Prints the current positions of musicians in all rows, including total and average weight.
     * @param rowPositionArray Array containing the weight of musicians in each position.
     * @param positionCorrespondingArray Array mapping row indices to their corresponding letters.
     */
    private static void printPositions(double[][] rowPositionArray, char[] positionCorrespondingArray) {
        int maxPositions = 0;
        for (double[] row : rowPositionArray) {
            if (row.length > maxPositions) {
                maxPositions = row.length;
            }
        }

        for (int i = 0; i < rowPositionArray.length; i++) {
            double total = 0.0;
            System.out.print(positionCorrespondingArray[i] + ": ");
            for (int j = 0; j < maxPositions; j++) {
                if (j < rowPositionArray[i].length) {
                    total += rowPositionArray[i][j];
                    System.out.printf("%5.1f ", rowPositionArray[i][j]);
                } else {
                    System.out.print("      ");
                }
            }

            double average = (rowPositionArray[i].length > 0) ? total / rowPositionArray[i].length : 0.0;
            System.out.printf(" [ %5.1f, %5.1f ]%n", total, average);
        }
    }

    /**
     * Adds a musician to a specified position within a row, if the position is vacant and within weight limits.
     * @param rowPositionArray Array containing the weight of musicians in each position.
     * @param positionCorrespondingArray Array mapping row indices to their corresponding letters.
     */
    private static void addMusicianToPosition(double[][] rowPositionArray, char[] positionCorrespondingArray) {
        System.out.print("Please enter row letter                   : ");
        char rowLetter = keyboard.next().toUpperCase().charAt(0);
        int rowIndex = rowLetter - 'A';
        while (rowIndex < 0 || rowIndex >= rowPositionArray.length) {
            System.out.print("ERROR: Out of range, try again            : ");
            rowLetter = keyboard.next().toUpperCase().charAt(0);
            rowIndex = rowLetter - 'A';
        }

        System.out.print("Please enter position number (1 to " + rowPositionArray[rowIndex].length + ")     : ");
        int position = keyboard.nextInt() - 1;
        while (position < 0 || position >= rowPositionArray[rowIndex].length) {
            System.out.print("ERROR: Out of range, try again            : ");
            position = keyboard.nextInt() - 1;
        }

        if (rowPositionArray[rowIndex][position] != 0.0) {
            System.out.println("ERROR: There is already a musician there.");
            return; // Prevents overwriting an existing musician
        }

        System.out.print("Please enter weight (45.0 to 200.0)       : ");
        double weight = keyboard.nextDouble();
        while (weight < 45.0 || weight > 200.0) {
            System.out.print("ERROR: Out of range, try again            : ");
            weight = keyboard.nextDouble();
        }

        // Check if adding this weight exceeds the max average weight for the row
        double newTotalWeight = weight;
        for (double w : rowPositionArray[rowIndex]) {
            newTotalWeight += w;
        }
        if (newTotalWeight > MAXWEIGHTPERROW) {
            System.out.println("ERROR: That would exceed the average weight limit.");
            return;
        }

        rowPositionArray[rowIndex][position] = weight;
        System.out.println("****** Musician added.");
    }
    /**
     * Removes a musician from a specified position within a row, if the position is currently occupied.
     * @param rowPositionArray Array containing the weight of musicians in each position.
     * @param positionCorrespondingArray Array mapping row indices to their corresponding letters.
     */
    private static void removeMusicianFromPosition(double[][] rowPositionArray, char[] positionCorrespondingArray) {
        System.out.print("Please enter row letter                   : ");
        char rowLetter = keyboard.next().toUpperCase().charAt(0);
        int rowIndex = rowLetter - 'A';
        while (rowIndex < 0 || rowIndex >= rowPositionArray.length) {
            System.out.print("ERROR: Out of range, try again            : ");
            rowLetter = keyboard.next().toUpperCase().charAt(0);
            rowIndex = rowLetter - 'A';
        }

        System.out.print("Please enter position number (1 to " + rowPositionArray[rowIndex].length + ")     : ");
        int position = keyboard.nextInt() - 1;
        while (position < 0 || position >= rowPositionArray[rowIndex].length) {
            System.out.print("ERROR: Out of range, try again            : ");
            position = keyboard.nextInt() - 1;
        }

        if (rowPositionArray[rowIndex][position] == 0.0) {
            System.out.println("ERROR: That position is vacant.");
        } else {
            rowPositionArray[rowIndex][position] = 0.0;
            System.out.println("****** Musician removed.");
        }
    }
}
