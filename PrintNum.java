import javafx.scene.control.TextArea;

public class PrintNum extends Tile {
    private TextArea toPrint;

    PrintNum(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
    }

    void setTextArea(TextArea tf) {
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
        p.setTextArea(toPrint);
        return p;
    }

    @Override
    char getAscii() {
        return 'p';
    }
}
