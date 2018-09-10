import javafx.scene.control.TextField;

public class PrintNum extends Tile {
    private TextField toPrint;

    PrintNum(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    void setTextField(TextField tf) {
        toPrint = tf;
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, TileAndBallStorage tb) {
        tb.removeExactBall(b);
        toPrint.appendText(b.number + " ");
    }

    @Override
    String getName() {
        return "PrintNum.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        PrintNum p = new PrintNum(thisDirection, sizeTile);
        p.setTextField(toPrint);
        return p;
    }

    @Override
    char getAscii() {
        return 'p';
    }
}
