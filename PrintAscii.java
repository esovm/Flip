import javafx.scene.control.TextArea;

public class PrintAscii extends Tile {
    private TextArea toPrint = Flip.output;

    PrintAscii(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        tb.removeExactBall(b);
        char c = (char) Math.abs(b.number);
        toPrint.setText(toPrint.getText() + c);
    }

    @Override
    String getName() {
        return "PrintAscii.png";
    }

    @Override
    public Tile clone() {
        return new PrintAscii(thisDirection);
    }

    @Override
    char getAscii() {
        return 'P';
    }
}
