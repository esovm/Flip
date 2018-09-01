import javafx.scene.paint.Color;

public class SelectOverlay{
    int startX = 0;
    int startY = 0;
    int endX = 0;
    int endY = 0;
    int tileSize;
    private static final Color c = Color.color(0.5,0.5,0.5,0.5);
    private TileAndBallStorage tb;
    public SelectOverlay(TileAndBallStorage tileAndBallStorage, int sizeTile) {
        tb = tileAndBallStorage;
        tileSize = sizeTile;
    }
    public void selectStart(int x, int y) {

    }
    public void drag(int x, int y) {

    }
    public void erase() {

    }
    public void delete() {

    }
}
