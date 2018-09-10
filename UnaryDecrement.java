public class UnaryDecrement extends Tile {
    UnaryDecrement(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        b.number--;
    }

    @Override
    String getName() {
        return "UnaryDecrement.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new UnaryDecrement(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return ',';
    }
}
