import javafx.scene.canvas.GraphicsContext;

public class TarpitMult extends Tile {
    private Ball ball;
    TarpitMult(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
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
    public Tile clone(int sizeTile) {
        return new TarpitMult(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '*';
    }

    @Override
    void draw(GraphicsContext gc, int x, int y) {
        super.draw(gc, x, y);
        if(ball != null) {
            ball.draw(gc);
        }
    }
}
