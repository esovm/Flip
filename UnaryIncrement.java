public class UnaryIncrement extends Tile {
    UnaryIncrement(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        b.number++;
    }

    @Override
    String getName() {
        return "UnaryIncrement.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new UnaryIncrement(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '\'';
    }
}
