import javafx.scene.control.TextField;

import java.util.Scanner;

public class ReadNum extends Tile {
    private TextField toRead;
    private Ball ball;

    ReadNum(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    void setTextField(TextField tf) {
        toRead = tf;
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    boolean hasBall() {
        return ball != null;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        String s = toRead.getText();
        if(s.equals("")) {
            if(b.number > 0) {
                ball = b;
                tb.removeExactBall(b);
            } else if(b.number < 0) {
                tb.suspend();
                while(true) {
                    try {
                        Thread.sleep(20);
                    } catch(InterruptedException e) {
                        System.err.println("Warning: Thread interrupted.");
                        e.printStackTrace();
                    }
                    if(tb.getTileAtPos(b.x, b.y) == this) {
                        if(s.contains(" ")) {
                            update(b,tb);
                            break;
                        }
                    } else {
                        break;
                    }
                }
                tb.resume();
            } else {
                tb.removeExactBall(b);
            }
        } else {
            Scanner sc = new Scanner(s);
            b.number = sc.nextInt();
            s.substring(Integer.toString(b.number).length());
        }
    }

    @Override
    String getName() {
        return "ReadNum.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        ReadNum p = new ReadNum(thisDirection, sizeTile);
        p.setTextField(toRead);
        return p;
    }

    @Override
    char getAscii() {
        return 'p';
    }
}
