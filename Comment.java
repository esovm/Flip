import javafx.scene.canvas.GraphicsContext;

class Comment extends Tile {
    private char c;

    Comment(Direction whichDirection, char whichCharacter) {
        super(whichDirection);
        c = whichCharacter;
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    char getAscii() {
        return c;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        Flip.output.appendText("Warning: The ball should not reach comments.");
    }

    @Override
    public Comment clone() {
        return new Comment(thisDirection, getAscii());
    }

    @Override
    String getName() {
        return "Error.png";
    }

    @Override
    void draw(GraphicsContext gc, int x, int y, int tileSize) {
        gc.strokeText("" + c, x * tileSize + tileSize / 2, y * tileSize + tileSize / 2);
    }
}
