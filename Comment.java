import javafx.scene.canvas.GraphicsContext;

class Comment extends Tile {
    private int c;
    Comment(Direction whichDirection, int posX, int posY, int sizeTile, int whichCharacter) {
        super(whichDirection, posX, posY, sizeTile);
        c = whichCharacter;
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    int getAscii() {
        return c;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        System.err.println("Warning: The ball should not reach comments.");
    }

    @Override
    Tile clone(int posX, int posY, int sizeTile) {
        return new Comment(thisDirection,posX,posY,sizeTile,getAscii());
    }

    @Override
    String getName() {
        return "Error.png";
    }

    @Override
    void draw(GraphicsContext gc) {
        gc.strokeText(""+c,x*tileSize+tileSize/2,y*tileSize+tileSize/2);
    }
}
