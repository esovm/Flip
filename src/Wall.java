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

public class Wall extends Tile {
    Wall(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        if(thisDirection == Direction.EASTWEST) {
            return b.number < 0;
        } else {
            return false;
        }
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        if(
                ((b.thisDirection == Direction.NORTH || b.thisDirection == Direction.SOUTH)
                && thisDirection == Direction.NORTHSOUTH)
                ||
                ((b.thisDirection == Direction.EAST || b.thisDirection == Direction.WEST)
                && thisDirection == Direction.EASTWEST)) {
            System.err.println("Balls should not hit walls from side directions");
        } else {
            b.thisDirection = Direction.flip(b.thisDirection);
        }
    }

    @Override
    String getName() {
        if(thisDirection == Direction.NORTHSOUTH) {
            return "WallUpDown.png";
        }
        if(thisDirection == Direction.EASTWEST) {
            return "WallLeftRight.png";
        }
        return "";
    }

    @Override
    public Tile clone() {
        return new Wall(thisDirection);
    }

    @Override
    char getAscii() {
        if(thisDirection == Direction.NORTHSOUTH) {
            return '|';
        }
        if(thisDirection == Direction.EASTWEST) {
            return '-';
        }
        return ' ';
    }
}
