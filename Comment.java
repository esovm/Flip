import javafx.scene.canvas.GraphicsContext;

class Comment extends Tile {
    private char c;
    Comment(Direction whichDirection, int sizeTile, char whichCharacter) {
        super(whichDirection, sizeTile);
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
    void update(Ball b, TileAndBallStorage tb) {
        System.err.println("Warning: The ball should not reach comments.");
    }

    @Override
    public Comment clone(int sizeTile) {
        return new Comment(thisDirection, sizeTile,getAscii());
    }

    @Override
    String getName() {
        return "Error.png";
    }

    @Override
    void draw(GraphicsContext gc, int x, int y) {
        gc.strokeText(""+c,x*tileSize+tileSize/2,y*tileSize+tileSize/2);
    }
}
