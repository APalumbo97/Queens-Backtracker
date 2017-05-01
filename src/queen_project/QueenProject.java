package queen_project;

import queen_project.queen_backtracker.BackTracker;
import queen_project.queen_backtracker.BoardConfiguration;
import queen_project.queen_backtracker.GUI;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @author Anthony Palumbo
 * date: 11-20-16
 * description: Program that runs either a CLI or GUI
 * usage: java -jar QueenProject.jar <input-filename> <CLI or GUI>
 */
public class QueenProject {

    /** Constant variables */
    private static BoardConfiguration b;
    private static double startTime;
    private static boolean isSolution;

    /**
     * Main method that is run.
     * @param args: the program arguments
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Usage: java -jar QueenProject.jar <input-filename> <CLI or GUI>");
                System.err.println("Use \"default\" for the input-filename to use an empty file.");
                System.exit(1);
            }
            startBackTracker(args[0]);
            switch (args[1]) {
                case "CLI":
                    startCLI(); break;
                case "GUI":
                    startGUI(args); break;
                default:
                    System.err.println("Usage: java -jar QueenProject.jar <input-filename> <CLI or GUI>");
                    System.err.println("Use \"default\" for the input-filename to use empty.txt");
                    System.exit(0);
            }
        } catch (FileNotFoundException f) {
            System.err.println("Incorrect name for the input file.");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }

    /**
     * Creates a board configuration and back tracker and tries to find a solution.
     * @param filename: the name of the input file
     * @throws FileNotFoundException: when the file cannot be found
     */
    private static void startBackTracker(String filename) throws FileNotFoundException {
        if (filename.equals("default")) {
            b = new BoardConfiguration(filename, true);
        } else {
            b = new BoardConfiguration(filename, false);
        }
        BackTracker bt = new BackTracker();
        startTime = System.currentTimeMillis();
        Optional<BoardConfiguration> solution = bt.solve(b);
        if (solution.isPresent()) {
            b = solution.get();
            isSolution = true;
        } else {
            isSolution = false;
        }
    }

    /**
     * Starts the command line interface for the program.
     * @throws Exception: any exception that occurs
     */
    private static void startCLI() throws Exception {
        System.out.println("Time taken: " + (System.currentTimeMillis()-startTime/1000.0) + " seconds.");
        if (isSolution) {
            System.out.println("Solution:\n" + b.toString());
        } else {
            System.out.println("No solution!");
        }
    }

    /**
     * Start the graphical user interface.
     * @param args: the arguments for the GUI
     * @throws Exception: any exception that occurs
     */
    private static void startGUI(String[] args) throws Exception {
        if (isSolution) {
            GUI.runGUI(b, "Solution: ", args);
        } else {
            GUI.runGUI(b, "No Solution!", args);
        }
    }
}
