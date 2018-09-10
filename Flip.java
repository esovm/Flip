import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Flip extends Application {
    private static int width = 1800;
    private static int height = 900;
    private static int canvasX = 200;
    private static int canvasY = 100;
    private static int tileSize = 20;
    TileAndBallStorage tb;
    private GraphicsContext gc;
    private Canvas canvas;
    private SelectOverlay so;
    private TextField howFast;
    private TextField input;
    private RunClient runClient = null;
    private File f;
    private FileChooser fc;
    private GraphicsObject currentPlace = null;
    private int shiftX;
    private int shiftY;

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

    private Image findImage(String name) {
        return new Image(this.getClass().getResourceAsStream("\\Icons\\" + name));
    }

    @Override
    public void start(Stage primaryStage) {
        int unitX = canvasX / 2;
        int unitY = canvasY / 2;

        currentPlace = new Empty(Direction.NORTHSOUTHEASTWEST, tileSize);

        fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Flip program(*.f)", "*.f"));

        primaryStage.setTitle("Flip 2.0");
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

        Group root = new Group();
        List<Node> children = root.getChildren();

        tb = new TileAndBallStorage(tileSize);

        so = new SelectOverlay(this, tileSize);

        Button load = new Button("", new ImageView(findImage("Load.png")));
        load.setPrefWidth(unitX * 2);
        load.setPrefHeight(unitY * 2);
        load.relocate(unitX * 6, 0);
        load.setOnAction(event -> {
            f = fc.showOpenDialog(primaryStage);
            if (f != null) {
                tb.read(f);
                draw();
            }
        });
        children.add(load);

        Button saveAs = new Button("", new ImageView(findImage("Save.png")));
        saveAs.setPrefWidth(unitX * 2);
        saveAs.setPrefHeight(unitY * 2);
        saveAs.relocate(unitX * 8, 0);
        saveAs.setOnAction(event -> {
            f = fc.showSaveDialog(primaryStage);
            if (f != null) {
                tb.write(f);
            }
        });
        children.add(saveAs);

        Button run = new Button("", new ImageView(findImage("Run.png")));
        run.setPrefWidth(unitX * 2);
        run.setPrefHeight(unitY * 2);
        run.setOnAction(event -> {
            if (runClient == null) {
                new Thread(runClient = new TimedRunClient(this, Integer.parseInt(howFast.getText()))).start();
            }
        });
        children.add(run);

        Button stop = new Button("", new ImageView(findImage("Stop.png")));
        stop.setPrefWidth(unitX * 2);
        stop.setPrefHeight(unitY * 2);
        stop.relocate(unitX * 2, 0);
        stop.setOnAction(event -> {
            if (runClient != null) {
                runClient.stop();
                runClient = null;
            }
        });
        children.add(stop);

        ToggleButton selectMode = new ToggleButton(""
                //, new ImageView(findImage("Select.png"))
        );
        selectMode.setPrefWidth(unitX * 2);
        selectMode.setPrefHeight(unitY * 2);
        selectMode.relocate(unitX * 10, 0);
        children.add(selectMode);

        ToggleGroup horVer = new ToggleGroup();

        ToggleButton deleteHorizontal = new ToggleButton(""
                //, new ImageView(findImage("Select.png"))
        );
        deleteHorizontal.setPrefWidth(unitX * 2);
        deleteHorizontal.setPrefHeight(unitY);
        deleteHorizontal.relocate(unitX * 12, 0);
        deleteHorizontal.setToggleGroup(horVer);
        children.add(deleteHorizontal);

        ToggleButton deleteVertical = new ToggleButton(""
                //, new ImageView(findImage("Select.png"))
        );
        deleteVertical.setPrefWidth(unitX * 2);
        deleteVertical.setPrefHeight(unitY * 2);
        deleteVertical.relocate(unitX * 12, unitY);
        deleteVertical.setToggleGroup(horVer);
        children.add(deleteVertical);

        howFast = new TextField();
        howFast.setPromptText("How fast the program should run.");
        howFast.setPrefWidth(unitX * 2);
        howFast.setPrefHeight(unitY);
        howFast.relocate(unitX * 4, 0);
        children.add(howFast);

        input = new TextField();
        input.setPromptText("Input for the program.");
        input.setPrefWidth(unitX * 2);
        input.setPrefHeight(unitY);
        input.relocate(unitX * 4, unitY);
        children.add(input);

        canvas = new Canvas(width - canvasX, height - canvasY);
        canvas.relocate(canvasX, canvasY);
        canvas.setFocusTraversable(true);
        children.add(canvas);

        gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);

        Scene s = new Scene(root);

        canvas.setOnMousePressed(event -> {
            int x = ((int) event.getX() + shiftX) / tileSize;
            int y = ((int) event.getY() + shiftY) / tileSize;
            if (x >= 0 && y >= 0) {
                if (deleteHorizontal.isSelected()) {
                    tb.removeRow(y);
                } else if (deleteVertical.isSelected()) {
                    tb.removeCol(x);
                } else if (selectMode.isSelected() ^ event.isAltDown()) {
                    so.select(x, y);
                } else {
                    so.clear();
                    tb.remove(x,y);
                    tb.place(currentPlace.clone(tileSize), x, y);
                }
            }
            draw();
        });
        canvas.setOnMouseDragged(event -> {
            int x = ((int) event.getX() + shiftX) / tileSize;
            int y = ((int) event.getY() + shiftY) / tileSize;
            if (x >= 0 && y >= 0) {
                if (selectMode.isSelected() ^ event.isAltDown()) {
                    so.drag(x, y);
                } else {
                    so.clear();
                    tb.remove(x,y);
                    tb.place(currentPlace.clone(tileSize), x, y);
                }
            }
            draw();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> canvas.requestFocus());
        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                so.delete();
            } else if (event.getCode() == KeyCode.ENTER) {
                tb.update();
                draw();
            } else if (event.isControlDown()) {
                if (event.getCode() == KeyCode.C) {
                    so.copy();
                } else if (event.getCode() == KeyCode.X) {
                    so.cut();
                } else if (event.getCode() == KeyCode.V) {
                    so.paste();
                } else if (event.getCode() == KeyCode.S) {
                    if (event.isShiftDown()) {
                        f = fc.showSaveDialog(primaryStage);
                    }
                    if (f != null) {
                        tb.write(f);
                    }
                } else if (event.getCode() == KeyCode.UP) {
                    if(shiftY >= tileSize/2) {
                        shiftY -= tileSize/2;
                    } else {
                        shiftY = 0;
                    }
                    draw();
                } else if (event.getCode() == KeyCode.LEFT) {
                    if(shiftX >= tileSize/2) {
                        shiftX -= tileSize/2;
                    } else {
                        shiftX = 0;
                    }
                    draw();
                } else if (event.getCode() == KeyCode.DOWN) {
                    shiftY += tileSize/2;
                    draw();
                } else if (event.getCode() == KeyCode.RIGHT) {
                    shiftX += tileSize/2;
                    draw();
                }
            }
        });
        canvas.setOnKeyTyped(event -> {
            String character = event.getCharacter();
            if (character.length() > 0) {
                char cha = character.charAt(0);
                if (!event.isControlDown()) {
                    if (selectMode.isSelected() ^ event.isAltDown()) {
                        so.fill(GraphicsObject.create(cha));
                    } else {
                        currentPlace = GraphicsObject.create(cha);
                    }
                }
            }
        });
        canvas.requestFocus();

        primaryStage.setOnCloseRequest(event -> stop.fire());
        primaryStage.setScene(s);
        primaryStage.show();
        draw();
    }

    void draw() {
        gc.setFill(Color.gray(0.25));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = -tileSize; i < canvas.getWidth() + tileSize; i += tileSize) {
            gc.strokeLine(i + (shiftX % tileSize), 0, i + (shiftX % tileSize), canvas.getHeight());
        }
        for (int i = -tileSize; i < canvas.getHeight() + tileSize; i += tileSize) {
            gc.strokeLine(0, i + (shiftY % tileSize), canvas.getWidth(), i + (shiftY % tileSize));
        }
        gc.setTransform(new Affine(new Translate(-shiftX, -shiftY)));
        tb.draw(gc);
        so.draw(gc);
        gc.setTransform(new Affine());
    }
}
