import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GraphicsObject {
    public Direction thisDirection;
    public int x;
    public int y;
    public int tileSize;

    public abstract String getName();

    public static Direction[] getAllowedDirections() {
        return new Direction[0];
    }

    public Image findImage() {
        return new Image(this.getClass().getResourceAsStream("\\Icons\\"+getName()));
    }


    public void draw(GraphicsContext gc) {
        gc.drawImage(findImage(), x * tileSize, y * tileSize, tileSize, tileSize);
    }

    public GraphicsObject(Direction whichDirection, int posX, int posY, int sizeTile) {
        thisDirection = whichDirection;
        x = posX;
        y = posY;
        tileSize = sizeTile;
    }


}
