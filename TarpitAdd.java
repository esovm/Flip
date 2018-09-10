import java.util.OptionalInt;

public class TarpitAdd extends Tile {
    OptionalInt i;
    TarpitAdd(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
        i = OptionalInt.empty();
    }

    @Override
    boolean getModifier(Ball b) {
        return b.number > 0;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        if(i.isPresent()) {
            b.number += i.getAsInt();
            i = OptionalInt.empty();
        } else {
            i = OptionalInt.of(b.number);
            tb.removeExactBall(b);
        }
    }

    @Override
    String getName() {
        return "TarpitAdd.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        return new TarpitAdd(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return '+';
    }
}
