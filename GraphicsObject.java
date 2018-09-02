import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class GraphicsObject {
    Direction thisDirection;
    int x;
    int y;
    int tileSize;

    abstract String getName();

    static Direction[] getAllowedDirections() {
        return new Direction[0];
    }

    private Image findImage() {
        return new Image(this.getClass().getResourceAsStream("\\Icons\\"+getName()));
    }


    void draw(GraphicsContext gc) {
        gc.drawImage(findImage(), x * tileSize, y * tileSize, tileSize, tileSize);
    }

    GraphicsObject(Direction whichDirection, int posX, int posY, int sizeTile) {
        thisDirection = whichDirection;
        x = posX;
        y = posY;
        tileSize = sizeTile;
    }


}
