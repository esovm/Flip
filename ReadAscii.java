/*
Copyright 2018 Rouli Freeman

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ReadAscii extends Tile {
    private TextField toRead = Flip.input;
    private Ball ball;
    private GraphicsObjectStorage tileBall;


    ReadAscii(Direction whichDirection) {
        super(whichDirection);
        toRead.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            handle();
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
        return "ReadAscii.png";
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
        return new ReadAscii(thisDirection);
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
