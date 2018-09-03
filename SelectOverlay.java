import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Point2D;

class SelectOverlay{
    private Point start = new Point(-1,-1);
    private Point end = new Point(-1,-1);
    private int tileSize;
    private static final Color c = Color.color(0,0,1,0.5);
    private TileAndBallStorage tb;
    SelectOverlay(TileAndBallStorage tileAndBallStorage, int sizeTile) {
        tb = tileAndBallStorage;
        tileSize = sizeTile;
    }
    void select(int x, int y) {
        clear();
        start = new Point(x,y);
        end = start;
    }
    void fillMove(GraphicsObject go) {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        for(int a = s.x; a <= e.x; a++) {
            for(int b = s.y; b <= e.y; b++) {
                tb.place(go.clone(tb.tileSize),a,b);
            }
        }
        start = new Point(end.x, start.y);
        end = new Point(2*end.x, end.y);
    }
    void cut() {

    }
    void paste() {

    }
    void drag(int x, int y) {
        end = new Point(x,y);
    }
    void clear() {
       start = new Point(-1,-1);
       end = new Point(-1,-1);
    }
    void erase() {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        tb.removeRect(s.x, s.y, e.x, e.y);
    }
    void delete() {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        tb.deleteRect(s.x, s.y, e.x, e.y);
    }
    Point arrangeStart() {
        int startX = start.x;
        int startY = start.y;
        int endX = end.x;
        int endY = end.y;
        if(endX < startX) {
            startX = endX;
        }
        if(endY < startY) {
            startY = endY;
        }
        return new Point(startX, startY);
    }
    Point arrangeEnd() {
        int startX = start.x;
        int startY = start.y;
        int endX = end.x;
        int endY = end.y;
        if(endX < startX) {
            endX = startX;
        }
        if(endY < startY) {
            endY = startY;
        }
        return new Point(endX+1, endY+1);
    }
    void draw(GraphicsContext gc) {
        Point s = arrangeStart();
        Point e = arrangeEnd();
        gc.setFill(c);
        gc.fillRect(s.x*tileSize, s.y*tileSize, e.x*tileSize-s.x*tileSize, e.y*tileSize-s.y*tileSize);
    }
}
