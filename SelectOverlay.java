import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class SelectOverlay{
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int tileSize;
    private static final Color c = Color.color(0.5,0.5,0.5,0.5);
    private TileAndBallStorage tb;
    SelectOverlay(TileAndBallStorage tileAndBallStorage, int sizeTile) {
        tb = tileAndBallStorage;
        tileSize = sizeTile;
        startX = 0;
        startY = 0;
        endX = 0;
        endY = 0;
    }
    void selectStart(int x, int y) {
        startX = x;
        startY = y;
    }
    void drag(int x, int y) {
        endX = x;
        endY = y;
    }
    void erase() {
        arrangeSelect();
        tb.removeRect(startX, startY, endX, endY);
    }
    void delete() {
        arrangeSelect();
        tb.deleteRect(startX, startY, endX, endY);
    }
    private void arrangeSelect() {
        if(startX > endX) {
            int x = startX;
            startX = endX;
            endX = x;
        }
        if(startY > endY) {
            int y = startY;
            startY = endY;
            endY = y;
        }
    }
    void click(int x, int y) {
        startX = x;
        startY = y;
        endX = x+1;
        endY = y+1;
    }
    void draw(GraphicsContext gc) {
        gc.setFill(c);
        gc.fillRect(startX*tileSize, startY*tileSize, endX*tileSize, endY*tileSize);
    }
}
