import javafx.application.Application;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @author Anthony Palumbo
 * date: 11-14-16
 * description: file that runs the project using boardConfig and backtracker
 * usage: java findSolution <filename> <PTUI or GUI>
 */
public class findSolution {

    /**
     * Main function that runs the program with either a PTUI or GUI.
     * @param args: first argument is the filename, second is PTUI or GUI
     */
    public static void main(String[] args) {
        try {
            boardConfig b = new boardConfig(args[0]);
            backtracker bt = new backtracker();
            double startTime = System.currentTimeMillis();
            Optional<boardConfig> solution = bt.solve(b);
            if(args[1].equals("PTUI")) {
                System.out.println("Time taken: " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds.");

                if(solution.isPresent())
                    System.out.println("Solution:\n" + solution.get().toString());
                else
                    System.out.println("No solution!");
            }
            else if(args[1].equals("GUI")) {
                //TODO launch the GUI
                System.out.println("GUI not implemented yet.");
            }
        }
        catch(FileNotFoundException f) {
            System.out.println("Incorrect name for the input file.");
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }


//    @Override
//    public void start(Stage stage) throws Exception {
//        //TODO
//    }
}
