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
import javafx.scene.paint.Color;

import java.awt.*;

class SelectOverlay {
    private Point start = new Point(-1, -1);
    private Point end = new Point(-1, -1);
    private int tileSize;
    private static final Color c = Color.color(0, 0, 1, 0.5);
    private Flip flip;

    SelectOverlay(Flip f, int sizeTile) {
        flip = f;
        tileSize = sizeTile;
    }

    void select(int x, int y) {
        clear();
        start = new Point(x, y);
        end = start;
    }

    void fill(GraphicsObject go) {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        if (e.x - s.x == 1 && e.y - s.y == 1) {
            if (go instanceof Empty) {
                flip.storage.remove(s.x, s.y);
            } else {
                flip.storage.place(go.clone(), s.x, s.y);
            }
            start = new Point(start.x + 1, start.y);
            end = new Point(end.x + 1, end.y);
        } else if (go instanceof Empty) {
            erase();
        } else {
            for (int a = s.x; a < e.x; a++) {
                for (int b = s.y; b < e.y; b++) {
                    flip.storage.place(go.clone(), a, b);
                }
            }
        }
        flip.draw();
    }

    void cut() {
        copy();
        erase();
        flip.draw();
    }

    void paste() {
        Point s = arrangeStart();
        flip.storage.pasteRect(s.x, s.y);
        flip.draw();
    }

    void copy() {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        flip.storage.copyRect(s.x, s.y, e.x, e.y);
        flip.draw();
    }

    void drag(int x, int y) {
        end = new Point(x, y);
    }

    void clear() {
        start = new Point(-1, -1);
        end = new Point(-1, -1);
    }

    private void erase() {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        flip.storage.removeRect(s.x, s.y, e.x, e.y);
    }

    void delete() {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        flip.storage.deleteRect(s.x, s.y, e.x, e.y);
        flip.draw();
    }

    private Point arrangeStart() {
        int startX = start.x;
        int startY = start.y;
        int endX = end.x;
        int endY = end.y;
        if (endX < startX) {
            startX = endX;
        }
        if (endY < startY) {
            startY = endY;
        }
        return new Point(startX, startY);
    }



    private Point arrangeEnd() {
        int startX = start.x;
        int startY = start.y;
        int endX = end.x;
        int endY = end.y;
        if (endX < startX) {
            endX = startX;
        }
        if (endY < startY) {
            endY = startY;
        }
        return new Point(endX + 1, endY + 1);
    }

    void draw(GraphicsContext gc) {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        gc.setFill(c);
        gc.fillRect(s.x * tileSize, s.y * tileSize, e.x * tileSize - s.x * tileSize, e.y * tileSize - s.y * tileSize);
    }
}
