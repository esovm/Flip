import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class GraphicsObject {
    Direction thisDirection;
    int tileSize;

    abstract String getName();

    static Direction[] getAllowedDirections() {
        return new Direction[0];
    }

    Image findImage() {
        return new Image(this.getClass().getResourceAsStream("\\Icons\\"+getName()));
    }


    void draw(GraphicsContext gc,int x, int y) {
        gc.drawImage(findImage(), x * tileSize, y * tileSize, tileSize, tileSize);
    }

    GraphicsObject(Direction whichDirection, int sizeTile) {
        thisDirection = whichDirection;
        tileSize = sizeTile;
    }


}
