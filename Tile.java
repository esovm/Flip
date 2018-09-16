import java.awt.*;

abstract class Tile extends GraphicsObject{
    static Tile[] subClasses;
    Tile(Direction whichDirection) {
        super(whichDirection);
    }
    static void init() {
        subClasses = new Tile[]{new Empty(Direction.NORTHSOUTHEASTWEST),
                new Sluce(Direction.NORTH), new Sluce(Direction.SOUTH),
                new Sluce(Direction.EAST), new Sluce(Direction.WEST),
                new True(Direction.NORTHSOUTHEASTWEST), new Random(Direction.NORTHSOUTHEASTWEST),
                new Flipper(Direction.NORTHSOUTH), new Flipper(Direction.EASTWEST),
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
    abstract boolean getModifier(Ball b);

    abstract void update(Ball b, GraphicsObjectStorage tb);

    public abstract Tile clone();

    static boolean getModifierToRight(Ball b, GraphicsObjectStorage tb, Direction d) {
        Direction dR1 = Direction.rotateRight(d);
        Direction dR2 = Direction.rotateRight(dR1);
        Point currentPoint = Direction.add(d.pos,dR1.pos);
        Boolean first = tb.getTileAtPos(currentPoint.x+b.x,currentPoint.y+b.y).getModifier(b);
        currentPoint = Direction.add(dR1.pos, dR2.pos);
        Boolean second = tb.getTileAtPos(currentPoint.x+b.x,currentPoint.y+b.y).getModifier(b);
        return first ^ second;
    }

    static boolean getModifierToLeft(Ball b, GraphicsObjectStorage tb, Direction d) {
        Direction dR1 = Direction.rotateLeft(d);
        Direction dR2 = Direction.rotateLeft(dR1);
        Point currentPoint = Direction.add(d.pos,dR1.pos);
        Boolean first = tb.getTileAtPos(currentPoint.x+b.x,currentPoint.y+b.y).getModifier(b);
        currentPoint = Direction.add(dR1.pos, dR2.pos);
        Boolean second = tb.getTileAtPos(currentPoint.x+b.x,currentPoint.y+b.y).getModifier(b);
        return first ^ second;
    }

    static Tile create(char ascii, int sizeTile) {
        for (Tile sc : subClasses) {
            if (sc.getAscii() == ascii) {
                return sc.clone();
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST,ascii);
    }
}
