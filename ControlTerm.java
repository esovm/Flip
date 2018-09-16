import javafx.scene.control.TextArea;
public class ControlTerm extends Tile {
    TextArea ta = Flip.output;
    Flip f;

    ControlTerm(Direction whichDirection) {
        super(whichDirection);
    }

    @Override
    boolean getModifier(Ball b) {
        return false;
    }

    void setFlip(Flip flip) {
        f = flip;
    }

    @Override
    void update(Ball b, GraphicsObjectStorage tb) {
        tb.removeExactBall(b);
        ta.appendText("\nProcess finished with exit code " + b.number);
        f.stopRunning();
    }

    @Override
    String getName() {
        return "CtrlTerm.png";
    }

    @Override
    public Tile clone() {
        ControlTerm q = new ControlTerm(thisDirection);
        q.setFlip(f);
        return q;
    }

    @Override
    char getAscii() {
        return 'Q';
    }
}
