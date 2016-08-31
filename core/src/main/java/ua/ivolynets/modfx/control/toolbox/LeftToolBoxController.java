package ua.ivolynets.modfx.control.toolbox;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller of the left tool box.
 */
public class LeftToolBoxController extends AbstractToolBoxController {
	
	/**
	 * Constructs tool box controller.
	 * 
	 * @param toolBar	Tool bar the tool box belongs to.
	 * @param panes		Placeholder for tool boxes.
	 */
	public LeftToolBoxController(ToolBar toolBar, final SplitPane panes) {
		
		super(toolBar, panes);
		
		this.toolWindow.getChildren().addListener((ListChangeListener<Node>) (change) -> {
			
			if (change.getList().isEmpty()) {
				panes.getItems().remove(toolWindow);
			} else {
				panes.getItems().add(0, toolWindow);
				panes.setDividerPosition(0, 0.2);
			}
			
		});
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ToggleButton getButton(String caption, Image icon) {
		
		final ToggleButton button = super.getButton(caption, icon);
		button.setRotate(-90);
		
		return button;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ImageView getIcon(Image icon) {
		
		final ImageView image = super.getIcon(icon);
		image.setRotate(90);
		
		return image;
		
	}
	
}
