public class UnaryNegate extends Tile {
    UnaryNegate(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return (b.number&1)==1;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        b.number = -b.number;
    }

    @Override
    String getName() {
        return "UnaryNegate.png";
    }

    @Override
    public Tile clone() {
        return new UnaryNegate(thisDirection);
    }

    @Override
    char getAscii() {
        return '~';
    }
}
