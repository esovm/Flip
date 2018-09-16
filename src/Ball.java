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

class Ball extends GraphicsObject {
    int number;
    int x;
    int y;

    public Ball clone() {
        return new Ball(thisDirection, x, y, number);
    }

    @Override
    char getAscii() {
        if (thisDirection == Direction.NORTH) {
            return 'w';
        }
        if (thisDirection == Direction.SOUTH) {
            return 's';
        }
        if (thisDirection == Direction.EAST) {
            return 'd';
        }
        if (thisDirection == Direction.WEST) {
            return 'a';
        }
        return ' ';
    }

    void draw(GraphicsContext gc, int tileSize) {
        gc.drawImage(findImage(), x * tileSize, y * tileSize, tileSize, tileSize);
    }

    void update(GraphicsObjectStorage tb) {
        if (thisDirection == Direction.NORTH) {
            y--;
        }
        if (thisDirection == Direction.SOUTH) {
            y++;
        }
        if (thisDirection == Direction.EAST) {
            x++;
        }
        if (thisDirection == Direction.WEST) {
            x--;
        }
        Random.random = Math.random() > 0.5;
        tb.getTileAtPos(x, y).update(this, tb);
    }

    @Override
    String getName() {
        if (thisDirection == Direction.NORTH) {
            return "BallUp.png";
        }
        if (thisDirection == Direction.SOUTH) {
            return "BallDown.png";
        }
        if (thisDirection == Direction.EAST) {
            return "BallRight.png";
        }
        if (thisDirection == Direction.WEST) {
            return "BallLeft.png";
        }
        throw new Error("Warning: This ball is not facing north or south or east or west");
    }

    Ball(Direction whichDirection, int posX, int posY, int thisNumber) {
        super(whichDirection);
        x = posX;
        y = posY;
        number = thisNumber;
    }
}
