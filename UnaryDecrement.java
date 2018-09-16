public class UnaryDecrement extends Tile {
    UnaryDecrement(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        b.number--;
    }

    @Override
    String getName() {
        return "UnaryDecrement.png";
    }

    @Override
    public Tile clone() {
        return new UnaryDecrement(thisDirection);
    }

    @Override
    char getAscii() {
        return ',';
    }
}
