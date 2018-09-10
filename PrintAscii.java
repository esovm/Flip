import javafx.scene.control.TextField;

public class PrintAscii extends Tile {
    private TextField toPrint;

    PrintAscii(Direction whichDirection, int sizeTile) {
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
        toPrint.appendText(Character.toString((char) Math.abs(b.number)));
    }

    @Override
    String getName() {
        return "PrintAscii.png";
    }

    @Override
    public Tile clone(int sizeTile) {
        PrintAscii p = new PrintAscii(thisDirection, sizeTile);
        p.setTextField(toPrint);
        return p;
    }

    @Override
    char getAscii() {
        return 'P';
    }
}
