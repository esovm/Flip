public class UnaryIncrement extends Tile {
    UnaryIncrement(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        b.number++;
    }

    @Override
    String getName() {
        return "UnaryIncrement.png";
    }

    @Override
    public Tile clone() {
        return new UnaryIncrement(thisDirection);
    }

    @Override
    char getAscii() {
        return '\'';
    }
}
