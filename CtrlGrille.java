public class CtrlGrille extends Tile {
    CtrlGrille(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        if(b.number <= 0) {
            tb.removeExactBall(b);
        }
    }

    @Override
    String getName() {
        return "CtrlGrille.png";
    }

    @Override
    public Tile clone() {
        return new CtrlGrille(thisDirection);
    }

    @Override
    char getAscii() {
        return '#';
    }
}
