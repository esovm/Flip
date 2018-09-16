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
import javafx.scene.image.Image;

abstract class GraphicsObject {
    static GraphicsObject[] subClasses;
    Direction thisDirection;
    private Image img;

    abstract String getName();

    static void init() {
        subClasses = new GraphicsObject[]{
                new Empty(Direction.NORTHSOUTHEASTWEST),
                new Ball(Direction.NORTH, 0, 0, 0), new Ball(Direction.SOUTH, 0, 0, 0),
                new Ball(Direction.EAST, 0, 0, 0), new Ball(Direction.WEST, 0, 0, 0),
                new Sluce(Direction.NORTH), new Sluce(Direction.SOUTH),
                new Sluce(Direction.EAST), new Sluce(Direction.WEST),
                new True(Direction.NORTHSOUTHEASTWEST), new Random(Direction.NORTHSOUTHEASTWEST),
                new Flipper(Direction.NORTHSOUTH),new Flipper(Direction.EASTWEST),
                new Wall(Direction.NORTHSOUTH), new Wall(Direction.EASTWEST),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 0), new NumGen(Direction.NORTHSOUTHEASTWEST, 1),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 2), new NumGen(Direction.NORTHSOUTHEASTWEST, 3),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 4), new NumGen(Direction.NORTHSOUTHEASTWEST, 5),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 6), new NumGen(Direction.NORTHSOUTHEASTWEST, 7),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 8), new NumGen(Direction.NORTHSOUTHEASTWEST, 9),
                new TarpitAdd(Direction.NORTHSOUTHEASTWEST), new TarpitMult(Direction.NORTHSOUTHEASTWEST),
                new UnaryNegate(Direction.NORTHSOUTHEASTWEST), new UnaryReset(Direction.NORTHSOUTHEASTWEST),
                new UnaryDecrement(Direction.NORTHSOUTHEASTWEST), new UnaryIncrement(Direction.NORTHSOUTHEASTWEST),
                new PrintNum(Direction.NORTHSOUTHEASTWEST), new PrintAscii(Direction.NORTHSOUTHEASTWEST),
                new ReadNum(Direction.NORTHSOUTHEASTWEST), new ReadAscii(Direction.NORTHSOUTHEASTWEST),
                new ControlTerm(Direction.NORTHSOUTHEASTWEST), new CtrlGrille(Direction.NORTHSOUTHEASTWEST),
                new Processor(Direction.NORTHSOUTHEASTWEST)};
    }

    public abstract GraphicsObject clone();

    abstract char getAscii();

    Image findImage() {
        return new Image(this.getClass().getResourceAsStream("/Icons/" + getName()));
    }


    void draw(GraphicsContext gc, int x, int y, int tileSize) {
        gc.drawImage(img, x * tileSize, y * tileSize, tileSize, tileSize);
    }

    GraphicsObject(Direction whichDirection) {
        thisDirection = whichDirection;
        img = findImage();
    }

    static GraphicsObject create(char ascii) {
        for (GraphicsObject sc : subClasses) {
            if (sc.getAscii() == ascii) {
                return sc;
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST, ascii);
    }


}
