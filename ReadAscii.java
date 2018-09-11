import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ReadAscii extends Tile {
    private TextField toRead = Flip.input;
    private Ball ball;
    private TileAndBallStorage tileBall;


    ReadAscii(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
        toRead.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            handle();
        });
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        ball = b;
        tileBall = tb;
        tb.removeExactBall(b);
        String s = toRead.getText();
        if(s.equals("")) {
            if(b.number < 0) {
                tb.suspend();
            } else if(b.number == 0) {
                ball = null;
            }
        } else {
            handle();
        }
    }

    @Override
    String getName() {
        return "ReadAscii.png";
    }

    @Override
    void draw(GraphicsContext gc, int x, int y) {
        super.draw(gc, x, y);
        if(ball != null) {
            ball.draw(gc);
        }
    }

    @Override
    public Tile clone(int sizeTile) {
        return new ReadAscii(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return 'R';
    }

    private synchronized void handle() {
        if(ball != null) {
            String s = toRead.getText();
            ball.number = s.charAt(0);
            toRead.setText(s.substring(1));
            tileBall.resume();
            tileBall.place(ball, ball.x, ball.y);
            ball = null;
            tileBall = null;
        }
    }
}
