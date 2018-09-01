import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Flip extends Application {
    private static int width = 1800;
    private static int height = 900;
    private static int canvasX = 200;
    private static int canvasY = 100;
    private static int tileSize = 20;
    private TileAndBallStorage tb;
    private GraphicsContext gc;
    private Canvas canvas;
    private Button run;
    private Button load;
    private Button saveAs;
    private TextField howFast;
    private SelectOverlay so;
    public static void main(String[] args) {
        try {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            canvasX = Integer.parseInt(args[2]);
            canvasY = Integer.parseInt(args[3]);
            tileSize = Integer.parseInt(args[4]);
            System.out.println("Running custom options:");
        } catch (NumberFormatException e) {
            System.err.println("The arguments are:");
            System.err.println("\t Width: Width of the window. The default is 1800.");
            System.err.println("\t Height: Height of the window. The default is 900.");
            System.err.println("\t X: The x location of the canvas. The default is 200.");
            System.err.println("\t Y: The y location of the canvas.The default is 100.");
            System.err.println("\t Size: Size of each tile. The default is 20. WARNING: changing this value may have unintended effects on the sharpness of the tiles.");
            System.err.println("All units are in pixels, and must be integers.");
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Running default options.");
        }
        System.out.println("\t Width = " + width + ".");
        System.out.println("\t Height = " + height + ".");
        System.out.println("\t X = " + canvasX + ".");
        System.out.println("\t Y = " + canvasY + ".");
        System.out.println("\t Size = " + tileSize + ".");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Flip 2.0");
        Group root = new Group();
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        Canvas canvas = new Canvas(width - canvasX, height - canvasY);
        canvas.setTranslateX(canvasX);
        canvas.setTranslateY(canvasY);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
