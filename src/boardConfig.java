/**
 * @author: Anthony Palumbo
 * date: 11-14-16
 * description: class for the chess board configuration
 */

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.Collection;
import java.util.ArrayList;

public class boardConfig {
    /** Constant characters used in the board. */
    public final static char EMPTY = '#';
    public final static char QUEEN = 'Q';

    /** Class variables for the chess board. */
    private int dim;
    private char[][] board;
    private int currRow;
    private int currCol;

    /**
     * Getter for the dimensions of the board.
     * @return: the size of one side of the board
     */
    public int getDim() {
        return this.dim;
    }

    /**
     * Getter for the current index of the row.
     * @return: the current index of the row
     */
    public int getRow() {
        return this.currRow;
    }

    /**
     * Getter for the current index of the column.
     * @return: the current index of the column
     */
    public int getCol() {
        return this.currCol;
    }

    /**
     * Getter for the board at a specific location.
     * @param r: the row
     * @param c: the column
     * @return: the value of the board at that row and column
     */
    public char getBoard(int r, int c) {
        return this.board[r][c];
    }

    /**
     * Constructor for the chess board.
     * @param filename: the name of the input file to read
     * @throws FileNotFoundException: throws this exception if the file does not exist
     */
    public boardConfig(String filename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        this.dim = in.nextInt();
        this.currRow = 0;
        this.currCol = 0;

        this.board = new char[this.dim][this.dim];
        String line;
        for(int r = 0; r < this.dim; r++) {
            line = in.nextLine().trim();
            for(int c = 0; c < this.dim*2; c += 2) {
                this.board[r][c] = line.charAt(c);
            }
        }

        in.close();
    }

    /**
     * Copy constructor for the chess board.
     * @param copy: the version of the board to be copied
     */
    public boardConfig(boardConfig copy) {
        this.dim = copy.getDim();
        this.currRow = copy.getRow();
        this.currCol = copy.getCol();
        for(int r = 0; r < this.dim; r++) {
            for(int c = 0; c < this.dim; c++) {
                this.board[r][c] = copy.getBoard(r, c);
            }
        }
    }

    /**
     * Determines if the cursor can move to the next location or not.
     * @return: true if the cursor can move, false if it cannot move
     */
    public boolean canMove() {
        if((this.currRow == this.dim-1)&&(this.currCol == this.dim-1))
            return false;
        return true;
    }

    /**
     * Moves the cursor if possible.
     * @return: true if the cursor was moved, false if it cannot be moved
     */
    public boolean moveCursor() {
        if(!canMove())
            return false;
        else {
            if(this.currCol != this.dim-1) {
                this.currCol++;
                return true;
            }
            else {
                this.currCol = 0;
                if(this.currRow == this.dim-1)
                    return false;
                else {
                    this.currRow++;
                    return true;
                }
            }
        }
    }

    /**
     * Gets the next possible successors.
     * @return: an arraylist of the next successors
     */
    public Collection<boardConfig> getSuccessors() {
        //TODO add pruning
        ArrayList<boardConfig> successors = new ArrayList<>();
        if(!canMove())
            return successors;
        else if(isValid()) {
            boardConfig b = new boardConfig(this);
            successors.add(b);
            return successors;
        }
        else {
            boardConfig b = new boardConfig(this);
            b.moveCursor();
            successors.add(b);
            b = new boardConfig(this);
            b.moveCursor();
            b.board[b.getRow()][b.getCol()] = QUEEN;
            successors.add(b);
            return successors;
        }
    }

    /**
     * Checks if the current configuration could be a possible solution.
     * @return: true if it could be a solution, false if not
     */
    public boolean isValid() {
        // Counts the number of queens in each row and column
        int ctr;
        for(int r = 0; r < this.dim; r++) {
            ctr = 0;
            for(int c = 0; c < this.dim; c++) {
                if(this.board[r][c] == QUEEN)
                    ctr++;
            }
            if(ctr > 1)
                return false;
        }
        for(int c = 0; c < this.dim; c++) {
            ctr = 0;
            for(int r = 0; r < this.dim; r++) {
                if(this.board[r][c] == QUEEN)
                    ctr++;
            }
            if(ctr > 1)
                return false;
        }

        //Checks diagonals
        int row, col;
        for(int r = 0; r < this.dim; r++) {
            for(int c = 0; c < this.dim; c++) {
                if(this.board[r][c] == QUEEN) {
                    row = r;
                    col = c;
                    while((row < this.dim)&&(col < this.dim)) {
                        if(this.board[row][col] == QUEEN)
                            return false;
                        else {
                            row++;
                            col++;
                        }
                    }
                    row = r;
                    col = c;
                    while((row < this.dim)&&(col >= 0)) {
                        if(this.board[row][col] == QUEEN)
                            return false;
                        else {
                            row++;
                            col--;
                        }
                    }
                    row = r;
                    col = c;
                    while((row >= 0)&&(col < this.dim)) {
                        if(this.board[row][col] == QUEEN)
                            return false;
                        else {
                            row--;
                            col++;
                        }
                    }
                    row = r;
                    col = c;
                    while((row >= 0)&&(col >= 0)) {
                        if(this.board[row][col] == QUEEN)
                            return false;
                        else {
                            row--;
                            col--;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if the current configuration is a solution.
     * @return: true if it is a solution, false if not
     */
    public boolean isGoal() {
        if(!isValid())
            return false;
        else {
            // Counts the number of queens in each row and column
            int ctr;
            for(int r = 0; r < this.dim; r++) {
                ctr = 0;
                for(int c = 0; c < this.dim; c++) {
                    if(this.board[r][c] == QUEEN)
                        ctr++;
                }
                if(ctr != 1)
                    return false;
            }
            for(int c = 0; c < this.dim; c++) {
                ctr = 0;
                for(int r = 0; r < this.dim; r++) {
                    if(this.board[r][c] == QUEEN)
                        ctr++;
                }
                if(ctr != 1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Creates a string representation of the board.
     * @return: string of the layout of the board
     */
    public String toString() {
        String output = this.dim + "x" + this.dim + "\n";
        for(int r = 0; r < this.dim; r++) {
            for(int c = 0; c < this.dim; c++) {
                output += this.board[r][c] + " ";
            }
            output += "\n";
        }
        return output;
    }
}
