import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @author Anthony Palumbo
 * date: 11-14-16
 * description: file that runs the project using boardConfig and backtracker
 * usage: java findSolution <filename> <CLI or GUI>
 */
public class findSolution extends Application {

    private static int dimensions;
    private static boardConfig b;
    private final static char QUEEN = 'Q';
    private static Text message = new Text();

    /**
     * Main function that runs the program with either a CLI or GUI.
     * @param args: first argument is the filename, second is CLI or GUI
     */
    public static void main(String[] args) {
        try {
            b = new boardConfig(args[0]);
            dimensions = b.getDim();
            backtracker bt = new backtracker();
            double startTime = System.currentTimeMillis();
            Optional<boardConfig> solution = bt.solve(b);
            if(args[1].equals("CLI")) {
                System.out.println("Time taken: " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds.");

                if(solution.isPresent())
                    System.out.println("Solution:\n" + solution.get().toString());
                else
                    System.out.println("No solution!");
            }
            else if(args[1].equals("GUI")) {
                if(solution.isPresent()) {
                    b = solution.get();
                    message.setText("Solution:");
                }
                else {
                    message.setText("No Solution.");
                }
                message.setFont(Font.font("Arial", 42));
                Application.launch(args);
            }
        }
        catch(FileNotFoundException f) {
            System.out.println("Incorrect name for the input file.");
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Starts the GUI
     * @param stage: the initial stage
     * @throws Exception any exception that occurs
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Queens Back-tracker");
        Scene scene = new Scene(buildScene(), 1000, 1000);
        scene.setFill(Color.BLUE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Build the scene
     * @return a VBox
     */
    private VBox buildScene() {
        VBox vb = new VBox(15);
        Text tf = message;
        vb.getChildren().addAll(tf, buildBoard());
        vb.setSpacing(10);
        vb.setPadding(new Insets(10));
        return vb;
    }

    /**
     * Builds a grid pane.
     * @return a GridPane
     */
    private GridPane buildBoard() {
        Image q = new Image("file:images/queen.jpg");
        ImagePattern queenImage = new ImagePattern(q);
        GridPane gp = new GridPane();
        gp.setHgap(5);
        gp.setVgap(5);
        for(int r = 0; r < dimensions; r++) {
            for(int c = 0; c < dimensions; c++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(100);
                rec.setHeight(100);
                rec.setArcWidth(20);
                rec.setArcHeight(20);
                if(b.getBoard(r, c) == QUEEN) {
                    rec.setFill(queenImage);
                }
                else
                    rec.setFill(Color.GRAY);
                GridPane.setRowIndex(rec, r);
                GridPane.setColumnIndex(rec, c);
                gp.getChildren().addAll(rec);
            }
        }
        return gp;
    }
}
