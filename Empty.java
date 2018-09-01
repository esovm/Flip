public class Empty extends Tile {
    @Override
    public int getAscii() {
        return ' ';
    }

    @Override
    public void update(Ball b, TileAndBallStorage tb) {
        return;
    }

    @Override
    public Tile clone(int posX, int posY, int sizeTile) {
        return new Empty(thisDirection,posX,posY,sizeTile);
    }

    @Override
    public String getName() {
        return "Empty.jpg";
    }

    public static Direction[] getAllowedDirections() {
        return new Direction[]{Direction.NORTHSOUTHEASTWEST};
    }
    public Empty(Direction whichDirection, int posX, int posY, int sizeTile) {
        super(whichDirection, posX, posY, sizeTile);
    }

    @Override
    public boolean getModifier(Ball b) {
        return false;
    }
}
