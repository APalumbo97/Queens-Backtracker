package queen_project.queen_backtracker;

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
import queen_project.QueenProject;


public class GUI extends Application{

    /** Constant variables */
    private static BoardConfiguration model;
    private static Text message;
    private static int dimensions;
    private final static char QUEEN = BoardConfiguration.QUEEN;

    /**
     * Starts the GUI.
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
     * Build the scene.
     * @return a VBox
     */
    private VBox buildScene() {
        VBox vb = new VBox(15);
        Text tf = message;
        tf.setFont(Font.font("Arial", 42));
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
        Image q;
        try {
            q = new Image(QueenProject.class.getResourceAsStream("/queen.jpg"));
        } catch (Exception e) {
            // Runs during testing
            q = new Image("file:images/queen.jpg");
        }

        ImagePattern queenImage = new ImagePattern(q);
        GridPane gp = new GridPane();
        gp.setHgap(5);
        gp.setVgap(5);
        for (int r = 0; r < dimensions; r++) {
            for (int c = 0; c < dimensions; c++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(100);
                rec.setHeight(100);
                rec.setArcWidth(20);
                rec.setArcHeight(20);
                if (model.getBoard(r, c) == QUEEN) {
                    rec.setFill(queenImage);
                } else {
                    rec.setFill(Color.GRAY);
                }
                GridPane.setRowIndex(rec, r);
                GridPane.setColumnIndex(rec, c);
                gp.getChildren().addAll(rec);
            }
        }
        return gp;
    }

    /**
     * Method that sets the constant variables and runs the GUI.
     * @param b: the board configuration of a solution
     * @param m: the message to be displayed
     * @param args: program arguments for GUI
     */
    public static void runGUI(BoardConfiguration b, String m, String[] args) {
        model = b;
        message = new Text(m);
        dimensions = model.getDim();
        Application.launch(args);
    }
}
