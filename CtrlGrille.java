public class CtrlGrille extends Tile {
    CtrlGrille(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        if(b.number <= 0) {
            tb.removeExactBall(b);
        }
    }

    @Override
    String getName() {
        return "CtrlGrille.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new CtrlGrille(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '#';
    }
}
