package queen_project.queen_backtracker;

import queen_project.QueenProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * @author Anthony Palumbo
 * date: 11-14-16
 * description: class for the chess board configuration
 */
public class BoardConfiguration {

    /** Constant characters used in the board. */
    private final static char EMPTY = '#';
    final static char QUEEN = 'Q';

    /** Class variables for the chess board. */
    private int dim;
    private char[][] board;
    private int currRow;
    private int currCol;

    /**
     * Constructor for the chess board.
     * @param filename: the name of the input file to read
     * @throws FileNotFoundException: throws this exception if the file does not exist
     */
    public BoardConfiguration(String filename, boolean resource) throws FileNotFoundException {
        Scanner in;
        if (resource) {
            InputStream input = QueenProject.class.getClassLoader().getResourceAsStream("empty.txt");
            in = new Scanner(input);
        } else {
            in = new Scanner(new File(filename));
        }
        this.dim = in.nextInt();
        this.currRow = 0;
        this.currCol = 0;

        this.board = new char[this.dim][this.dim];
        String line;
        in.nextLine();
        int c;
        for (int r = 0; r < this.dim; r++) {
            line = in.nextLine().replaceAll("\\s+", "");
            c = 0;
            for (char ch: line.toCharArray()) {
                if (ch == 'Q') {
                    this.board[r][c] = QUEEN;
                } else {
                    this.board[r][c] = EMPTY;
                }
                c++;
            }
        }

        in.close();
    }

    /**
     * Copy constructor for the chess board.
     * @param copy: the version of the board to be copied
     */
    private BoardConfiguration(BoardConfiguration copy) {
        this.dim = copy.dim;
        this.currRow = copy.currRow;
        this.currCol = copy.currCol;
        this.board = new char[this.dim][this.dim];
        for (int r = 0; r < this.dim; r++) {
            System.arraycopy(copy.board[r], 0, this.board[r], 0, copy.dim);
        }
    }

    /**
     * Getter for private variable dim.
     * @return the value of dim
     */
    int getDim() {
        return this.dim;
    }

    /**
     * Getter for the board
     * @param r: the row index
     * @param c: the column index
     * @return the value at the board
     */
    char getBoard(int r, int c) {
        return this.board[r][c];
    }

    /**
     * Determines if the cursor can move to the next location or not.
     * @return true if the cursor can move, false if it cannot move
     */
    private boolean canMove() {
        return !((this.currRow == this.dim-1)&&(this.currCol == this.dim-1));
    }

    /**
     * Moves the cursor if possible.
     * @return true if the cursor was moved, false if it cannot be moved
     */
    private boolean moveCursor() {
        if (!canMove()) {
            return false;
        } else {
            if (this.currCol != this.dim-1) {
                this.currCol++;
                return true;
            } else {
                this.currCol = 0;
                if (this.currRow == this.dim-1) {
                    return false;
                } else {
                    this.currRow++;
                    return true;
                }
            }
        }
    }

    /**
     * Gets the next possible successors.
     * @return an array list of the next successors
     */
    Collection<BoardConfiguration> getSuccessors() {
        ArrayList<BoardConfiguration> successors = new ArrayList<>();
        if (canMove()) {
            BoardConfiguration b1 = new BoardConfiguration(this);
            BoardConfiguration b2 = new BoardConfiguration(this);
            b1.moveCursor();
            b2.moveCursor();
            successors.add(b1);
            if (b2.board[b2.currRow][b2.currCol] != QUEEN) {
                b2.board[b2.currRow][b2.currCol] = QUEEN;
                if (b2.isValid())
                    successors.add(b2);
            }
        }
        return successors;
    }

    /**
     * Checks if the current configuration could be a possible solution.
     * @return true if it could be a solution, false if not
     */
    boolean isValid() {
        // Counts the number of queens in each row and column
        int ctr;
        for (int r = 0; r < this.dim; r++) {
            ctr = 0;
            for (int c = 0; c < this.dim; c++) {
                if (this.board[r][c] == QUEEN) {
                    ctr++;
                }
            }
            if (ctr > 1) {
                return false;
            }
        }
        for (int c = 0; c < this.dim; c++) {
            ctr = 0;
            for (int r = 0; r < this.dim; r++) {
                if (this.board[r][c] == QUEEN) {
                    ctr++;
                }
            }
            if (ctr > 1) {
                return false;
            }
        }

        //Checks diagonals
        int row, col;
        for (int r = 0; r < this.dim; r++) {
            for (int c = 0; c < this.dim; c++) {
                if (this.board[r][c] == QUEEN) {
                    row = r + 1;
                    col = c + 1;
                    while ((row < this.dim)&&(col < this.dim)) {
                        if (this.board[row][col] == QUEEN) {
                            return false;
                        } else {
                            row++;
                            col++;
                        }
                    }
                    row = r + 1;
                    col = c - 1;
                    while ((row < this.dim)&&(col >= 0)) {
                        if (this.board[row][col] == QUEEN) {
                            return false;
                        } else {
                            row++;
                            col--;
                        }
                    }
                    row = r - 1;
                    col = c + 1;
                    while ((row >= 0)&&(col < this.dim)) {
                        if (this.board[row][col] == QUEEN) {
                            return false;
                        } else {
                            row--;
                            col++;
                        }
                    }
                    row = r - 1;
                    col = c - 1;
                    while ((row >= 0)&&(col >= 0)) {
                        if (this.board[row][col] == QUEEN) {
                            return false;
                        } else {
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
     * @return true if it is a solution, false if not
     */
    boolean isGoal() {
        if (!isValid()) {
            return false;
        } else {
            // Counts the number of queens in each row and column
            int ctr;
            for (int r = 0; r < this.dim; r++) {
                ctr = 0;
                for (int c = 0; c < this.dim; c++) {
                    if (this.board[r][c] == QUEEN) {
                        ctr++;
                    }
                }
                if (ctr != 1) {
                    return false;
                }
            }
            for (int c = 0; c < this.dim; c++) {
                ctr = 0;
                for (int r = 0; r < this.dim; r++) {
                    if (this.board[r][c] == QUEEN) {
                        ctr++;
                    }
                }
                if (ctr != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates a string representation of the board.
     * @return string of the layout of the board
     */
    public String toString() {
        String output = this.dim + "x" + this.dim + "\n";
        for (int r = 0; r < this.dim; r++) {
            for (int c = 0; c < this.dim; c++) {
                output += this.board[r][c] + " ";
            }
            output += "\n";
        }
        return output;
    }
}
