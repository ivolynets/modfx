package ua.ivolynets.modfx.control.toolbox;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;

/**
 * Controller of the system tool box.
 */
public class SystemToolBoxController extends AbstractToolBoxController {
	
	/**
	 * Constructs tool box controller.
	 * 
	 * @param toolBar	Tool bar the tool box belongs to.
	 * @param panes		Placeholder for tool boxes.
	 */
	public SystemToolBoxController(ToolBar toolBar, final SplitPane panes) {
		
		super(toolBar, panes);
		
		this.toolWindow.getChildren().addListener((ListChangeListener<Node>) (change) -> {
			
			if (change.getList().isEmpty()) {
				panes.getItems().remove(toolWindow);
			} else {
				panes.getItems().add(0, toolWindow);
				panes.setDividerPosition(0, 0.8);
			}
			
		});
		
	}
	
}
