public class True extends Tile {
    True(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return true;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        b.thisDirection = Direction.flip(b.thisDirection);
    }

    @Override
    String getName() {
        return "True.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new True(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '@';
    }
}
