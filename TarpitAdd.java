import javafx.scene.canvas.GraphicsContext;

public class TarpitAdd extends Tile {
    private Ball ball;
    TarpitAdd(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return b.number > 0;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
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
    public Tile clone() {
        return new TarpitAdd(thisDirection);
    }

    @Override
    char getAscii() {
        return '+';
    }

    @Override
    void draw(GraphicsContext gc, int x, int y, int tileSize) {
        super.draw(gc, x, y, tileSize);
        if(ball != null) {
            ball.draw(gc, tileSize);
        }
    }
}
