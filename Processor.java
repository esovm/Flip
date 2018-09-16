public class Processor extends Tile {
    Processor(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        tb.removeExactBall(b);
        if(!getModifierToLeft(b, tb, b.thisDirection)) {
            tb.place(new Ball(Direction.rotateLeft(b.thisDirection),b.x, b.y, b.number), b.x, b.y);
        }
        if(!getModifierToRight(b, tb, b.thisDirection)) {
            tb.place(new Ball(Direction.rotateRight(b.thisDirection),b.x, b.y, b.number), b.x, b.y);
        }
    }

    @Override
    String getName() {
        return "Processor.png";
    }

    @Override
    public Tile clone() {
        return new Processor(thisDirection);
    }

    @Override
    char getAscii() {
        return 'X';
    }
}
