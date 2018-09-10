import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.Scanner;

public class ReadAscii extends Tile {
    private TextField toRead;
    private Ball ball;
    private TileAndBallStorage tileBall;


    ReadAscii(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    void setTextField(TextField tf) {
        toRead = tf;
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
        ReadAscii r = new ReadAscii(thisDirection, sizeTile);
        r.setTextField(toRead);
        return r;
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
