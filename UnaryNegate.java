public class UnaryNegate extends Tile {
    UnaryNegate(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return (b.number&1)==1;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        b.number = -b.number;
    }

    @Override
    String getName() {
        return "UnaryNegate.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new UnaryNegate(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '~';
    }
}
