//Name: Shanshan Huang
//Class: 1400-012
//Program #: 18
//Due Date:Dec 1 2015
//
//Honor Pledge: On my honor as a student of the University of Nebraska at Omaha, I have neither given nor received unauthorized help on this homework assignment.
//
//NAME: SHANSHAN HUANG
//NUID: 550
//EMAIL:shuang03@unomaha.edu
//Colleagues: None
//Description:This program is using three methods to show up different generation cells live or die in the next generation. 

import java.util.Random;
import java.util.Scanner;

public class shuang03_Life {

    // the size of the grid (GRIDSIZE x GRIDSIZE)
    final private static int GRIDSIZE = 18;

    /********************************************************************************/
    public static void main (String args[]) {
        boolean[][] board = new boolean[GRIDSIZE][GRIDSIZE];
        char choice;
        int x = 1;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("Start with a (r)andom board, the (q)ueen bee shuttle or the (g)lider pattern? ");
            choice = sc.next()
                       .charAt(0);
        } while ( choice != 'r' && choice != 'q' && choice != 'g' );

        clearGrid(board);
        setup(board, choice);

        do {
            System.out.printf("Viewing generation #%d:\n\n", x++);
            displayGrid(board);
            genNextGrid(board);
            System.out.print("\n(q)uit or any other key + ENTER to continue: ");
            choice = sc.next()
                       .charAt(0);
        } while ( choice != 'q' );

    }

    /********************************************************************************/
    public static void setup (boolean[][] board, char which) {
        Random randomNumbers = new Random();

        clearGrid(board);

        if ( which == 'q' ) {
            // Set up the Queen Bee Shuttle pattern
            board[5][1] = true;
            board[5][2] = true;
            board[6][3] = true;
            board[7][4] = true;
            board[8][4] = true;
            board[9][4] = true;
            board[10][3] = true;
            board[11][2] = true;
            board[11][1] = true;
        } else if ( which == 'g' ) {
            // Set up a Glider
            board[17][0] = true;
            board[16][1] = true;
            board[15][1] = true;
            board[16][2] = true;
            board[17][2] = true;
        } else {
            // set up random
            for ( int row = 0; row < board.length; row++ ) {
                for ( int col = 0; col < board[row].length; col++ ) {
                    if ( randomNumbers.nextInt() % 2 == 0 )
                        board[row][col] = true;
                }
            }
        }

    }

    /********************************************************************************/
    public static void displayGrid (boolean[][] grid) {
        // Start printing the top row of numbers
        System.out.print("   ");
        for ( int x = 1; x <= grid.length; x++ ) {
            if ( (x / 10) != 0 )
                System.out.printf("%d", x / 10);
            else
                System.out.print(" ");
        }

        System.out.println();
        System.out.print("   ");

        for ( int x = 1; x <= grid.length; x++ ) {
            System.out.printf("%d", x % 10);
        }
        System.out.println();

        for ( int r = 0; r < grid.length; r++ ) {
            System.out.printf("%d", r + 1);
            if ( r + 1 < 10 )
                System.out.print("  ");
            else
                System.out.print(" ");
            for ( int c = 0; c < grid.length; c++ ) {
                if ( grid[r][c] == true )
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }

    /*******************************************************************************/

    // put the three methods you must write here and make sure to document
    // them!

    /**
     * This Method is called clearGrid.
     * It will simply reset the two-dimensional array that has been passed to it to all false values.
     *
     * @param r r identifies the row
     * @param c c identifies the column
     * @return none, void
     */
    public static void clearGrid (boolean[][] grid) {
        for ( int r = 0; r < grid.length; r++ ) {
            for ( int c = 0; c < grid[r].length; c++ ) {
                grid[r][c] = false;
            }
        }
    }

    /**
     * This method is called genNextGrid.
     * It will actually generate the next generation of the simulation.It use two-dementional array grid that is passed to it as the "current" generation.
     *
     * @param grid  two-dementional array that is passed to it as the "current" generation.
     * @param nextG two-dementional array that is generate 18x18 size rows*columns
     * @param r     r identifies the row
     * @param c     c identifies the column
     * @return none, void
     */
    public static void genNextGrid (boolean[][] grid) {
        boolean[][] nextG = new boolean[GRIDSIZE][GRIDSIZE];

        //These two for loops make sure the row and column are less than the GRIDSIZE. Then it switch i to 0 to 8 and distinguish whether nextG[r][c] is ture or false so that it could generate further generation.
        for ( int r =
              0; r < GRIDSIZE; r++ ) // r identifies the row and the for loop control the r is less than GRIDSIZE.
        {
            for ( int c =
                  0; c < GRIDSIZE; c++ )// c identifies the column and the for loop control the c is less than GRIDSIZE.
            {
                int i = countNeighbors(grid, r, c);

                switch ( i ) {
                    case 1:
                    case 0:
                        nextG[r][c] = false;
                        break;

                    case 2:
                        if ( grid[r][c] ) {
                            nextG[r][c] = true;
                        } else
                            nextG[r][c] = false;
                        break;

                    case 3:
                        nextG[r][c] = true;
                        break;

                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        nextG[r][c] = false;
                        break;
                }
            }
        }
        //These for loops identifies the rows and columns. It make sure that in the rows and columns, grid[r][c] is equal to nextG[r][c]
        for ( int r = 0; r < GRIDSIZE; r++ ) {
            for ( int c = 0; c < GRIDSIZE; c++ ) {
                grid[r][c] = nextG[r][c];
            }
        }
    }

    /**
     * This method is called counNeighbors.
     * It counts the number of neighbors in grid for an elements at position row and column.
     *
     * @param grid the board
     * @param row  the row index of the given point
     * @param col  the col index of the given point
     * @return int count
     */
    public static int countNeighbors (final boolean[][] grid, final int row, final int col) {
        int i;
        i = 0;

        //check the surrounding points in a 3*3 grid
        for ( int a = row - 1; a < row + 2; a++ ) {
            if ( a < 0 || a >= GRIDSIZE ) {
                continue;
            }

            for ( int b = col - 1; b < col + 2; b++ ) {
                if ( (a == row && b == col) || b < 0 || b >= GRIDSIZE ) {
                    continue;
                }
                if ( grid[a][b] ) {
                    i++;
                }
            }
        }
        return i;
    }
}
