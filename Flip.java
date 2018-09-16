/*
Copyright 2018 Rouli Freeman

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Flip extends Application {
	private Group root;
	private GraphicsContext gc;
	private static int width = 1800;
	private static int height = 900;
	private static int canvasX = 200;
	private static int canvasY = 100;
	private static int tileSize = 20;
	private static String whichFont = "Space_Mono.ttf";
	private static int fontSize = 12;
	GraphicsObjectStorage storage;
	private Canvas canvas;
	private SelectOverlay overlay;
	private TextField howFast;
	private Timeline runStop;
	private File defaultSaveLocation;
	private FileChooser chooser;
	private GraphicsObject toWrite;
	static TextField input;
	static TextArea output;
	private int shiftX;
	private int shiftY;
	private ArrayList<ResizableControl> controls;
	private ResizableInt cX;
	private ResizableInt cY;
	private ResizableInt uX;
	private ResizableInt uY;

	public static void main(String[] args) {
		launch(args);
	}

	private Image findImage(String name) {
		return new Image(this.getClass().getResourceAsStream("/Icons/" + name));
	}

	private Font findFont(String name, int size) {
		return Font.loadFont(this.getClass().getResourceAsStream("/Fonts/" + name), size);
	}

	private <T extends Control> T addChild(T child, String tooltip) {
		List<Node> children = root.getChildren();
		child.setTooltip(new Tooltip(tooltip));
		child.setPadding(Insets.EMPTY);
		children.add(child);
		return child;
	}

	@Override
	public void start(Stage primaryStage) {
		Map<String, String> args = getParameters().getNamed();
		try {
			if (!args.isEmpty()) {
				int length = 0;
				if (args.containsKey("help")) {
					System.out.println("The arguments are:");
					System.out.println("\t Width: Width of the window. The default is 1800.");
					System.out.println("\t Height: Height of the window. The default is 900.");
					System.out.println("\t Size: Size of each tile. The default is 20. WARNING: changing this value may have unintended effects on the sharpness of the tiles.");
					System.out.println("\t Font: The font for the output. The default is space mono.");
					System.out.println("\t Size: Size of the font. The default is 12.");
					System.out.println("All units are in pixels, and must be integers.");
					System.exit(0);
				}
				if (args.containsKey("width")) {
					length++;
					width = Integer.parseInt(args.get("width"));
				}
				if (args.containsKey("height")) {
					length++;
					height = Integer.parseInt(args.get("height"));
				}
				if (args.containsKey("tileSize")) {
					length++;
					tileSize = Integer.parseInt(args.get("tileSize"));
				}
				if (args.containsKey("font")) {
					length++;
					whichFont = args.get("font");
				}
				if (args.containsKey("fontSize")) {
					length++;
					fontSize = Integer.parseInt(args.get("fontSize"));
				}
				if (args.size() != length) {
					System.err.println("You have typed an incorrect argument. Please run flip with the --help=true argument.");
					System.exit(1);
				}
				System.out.println("Running custom options:");
			} else {
				System.out.println("Running default options:");
			}
		} catch (NumberFormatException e) {
		}
		System.out.println("\t Width = " + width + ".");
		System.out.println("\t Height = " + height + ".");
		System.out.println("\t Size = " + tileSize + ".");
		System.out.println("\t Font = " + whichFont + ".");
		System.out.println("\t Font Size = " + fontSize + ".");
		cX = x -> (int) ((x/1800.0)*200);
		cY = y -> (int) ((y/900.0)*100);
		uX = x -> x / 2;
		uY = y -> y / 2;


		toWrite = new Empty(Direction.NORTHSOUTHEASTWEST);

		controls = new ArrayList<>();
		
		chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Flip program(*.f)", "*.f"));

		primaryStage.setTitle("Flip");
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
		primaryStage.getIcons().add(findImage("Icon.png"));

		root = new Group();

		storage = new GraphicsObjectStorage(tileSize);

		overlay = new SelectOverlay(this, tileSize);
		ImageView runImg = new ImageView(findImage("Run.png"));
		Button run = new Button("", runImg);
		addChild(run, "Run program").setOnAction(event -> {
			if (runStop == null) {
				try {
				runStop = new Timeline(new KeyFrame(Duration.millis(Integer.parseUnsignedInt(howFast.getText())), ae -> {
					storage.update();
					draw();
				}));
				runStop.setCycleCount(Timeline.INDEFINITE);
				runStop.play();
				} catch(NumberFormatException e) {
					System.err.println("Warning: The text box for how fast to run the program is either empty or has a non-number character or is negative.");
				}
			}
		});
		controls.add(new ResizableButton(run, runImg, x -> x * 2, y -> y * 2, x -> 0, y -> 0));
		ImageView stopImg = new ImageView(findImage("Stop.png"));
		Button stop = new Button("", stopImg);
		addChild(stop, "Stop program").setOnAction(event -> {
			stopRunning();
		});
		controls.add(new ResizableButton(stop, stopImg, x -> x * 2, y -> y * 2, x -> x * 2, y -> 0));

		howFast = new TextField();
		addChild(howFast, "How fast the program should go(In milliseconds)")
				.setPromptText("How fast the program should run.");
		controls.add(new ResizableControl(howFast, x -> x * 2, y -> y * 2, x -> x * 4, y -> 0));
		
		ImageView loadImg = new ImageView(findImage("Load.png"));
		Button load = new Button("", loadImg);
		addChild(load, "Load file").setOnAction(event -> {
			defaultSaveLocation = chooser.showOpenDialog(primaryStage);
			if (defaultSaveLocation != null) {
				storage.read(defaultSaveLocation, this);
				output.clear();
				draw();
			}
		});  
		controls.add(new ResizableButton(load, loadImg, x -> x * 2, y -> y * 2, x -> x * 6, y -> 0));

		ImageView saveAsImg = new ImageView(findImage("Save.png"));
		Button saveAs = new Button("", saveAsImg);
		addChild(saveAs, "Save file").setOnAction(event -> {
			defaultSaveLocation = chooser.showSaveDialog(primaryStage);
			if (defaultSaveLocation != null) {
				storage.write(defaultSaveLocation);
			}
		});
		controls.add(new ResizableButton(saveAs, saveAsImg, x -> x * 2, y -> y * 2, x -> x * 8, y -> 0));

		ImageView selectModeImg = new ImageView(findImage("Select.png"));
		ToggleButton selectMode = new ToggleButton("", selectModeImg);
		addChild(selectMode, "Text editor mode or tower defense mode.");
		controls.add(new ResizableButton(selectMode, selectModeImg, x -> x * 2, y -> y * 2, x -> x * 10, y -> 0));


		ToggleGroup horVer = new ToggleGroup();

		ImageView deleteHorizontalImg = new ImageView(findImage("SelectHor.png"));
		ToggleButton deleteHorizontal = new ToggleButton("", deleteHorizontalImg);
		addChild(deleteHorizontal, "While this is selected, click to remove rows.")
				.setToggleGroup(horVer);
		controls.add(new ResizableButton(deleteHorizontal, deleteHorizontalImg, x -> x * 2, y -> y, x -> x * 12, y -> 0));

		ImageView deleteVerticalImg =  new ImageView(findImage("SelectVer.png"));
		ToggleButton deleteVertical = new ToggleButton("", deleteVerticalImg);
		addChild(deleteVertical, "While this is selected, click to remove colums.").setToggleGroup(horVer);
		controls.add(new ResizableButton(deleteVertical, deleteVerticalImg, x -> x * 2, y -> y, x -> x * 12, y -> y));
		
		input = new TextField();
		addChild(input, "Input for the program.")
				.setPromptText("Input for the program.");
		controls.add(new ResizableControl(input, x -> x * 2, y -> y * 2, x -> x * 14, y -> 0));

		ImageView clearImg = new ImageView(findImage("Clear.png"));
		Button clear = new Button("", clearImg);
		addChild(clear, "Click to clear the program output.")
				.setOnAction(event -> output.setText(""));
		controls.add(new ResizableButton(clear, clearImg, x -> x, y -> y * 2, x -> x * 16, y -> 0));


		output = new TextArea();
		addChild(output, "Output for the program.")
				.setPromptText("Output for the program.");
		output.setWrapText(false);
		output.setFont(findFont(whichFont, fontSize));
		showHelp();
		controls.add(new ResizableControl(output, x -> x * 2, y -> height - y * 2 - 40, x -> 0, y -> y * 2));

		canvas = new Canvas();
		canvas.setFocusTraversable(true);
		root.getChildren().add(canvas);

		resize(primaryStage);
		
		gc = canvas.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);

		Scene s = new Scene(root);

		canvas.setOnMousePressed(event -> {
			int x = ((int) event.getX() + shiftX) / tileSize;
			int y = ((int) event.getY() + shiftY) / tileSize;
			if (x >= 0 && y >= 0) {
				if (deleteHorizontal.isSelected()) {
					storage.removeRow(y);
				} else if (deleteVertical.isSelected()) {
					storage.removeCol(x);
				} else if (selectMode.isSelected() ^ event.isAltDown()) {
					overlay.select(x, y);
				} else {
					overlay.clear();
					storage.remove(x, y);
					storage.place(toWrite.clone(), x, y);
				}
			}
			draw();
		});
		canvas.setOnMouseDragged(event -> {
			int x = ((int) event.getX() + shiftX) / tileSize;
			int y = ((int) event.getY() + shiftY) / tileSize;
			if (x >= 0 && y >= 0) {
				if (selectMode.isSelected() ^ event.isAltDown()) {
					overlay.drag(x, y);
				} else {
					overlay.clear();
					storage.remove(x, y);
					storage.place(toWrite.clone(), x, y);
				}
			}
			draw();
		});
		canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> canvas.requestFocus());
		canvas.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.BACK_SPACE) {
				overlay.delete();
			} else if (event.getCode() == KeyCode.ENTER) {
				storage.update();
				draw();
			} else if (event.isControlDown()) {
				// R: Add some way for these key strokes to be discoverable. Otherwise the user
				// won't discover them and won't use them so they're useless.
				// Displaying keystrokes when they make a mistake or type control-questionmark
				// or questionmark might suffice.
				// Or a prominent help button. Or show keys one startup.
				if (event.getCode() == KeyCode.SLASH) {
					showHelp();
				} else if (event.getCode() == KeyCode.C) {
					overlay.copy();
				} else if (event.getCode() == KeyCode.X) {
					overlay.cut();
				} else if (event.getCode() == KeyCode.V) {
					overlay.paste();
				} else if (event.getCode() == KeyCode.S) {
					if (event.isShiftDown()) {
						defaultSaveLocation = chooser.showSaveDialog(primaryStage);
					}
					if (defaultSaveLocation != null) {
						storage.write(defaultSaveLocation);
					}
				} else if (event.getCode() == KeyCode.Z) {
					if (defaultSaveLocation != null) {
						storage.read(defaultSaveLocation, this);
						output.clear();
						draw();
					}
				} else if (event.getCode() == KeyCode.UP) {
					if (shiftY >= tileSize / 2) {
						shiftY -= tileSize / 2;
					} else {
						shiftY = 0;
					}
					draw();
				} else if (event.getCode() == KeyCode.LEFT) {
					if (shiftX >= tileSize / 2) {
						shiftX -= tileSize / 2;
					} else {
						shiftX = 0;
					}
					draw();
				} else if (event.getCode() == KeyCode.DOWN) {
					shiftY += tileSize / 2;
					draw();
				} else if (event.getCode() == KeyCode.RIGHT) {
					shiftX += tileSize / 2;
					draw();
				}
			}
		});
		canvas.setOnKeyTyped(event -> {
			String character = event.getCharacter();
			if (character.length() > 0) {
				char typedChar = character.charAt(0);
				if (!event.isControlDown()) {
					toWrite = GraphicsObject.create(typedChar);
					if (toWrite instanceof ControlTerm) {
						((ControlTerm) toWrite).setFlip(this);
					}
					if (selectMode.isSelected() ^ event.isAltDown()) {
						overlay.fill(GraphicsObject.create(typedChar));
					}
				}
			}
		});
		canvas.requestFocus();

		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
			resize(primaryStage);
			draw();
		};
		primaryStage.widthProperty().addListener(stageSizeListener);
		primaryStage.heightProperty().addListener(stageSizeListener);
		
		Tile.init();
		GraphicsObject.init();

		primaryStage.setOnCloseRequest(event -> stopRunning());
		primaryStage.setScene(s);
		primaryStage.show();
		draw();
	}

	private void resize(Stage primaryStage) {
		width = (int) primaryStage.getWidth();
		height = (int) primaryStage.getHeight();
		canvasX = cX.calculate(width);
		canvasY = cY.calculate(height);
		int unitX = uX.calculate(canvasX);
		int unitY = uY.calculate(canvasY);
		for(ResizableControl c : controls) {
			c.resize(unitX, unitY);
		}
		canvas.setWidth(width - canvasX);
		canvas.setHeight(height - canvasY);
		canvas.relocate(canvasX, canvasY);
	}

	private void showHelp() {
		output.appendText("\n"
				+ "\nKeycode help:"
				+ "\n\t[Ctrl] C: Copy selection."
				+ "\n\t[Ctrl] X: Cut selection."
				+ "\n\t[Ctrl] V: Paste selection."
				+ "\n\t[Ctrl] S: Save to most recently saved file."
				+ "\n\t[Ctrl][Shift] S: Open save dialogue."
				+ "\n\t[Ctrl] Z: Open from most recently saved file."
				+ "\n\t[Ctrl] /: Display help."
				+ "\n\t[Ctrl] Arrowkeys: Move point of view."
				+ "\n\t[Alt]: Toggle selection."
				+ "\n\tWASD: Place balls."
				+ "\n\tAny other key: Place tile.");
	}

	void stopRunning() {
		if (runStop != null) {
			runStop.stop();
			runStop = null;
		}
	}

	synchronized void draw() {
		gc.setFill(Color.gray(0.25));
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (int i = -tileSize; i < canvas.getWidth() + tileSize; i += tileSize) {
			gc.strokeLine(i + (shiftX % tileSize), 0, i + (shiftX % tileSize), canvas.getHeight());
		}
		for (int i = -tileSize; i < canvas.getHeight() + tileSize; i += tileSize) {
			gc.strokeLine(0, i + (shiftY % tileSize), canvas.getWidth(), i + (shiftY % tileSize));
		}
		gc.setTransform(new Affine(new Translate(-shiftX, -shiftY)));
		storage.draw(gc);
		overlay.draw(gc);
		gc.setTransform(new Affine());
	}
}
