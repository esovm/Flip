public class Sluce extends Tile {
    Sluce(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        if(b.thisDirection == Direction.flip(thisDirection)) {
            boolean left = getModifierToLeft(b, tb, b.thisDirection);
            boolean right = getModifierToRight(b, tb, b.thisDirection);
            if (left ^ right) {
                if (left) {
                    b.thisDirection = Direction.rotateLeft(b.thisDirection);
                }
                if (right) {
                    b.thisDirection = Direction.rotateRight(b.thisDirection);
                }
            } else {
                b.thisDirection = thisDirection;
            }
        } else {
            b.thisDirection = thisDirection;
        }
    }

    @Override
    String getName() {
        if (thisDirection == Direction.NORTH) {
            return "SluceUp.png";
        }
        if (thisDirection == Direction.SOUTH) {
            return "SluceDown.png";
        }
        if (thisDirection == Direction.EAST) {
            return "SluceRight.png";
        }
        if (thisDirection == Direction.WEST) {
            return "SluceLeft.png";
        }
        return "";
    }

    @Override
    public Tile clone() {
        return new Sluce(thisDirection);
    }

    @Override
    char getAscii() {
        if (thisDirection == Direction.NORTH) {
            return '^';
        }
        if (thisDirection == Direction.SOUTH) {
            return 'v';
        }
        if (thisDirection == Direction.EAST) {
            return '>';
        }
        if (thisDirection == Direction.WEST) {
            return '<';
        }
        return ' ';
    }
}
