public class Processor extends Tile {
    Processor(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        tb.removeExactBall(b);
        if(!getModifierToLeft(b, tb, b.thisDirection)) {
            tb.place(new Ball(Direction.rotateLeft(b.thisDirection),b.x, b.y, tileSize, b.number), b.x, b.y);
        }
        if(!getModifierToRight(b, tb, b.thisDirection)) {
            tb.place(new Ball(Direction.rotateRight(b.thisDirection),b.x, b.y, tileSize, b.number), b.x, b.y);
        }
    }

    @Override
    String getName() {
        return "Processor.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new Processor(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return 'X';
    }
}
