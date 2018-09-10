public class UnaryReset extends Tile {
    UnaryReset(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        b.number = 0;
    }

    @Override
    String getName() {
        return "UnaryReset.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new UnaryReset(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '.';
    }
}
