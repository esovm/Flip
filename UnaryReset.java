public class UnaryReset extends Tile {
    UnaryReset(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        b.number = 0;
    }

    @Override
    String getName() {
        return "UnaryReset.png";
    }

    @Override
    public Tile clone() {
        return new UnaryReset(thisDirection);
    }

    @Override
    char getAscii() {
        return '.';
    }
}
