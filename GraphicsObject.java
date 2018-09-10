import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class GraphicsObject {
    Direction thisDirection;
    int tileSize;
    Image img;

    abstract String getName();

    public abstract GraphicsObject clone(int sizeTile);

    abstract char getAscii();

    Image findImage() {
        return new Image(this.getClass().getResourceAsStream("\\Icons\\" + getName()));
    }


    void draw(GraphicsContext gc, int x, int y) {
        gc.drawImage(img, x * tileSize, y * tileSize, tileSize, tileSize);
    }

    GraphicsObject(Direction whichDirection, int sizeTile) {
        thisDirection = whichDirection;
        tileSize = sizeTile;
        img = findImage();
    }

    static GraphicsObject create(char ascii) {
        GraphicsObject[] subClasses = new GraphicsObject[]{
                new Empty(Direction.NORTHSOUTHEASTWEST, 0),
                new Ball(Direction.NORTH, 0, 0, 0, 0), new Ball(Direction.SOUTH, 0, 0, 0, 0),
                new Ball(Direction.EAST, 0, 0, 0, 0), new Ball(Direction.WEST, 0, 0, 0, 0),
                new Sluce(Direction.NORTH, 0), new Sluce(Direction.SOUTH, 0),
                new Sluce(Direction.EAST, 0), new Sluce(Direction.WEST, 0),
                new True(Direction.NORTHSOUTHEASTWEST, 0),
                new Flipper(Direction.NORTHSOUTH,0),new Flipper(Direction.EASTWEST,0),
                new Wall(Direction.NORTHSOUTH,0), new Wall(Direction.EASTWEST, 0),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 0), new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 1),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 2), new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 3),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 4), new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 5),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 6), new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 7),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 8), new NumGen(Direction.NORTHSOUTHEASTWEST, 0, 9),
                new TarpitAdd(Direction.NORTHSOUTHEASTWEST, 0), new TarpitMult(Direction.NORTHSOUTHEASTWEST, 0)};
        for (GraphicsObject sc : subClasses) {
            if (sc.getAscii() == ascii) {
                return sc;
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST, 0, ascii);
    }


}
