import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class GraphicsObject {
    static GraphicsObject[] subClasses;
    Direction thisDirection;
    private Image img;

    abstract String getName();

    static void init() {
        subClasses = new GraphicsObject[]{
                new Empty(Direction.NORTHSOUTHEASTWEST),
                new Ball(Direction.NORTH, 0, 0, 0), new Ball(Direction.SOUTH, 0, 0, 0),
                new Ball(Direction.EAST, 0, 0, 0), new Ball(Direction.WEST, 0, 0, 0),
                new Sluce(Direction.NORTH), new Sluce(Direction.SOUTH),
                new Sluce(Direction.EAST), new Sluce(Direction.WEST),
                new True(Direction.NORTHSOUTHEASTWEST), new Random(Direction.NORTHSOUTHEASTWEST),
                new Flipper(Direction.NORTHSOUTH),new Flipper(Direction.EASTWEST),
                new Wall(Direction.NORTHSOUTH), new Wall(Direction.EASTWEST),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 0), new NumGen(Direction.NORTHSOUTHEASTWEST, 1),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 2), new NumGen(Direction.NORTHSOUTHEASTWEST, 3),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 4), new NumGen(Direction.NORTHSOUTHEASTWEST, 5),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 6), new NumGen(Direction.NORTHSOUTHEASTWEST, 7),
                new NumGen(Direction.NORTHSOUTHEASTWEST, 8), new NumGen(Direction.NORTHSOUTHEASTWEST, 9),
                new TarpitAdd(Direction.NORTHSOUTHEASTWEST), new TarpitMult(Direction.NORTHSOUTHEASTWEST),
                new UnaryNegate(Direction.NORTHSOUTHEASTWEST), new UnaryReset(Direction.NORTHSOUTHEASTWEST),
                new UnaryDecrement(Direction.NORTHSOUTHEASTWEST), new UnaryIncrement(Direction.NORTHSOUTHEASTWEST),
                new PrintNum(Direction.NORTHSOUTHEASTWEST), new PrintAscii(Direction.NORTHSOUTHEASTWEST),
                new ReadNum(Direction.NORTHSOUTHEASTWEST), new ReadAscii(Direction.NORTHSOUTHEASTWEST),
                new ControlTerm(Direction.NORTHSOUTHEASTWEST), new CtrlGrille(Direction.NORTHSOUTHEASTWEST),
                new Processor(Direction.NORTHSOUTHEASTWEST)};
    }

    public abstract GraphicsObject clone();

    abstract char getAscii();

    Image findImage() {
        return new Image(this.getClass().getResourceAsStream("/Icons/" + getName()));
    }


    void draw(GraphicsContext gc, int x, int y, int tileSize) {
        gc.drawImage(img, x * tileSize, y * tileSize, tileSize, tileSize);
    }

    GraphicsObject(Direction whichDirection) {
        thisDirection = whichDirection;
        img = findImage();
    }

    static GraphicsObject create(char ascii) {
        for (GraphicsObject sc : subClasses) {
            if (sc.getAscii() == ascii) {
                return sc;
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST, ascii);
    }


}
