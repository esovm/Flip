import java.util.OptionalInt;

public class TarpitMult extends Tile {
    OptionalInt i;
    TarpitMult(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
        i = OptionalInt.empty();
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        if(i.isPresent()) {
            b.number *= i.getAsInt();
            i = OptionalInt.empty();
        } else {
            i = OptionalInt.of(b.number);
            tb.removeExactBall(b);
        }
    }

    @Override
    String getName() {
        return "TarpitMult.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new TarpitMult(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '*';
    }
}
