public class Wall extends Tile {
    Wall(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
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
    public Tile clone(int sizeTile) {
        return new Wall(thisDirection, sizeTile);
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
