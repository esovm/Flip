import javafx.scene.control.Control;
import javafx.scene.image.ImageView;

public class ResizableButton extends ResizableControl {
	private ImageView i;
	@Override
	public void resize(int unitX, int unitY) {
		int w = width.calculate(unitX);
		int h = height.calculate(unitY);
		int posX = x.calculate(unitX);
		int posY = y.calculate(unitY);
		c.setPrefWidth(w);
		c.setPrefHeight(h);
		c.relocate(posX, posY);
		i.setFitWidth(w);
		i.setFitHeight(h);
	}
	public ResizableButton(Control control, ImageView iv, ResizableInt w, ResizableInt h, ResizableInt posX, ResizableInt posY) {
		super(control, w, h, posX, posY);
		i = iv;
	}
}
