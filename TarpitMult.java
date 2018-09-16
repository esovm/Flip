import javafx.scene.canvas.GraphicsContext;

public class TarpitMult extends Tile {
    private Ball ball;
    TarpitMult(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        if(ball != null) {
            b.number *= ball.number;
            ball = null;
        } else {
            ball = b;
            tb.removeExactBall(b);
        }
    }

    @Override
    String getName() {
        return "TarpitMult.png";
    }

    @Override
    public Tile clone() {
        return new TarpitMult(thisDirection);
    }

    @Override
    char getAscii() {
        return '*';
    }

    @Override
    void draw(GraphicsContext gc, int x, int y, int tileSize) {
        super.draw(gc, x, y, tileSize);
        if(ball != null) {
            ball.draw(gc, tileSize);
        }
    }
}
