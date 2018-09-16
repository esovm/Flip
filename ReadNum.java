import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.Scanner;

public class ReadNum extends Tile {
    private TextField toRead = Flip.input;
    private Ball ball;
    private GraphicsObjectStorage tileBall;


    ReadNum(Direction whichDirection) {
        super(whichDirection);
        toRead.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            char c = event.getCharacter().charAt(0);
            if(c == ' ') {
                handle();
            }
        });
    }



    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
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
        return "ReadNum.png";
    }

    @Override
    void draw(GraphicsContext gc, int x, int y, int tileSize) {
        super.draw(gc, x, y, tileSize);
        if(ball != null) {
            ball.draw(gc, tileSize);
        }
    }

    @Override
    public Tile clone() {
        ReadNum r = new ReadNum(thisDirection);
        return r;
    }

    @Override
    char getAscii() {
        return 'r';
    }

    private synchronized void handle() {
        if(ball != null) {
            String s = toRead.getText();
            Scanner sc = new Scanner(s);
            ball.number = sc.nextInt();
            toRead.setText(s.substring(Integer.toString(ball.number).length() + 1));
            tileBall.resume();
            tileBall.place(ball, ball.x, ball.y);
            ball = null;
            tileBall = null;
            sc.close();
        }
    }
}
