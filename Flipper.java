import java.awt.*;

public class Flipper extends Tile {
    Flipper(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        if(thisDirection == Direction.NORTHSOUTH) {
            if(b.thisDirection == Direction.NORTH || b.thisDirection == Direction.SOUTH) {
                b.thisDirection = Direction.rotateLeft(b.thisDirection);
            } else if(b.thisDirection == Direction.EAST || b.thisDirection == Direction.WEST) {
                b.thisDirection = Direction.rotateRight(b.thisDirection);
            }
        }
        if(thisDirection == Direction.EASTWEST) {
            if(b.thisDirection == Direction.NORTH || b.thisDirection == Direction.SOUTH) {
                b.thisDirection = Direction.rotateRight(b.thisDirection);
            } else if(b.thisDirection == Direction.EAST || b.thisDirection == Direction.WEST) {
                b.thisDirection = Direction.rotateLeft(b.thisDirection);
            }
        }
        Point whichDirection = new Point(0,0);
        if(thisDirection == Direction.NORTHSOUTH) {
            whichDirection = new Point(1,1);
        }
        if(thisDirection == Direction.EASTWEST) {
            whichDirection = new Point(1,-1);
        }
        if(tb.getTileAtPos(whichDirection.x + b.x, whichDirection.y + b.y).getModifier(b) ^ tb.getTileAtPos(-whichDirection.x + b.x, -whichDirection.y + b.y).getModifier(b)) {
            tb.place(new Flipper(Direction.flip(thisDirection)),b.x, b.y);
        }
    }

    @Override
    String getName() {
        if(thisDirection == Direction.NORTHSOUTH) {
            return "FlipperUpDown.png";
        }
        if(thisDirection == Direction.EASTWEST) {
            return "FlipperLeftRight.png";
        }
        return "";
    }

    @Override
    public Tile clone() {
        return new Flipper(thisDirection);
    }

    @Override
    char getAscii() {
        if(thisDirection == Direction.NORTHSOUTH) {
            return '\\';
        }
        if(thisDirection == Direction.EASTWEST) {
            return '/';
        }
        return ' ';
    }
}
