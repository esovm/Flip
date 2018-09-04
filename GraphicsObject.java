import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class GraphicsObject {
    Direction thisDirection;
    int tileSize;

    abstract String getName();

    public abstract GraphicsObject clone(int sizeTile);

    abstract char getAscii();

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

    static GraphicsObject create(char ascii) {
        GraphicsObject[] subClasses = new GraphicsObject[]{new Empty(Direction.NORTHSOUTHEASTWEST, 0),
                new Ball(Direction.NORTH, 0,0,0,0), new Ball(Direction.SOUTH, 0,0,0,0),
                new Ball(Direction.EAST, 0,0,0,0), new Ball(Direction.WEST, 0,0,0,0) };
        for (GraphicsObject sc : subClasses) {
            if (sc.getAscii() == ascii) {
                return sc;
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST,0,ascii);
    }


}
