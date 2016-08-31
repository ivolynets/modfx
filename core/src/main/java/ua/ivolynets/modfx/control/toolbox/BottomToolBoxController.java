package ua.ivolynets.modfx.control.toolbox;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;

/**
 * Controller of the bottom tool box.
 */
public class BottomToolBoxController extends AbstractToolBoxController {
	
	/**
	 * Constructs tool box controller.
	 * 
	 * @param toolBar	Tool bar the tool box belongs to.
	 * @param panes		Placeholder for tool boxes.
	 */
	public BottomToolBoxController(ToolBar toolBar, final SplitPane panes) {
		
		super(toolBar, panes);
		
		this.toolWindow.getChildren().addListener((ListChangeListener<Node>) (change) -> {
			
			if (change.getList().isEmpty()) {
				panes.getItems().remove(toolWindow);
			} else {
				panes.getItems().add(toolWindow);
				panes.setDividerPosition(0, 0.8);
			}
			
		});
		
	}
	
}
