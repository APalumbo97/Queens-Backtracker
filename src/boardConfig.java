/**
 * @author: Anthony Palumbo
 * date: 11-14-16
 * description: class for the chess board configuration
 */

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.ArrayList;

public class boardConfig {

    public final static char EMPTY = '#';

    public final static char QUEEN = 'Q';

    private int dim;
    private int rows[];
    private int cols[];
    private String[][] board;
    private int currRow;
    private int currCol;

    public boardConfig(String filename) throws FileNotFoundException {
        //TODO
    }

    public boardConfig(boardConfig copy) {
        //TODO
    }

    public boolean canMove() {
        //TODO may not be needed
        return true; //TODO remove
    }

    public boolean moveCursor() {
        //TODO may not be needed
        return true; //TODO remove
    }

    public Collection<boardConfig> getSuccessors() {
        //TODO
        return new ArrayList<boardConfig>(); //TODO remove
    }

    public boolean isValid() {
        //TODO
        return true; //TODO remove
    }

    public boolean isGoal() {
        //TODO
        return true; //TODO remove
    }

    public String toString() {
        //TODO
        return ""; //TODO remove
    }
}
