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

import java.awt.*;

enum Direction {
    NORTH(0,-1), SOUTH(0,1), EAST(1,0), WEST(-1,0), NORTHSOUTH(0,0), EASTWEST(0,0), NORTHSOUTHEASTWEST(0,0); // R: NORTHSOUTHEASTWEST is funny and confusing. Maybe NONE?

    Point pos;
    Direction(int x, int y) {
        pos = new Point(x,y);
    }

    @Override
    public String toString() {
        return name();
    }

    static Point add(Point a, Point b) {
        return new Point(a.x+b.x, a.y+b.y);
    }

    static Direction getString(String s) {
        Direction d = NORTH;
        if(s.equals(d.toString())) {
            return d;
        }
        d = SOUTH;
        if(s.equals(d.toString())) {
            return d;
        }
        d = EAST;
        if(s.equals(d.toString())) {
            return d;
        }
        d = WEST;
        if(s.equals(d.toString())) {
            return d;
        }
        d = NORTHSOUTH;
        if(s.equals(d.toString())) {
            return d;
        }
        d = EASTWEST;
        if(s.equals(d.toString())) {
            return d;
        }
        return Direction.NORTHSOUTHEASTWEST;
    }
    static Direction rotateLeft(Direction d) {
        Direction newD = d;
        if(d == NORTH) {
            newD = WEST;
        }
        if(d == SOUTH) {
            newD = EAST;
        }
        if(d == EAST) {
            newD = NORTH;
        }
        if(d == WEST) {
            newD = SOUTH;
        }
        if(d == NORTHSOUTH) {
            newD = EASTWEST;
        }
        if(d == EASTWEST) {
            newD = NORTHSOUTH;
        }
        return newD;
    }
    static Direction rotateRight(Direction d) {
        Direction newD = d;
        if(d == WEST) {
            newD = NORTH;
        }
        if(d == EAST) {
            newD = SOUTH;
        }
        if(d == NORTH) {
            newD = EAST;
        }
        if(d == SOUTH) {
            newD = WEST;
        }
        if(d == NORTHSOUTH) {
            newD = EASTWEST;
        }
        if(d == EASTWEST) {
            newD = NORTHSOUTH;
        }
        return newD;
    }
    static Direction flip(Direction d) {
        Direction newD = d;
        if(d == NORTH) {
            newD = SOUTH;
        }
        if(d == SOUTH) {
            newD = NORTH;
        }
        if(d == EAST) {
            newD = WEST;
        }
        if(d == WEST) {
            newD = EAST;
        }
        if(d == NORTHSOUTH) {
            newD = EASTWEST;
        }
        if(d == EASTWEST) {
            newD = NORTHSOUTH;
        }
        return newD;
    }
}
