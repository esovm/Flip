import javafx.scene.control.TextArea;

public class PrintNum extends Tile {
    private TextArea toPrint = Flip.output;

    PrintNum(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        tb.removeExactBall(b);
        toPrint.appendText(b.number + " ");
    }

    @Override
    String getName() {
        return "PrintNum.png";
    }

    @Override
    public Tile clone() {
        return new PrintNum(thisDirection);
    }

    @Override
    char getAscii() {
        return 'p';
    }
}
