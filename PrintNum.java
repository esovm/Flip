import javafx.scene.control.TextArea;

public class PrintNum extends Tile {
    private TextArea toPrint = Flip.output;

    PrintNum(Direction whichDirection, int sizeTile) {
        super(whichDirection, sizeTile);
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
        return new PrintNum(thisDirection, sizeTile);
    }

    @Override
    char getAscii() {
        return 'p';
    }
}
