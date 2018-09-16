public class Wall extends Tile {
    Wall(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        if(thisDirection == Direction.EASTWEST) {
            return b.number < 0;
        } else {
            return false;
        }
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        if(
                ((b.thisDirection == Direction.NORTH || b.thisDirection == Direction.SOUTH)
                && thisDirection == Direction.NORTHSOUTH)
                ||
                ((b.thisDirection == Direction.EAST || b.thisDirection == Direction.WEST)
                && thisDirection == Direction.EASTWEST)) {
            System.err.println("Balls should not hit walls from side directions");
        } else {
            b.thisDirection = Direction.flip(b.thisDirection);
        }
    }

    @Override
    String getName() {
        if(thisDirection == Direction.NORTHSOUTH) {
            return "WallUpDown.png";
        }
        if(thisDirection == Direction.EASTWEST) {
            return "WallLeftRight.png";
        }
        return "";
    }

    @Override
    public Tile clone() {
        return new Wall(thisDirection);
    }

    @Override
    char getAscii() {
        if(thisDirection == Direction.NORTHSOUTH) {
            return '|';
        }
        if(thisDirection == Direction.EASTWEST) {
            return '-';
        }
        return ' ';
    }
}
