package ua.ivolynets.modfx.control.toolbox;

import java.io.IOException;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * Implements basic functionality of the tool box controller. 
 * @author Igor Volynets
 */
public abstract class AbstractToolBoxController implements ToolBoxController {
	
	/**
	 * Tool bar the tool box belongs to.
	 */
	protected final ToolBar toolBar;
	
	/**
	 * Placeholder for tool boxes.
	 */
	protected final SplitPane horizontalPanes;
	
	/**
	 * Group for tool box toggle buttons.
	 */
	protected final ToggleGroup group = new ToggleGroup();
	
	/**
	 * Tool box top-level container.
	 */
	protected final AnchorPane toolWindow = new AnchorPane();
	
	/**
	 * Constructs tool box controller.
	 * 
	 * @param toolBar	Tool bar the tool box belongs to.
	 * @param panes		Placeholder for tool boxes.
	 */
	public AbstractToolBoxController(ToolBar toolBar, SplitPane panes) {
		this.toolBar = toolBar;
		this.horizontalPanes = panes;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tool addTool(String caption, Image icon, URL layout) {
		
		try {
			
			final ToggleButton toggleButton = this.getButton(caption, icon);
			
			final FXMLLoader loader = new FXMLLoader(layout);
			loader.load();
			
			final Parent root = loader.getRoot();
			
			AnchorPane.setTopAnchor(root, 0d);
			AnchorPane.setBottomAnchor(root, 0d);
			AnchorPane.setLeftAnchor(root, 0d);
			AnchorPane.setRightAnchor(root, 0d);
			
			toggleButton.selectedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
				
				if (newValue) {
					toolWindow.getChildren().add(root);
				} else {
					toolWindow.getChildren().remove(root);
				}
				
			});
			
			final Group group = new Group();
			group.getChildren().add(toggleButton);
			this.toolBar.getItems().add(group);
			
			return new Tool(this.toolWindow, toggleButton, root, loader.getController());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tool addTool(String caption, String icon, String layout) {
		
		final Image image = new Image(icon);
		final URL descriptor = Thread.currentThread().getContextClassLoader().getResource(layout);
		return this.addTool(caption,  image, descriptor);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tool addTool(String caption, URL layout) {
		return this.addTool(caption,  null, layout);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tool addTool(String caption, String layout) {
		return this.addTool(caption,  null, layout);
	}

	/**
	 * Creates and returns tool box button component.
	 * 
	 * @param caption	Button caption.
	 * @param icon		Button icon.
	 * @return	Button component instance.
	 */
	protected ToggleButton getButton(String caption, Image icon) {
		
		final ToggleButton button = icon != null ? new ToggleButton(caption, this.getIcon(icon)) : new ToggleButton(caption);
		button.setFocusTraversable(false);
		button.setMnemonicParsing(false);
		button.setFont(new Font(10));
		button.setToggleGroup(this.group);
		
		return button;
		
	}
	
	/**
	 * Wraps button icon and returns its view.
	 * 
	 * @param icon	Button icon.
	 * @return	Icon view.
	 */
	protected ImageView getIcon(Image icon) {
		return new ImageView(icon);
	}

}
