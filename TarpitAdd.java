import javafx.scene.canvas.GraphicsContext;

public class TarpitAdd extends Tile {
    private Ball ball;
    TarpitAdd(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return b.number > 0;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        if(ball != null) {
            b.number += ball.number;
            ball = null;
        } else {
            ball = b;
            tb.removeExactBall(b);
        }
    }

    @Override
    String getName() {
        return "TarpitAdd.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new TarpitAdd(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '+';
    }

    @Override
    void draw(GraphicsContext gc, int x, int y) {
        super.draw(gc, x, y);
        ball.draw(gc);
    }
}
