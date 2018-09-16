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

import javafx.scene.control.TextArea;
public class ControlTerm extends Tile {
    TextArea ta = Flip.output;
    Flip f;

    ControlTerm(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    void setFlip(Flip flip) {
        f = flip;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        tb.removeExactBall(b);
        ta.appendText("\nProcess finished with exit code " + b.number);
        f.stopRunning();
    }

    @Override
    String getName() {
        return "CtrlTerm.png";
    }

    @Override
    public Tile clone() {
        ControlTerm q = new ControlTerm(thisDirection);
        q.setFlip(f);
        return q;
    }

    @Override
    char getAscii() {
        return 'Q';
    }
}
