import javafx.scene.canvas.GraphicsContext;

public class NumGen extends Tile {
    int number;
    NumGen(Direction whichDirection, int num) {
        super(whichDirection);
        number = num;
    }

    @Override
    boolean getModifier(Ball b) {
        return b.number == 0 && number == 0;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        tb.place(new Ball(b.thisDirection, b.x, b.y, number), b.x, b.y);
        b.thisDirection = Direction.flip(b.thisDirection);
    }

    @Override
    String getName() {
        return "Error.png";
    }

    @Override
    void draw(GraphicsContext gc, int x, int y, int tileSize) {
        gc.strokeText("" + number, x * tileSize + tileSize / 2, y * tileSize + tileSize / 2);
    }

    @Override
    public Tile clone() {
        return new NumGen(thisDirection, number);
    }

    @Override
    char getAscii() {
        return Integer.toString(number).charAt(0);
    }
}
