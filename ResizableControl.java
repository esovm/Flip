import javafx.scene.control.Control;

public class ResizableControl {
	protected ResizableInt width;
	protected ResizableInt height;
	protected ResizableInt x;
	protected ResizableInt y;
	protected Control c;
	public void resize(int unitX, int unitY) {
		c.setPrefWidth(width.calculate(unitX));
		c.setPrefHeight(height.calculate(unitY));
		c.relocate(x.calculate(unitX), y.calculate(unitY));
	}
	public ResizableControl(Control control, ResizableInt w, ResizableInt h, ResizableInt posX, ResizableInt posY) {
		c = control;
		width = w;
		height = h;
		x = posX;
		y = posY;
	}
}
