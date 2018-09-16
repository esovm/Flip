public class True extends Tile {
    True(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return true;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        b.thisDirection = Direction.flip(b.thisDirection);
    }

    @Override
    String getName() {
        return "True.png";
    }

    @Override
    public Tile clone() {
        return new True(thisDirection);
    }

    @Override
    char getAscii() {
        return '@';
    }
}
