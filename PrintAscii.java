import javafx.scene.control.TextArea;

public class PrintAscii extends Tile {
    private TextArea toPrint;

    PrintAscii(Direction whichDirection, int sizeTile) {
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
        toPrint.appendText(Character.toString((char) Math.abs(b.number)));
    }

    @Override
    String getName() {
        return "PrintAscii.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        PrintAscii p = new PrintAscii(thisDirection, sizeTile);
        p.setTextArea(toPrint);
        return p;
    }

    @Override
    char getAscii() {
        return 'P';
    }
}
