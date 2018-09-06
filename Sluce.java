public class Sluce extends Tile {
    Sluce(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        b.thisDirection = thisDirection;
    }

    @Override
    String getName() {
        if(thisDirection == Direction.NORTH) {
            return "SluceUp.png";
        }
        if(thisDirection == Direction.SOUTH) {
            return "SluceDown.png";
        }
        if(thisDirection == Direction.EAST) {
            return "SluceRight.png";
        }
        if(thisDirection == Direction.WEST) {
            return "SluceLeft.png";
        }
        return "";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new Sluce(thisDirection,sizeTile);
    }

    static Direction[] getAllowedDirections() {
        return new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST,Direction.WEST};
    }

    @Override
    char getAscii() {
        if(thisDirection == Direction.NORTH) {
            return '^';
        }
        if(thisDirection == Direction.SOUTH) {
            return 'v';
        }
        if(thisDirection == Direction.EAST) {
            return '>';
        }
        if(thisDirection == Direction.WEST) {
            return '<';
        }
        return ' ';
    }
}
