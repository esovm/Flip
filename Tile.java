import java.awt.*;

abstract class Tile extends GraphicsObject{

    Tile(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    abstract boolean getModifier(Ball b);

    abstract void update(Ball b, TileAndBallStorage tb);

    public abstract Tile clone(int sizeTile);

    static boolean getModifierToRight(Ball b, TileAndBallStorage tb, Direction d) {
        Direction dR1 = Direction.rotateRight(d);
        Direction dR2 = Direction.rotateRight(dR1);
        Point currentPoint = Direction.add(d.pos,dR1.pos);
        Boolean first = tb.getTileAtPos(currentPoint.x+b.x,currentPoint.y+b.y).getModifier(b);
        currentPoint = Direction.add(dR1.pos, dR2.pos);
        Boolean second = tb.getTileAtPos(currentPoint.x+b.x,currentPoint.y+b.y).getModifier(b);
        return first ^ second;
    }

    static boolean getModifierToLeft(Ball b, TileAndBallStorage tb, Direction d) {
        Direction dR1 = Direction.rotateLeft(d);
        Direction dR2 = Direction.rotateLeft(dR1);
        Point currentPoint = Direction.add(d.pos,dR1.pos);
        Boolean first = tb.getTileAtPos(currentPoint.x+b.x,currentPoint.y+b.y).getModifier(b);
        currentPoint = Direction.add(dR1.pos, dR2.pos);
        Boolean second = tb.getTileAtPos(currentPoint.x+b.x,currentPoint.y+b.y).getModifier(b);
        return first ^ second;
    }

    static Tile create(char ascii, int sizeTile) {
        Tile[] subClasses = new Tile[]{new Empty(Direction.NORTHSOUTHEASTWEST, 0),
                new Sluce(Direction.NORTH, 0), new Sluce(Direction.SOUTH, 0),
                new Sluce(Direction.EAST, 0), new Sluce(Direction.WEST, 0),
                new True(Direction.NORTHSOUTHEASTWEST, 0),
                new Flipper(Direction.NORTHSOUTH,0),new Flipper(Direction.EASTWEST,0)};
        for (Tile sc : subClasses) {
            if (sc.getAscii() == ascii) {
                return sc.clone(sizeTile);
            }
        }
        return new Comment(Direction.NORTHSOUTHEASTWEST,sizeTile,ascii);
    }
}
