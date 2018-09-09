import java.util.OptionalInt;

public class Tarpit extends Tile {
    boolean mult;
    OptionalInt i;
    Tarpit(Direction whichDirection, int sizeTile, boolean multiply) {
        super(whichDirection, sizeTile);
        mult = multiply;
        i = OptionalInt.empty();
        img = findImage();
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        if(i.isPresent()) {
            if(mult) {
                b.number *= i.getAsInt();
            } else {
                b.number += i.getAsInt();
            }
            i = OptionalInt.empty();
        } else {
            i = OptionalInt.of(b.number);
            tb.removeExactBall(b);
        }
    }

    @Override
    String getName() {
        if(mult) {
            return "TarpitMult.png";
        } else {
            return "TarpitAdd.png";
        }
    }

    @Override
    public Tile clone(int sizeTile) {
        return new Tarpit(thisDirection, sizeTile, mult);
    }

    @Override
    char getAscii() {
        if(mult) {
            return '*';
        } else {
            return '+';
        }
    }
}
