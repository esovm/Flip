import javafx.scene.canvas.GraphicsContext;

public class Comment extends Tile {
    int c;
    public Comment(Direction whichDirection, int posX, int posY, int sizeTile, int whichCharacter) {
        super(whichDirection, posX, posY, sizeTile);
        c = whichCharacter;
    }

    @Override
    public boolean getModifier(Ball b) {
        return false;
    }

    @Override
    public int getAscii() {
        return c;
    }

    public static Direction[] getAllowedDirections() {
        return new Direction[]{Direction.NORTHSOUTHEASTWEST};
    }

    @Override
    public void update(Ball b, TileAndBallStorage tb) {
        System.err.println("Warning: The ball should not reach comments.");
    }

    @Override
    public Tile clone(int posX, int posY, int sizeTile) {
        return new Comment(thisDirection,posX,posY,sizeTile,getAscii());
    }

    @Override
    public String getName() {
        return "Error.jpg";
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.strokeText(""+c,x*tileSize+tileSize/2,y*tileSize+tileSize/2);
    }
}
