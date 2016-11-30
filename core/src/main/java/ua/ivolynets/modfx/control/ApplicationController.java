package ua.ivolynets.modfx.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import ua.ivolynets.modfx.GuiContext;
import ua.ivolynets.modfx.control.dialog.DialogController;
import ua.ivolynets.modfx.control.document.DocumentController;
import ua.ivolynets.modfx.control.menu.MenuController;
import ua.ivolynets.modfx.control.status.StatusBar;
import ua.ivolynets.modfx.control.toolbar.ToolBarController;
import ua.ivolynets.modfx.control.toolbox.BottomToolBoxController;
import ua.ivolynets.modfx.control.toolbox.LeftToolBoxController;
import ua.ivolynets.modfx.control.toolbox.RightToolBoxController;
import ua.ivolynets.modfx.control.toolbox.SystemToolBoxController;
import ua.ivolynets.modfx.control.toolbox.ToolBoxController;
import ua.ivolynets.modfx.control.window.WindowController;

/**
 * Controller for the main application window.
 * @author Igor Volynets
 */
public class ApplicationController implements Initializable {
	
	@FXML
	private MenuBar mainMenu;
	
	@FXML
	private ToolBar toolBar;
	
	@FXML
	private ToolBar leftToolBox;
	
	@FXML
	private ToolBar rightToolBox;
	
	@FXML
	private ToolBar bottomToolBox;
	
	@FXML
	private ToolBar systemToolBox;
	
	@FXML
	private SplitPane verticalPanes;
	
	@FXML
	private SplitPane horizontalPanes;
	
	@FXML
	private TabPane tabs;
	
	@FXML
	private ImageView statusIcon;
	
	@FXML
	private Label statusMessage;
	
	/**
	 * Application context.
	 */
	private GuiContext context;
	
	/**
	 * Main menu controller.
	 */
	private MenuController mainMenuController;
	
	/**
	 * Tool bar controller.
	 */
	private ToolBarController toolBarController;
	
	/**
	 * Left tool box controller.
	 */
	private ToolBoxController leftToolBoxController;
	
	/**
	 * Right tool box controller.
	 */
	private ToolBoxController rightToolBoxController;
	
	/**
	 * Bottom tool box controller.
	 */
	private ToolBoxController bottomToolBoxController;
	
	/**
	 * System tool box controller.
	 */
	private ToolBoxController systemToolBoxController;
	
	/**
	 * Document controller.
	 */
	private DocumentController documentController;
	
	/**
	 * Window controller.
	 */
	private WindowController windowController;
	
	/**
	 * Dialog controller.
	 */
	private DialogController dialogController;
	
	/**
	 * Status bar.
	 */
	private StatusBar statusBar;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//
	}
	
	/**
	 * Explicitly initializes application controller.
	 * @param context	Application context.
	 */
	public void init(GuiContext context) {
		
		this.context = context;
		
		this.bottomToolBox.minHeightProperty().bind(this.systemToolBox.heightProperty());
		
		this.mainMenuController = new MenuController(this.mainMenu);
		this.toolBarController = new ToolBarController(this.toolBar);
		
		this.leftToolBoxController = new LeftToolBoxController(this.leftToolBox, this.horizontalPanes);
		this.rightToolBoxController = new RightToolBoxController(this.rightToolBox, this.horizontalPanes);
		this.bottomToolBoxController = new BottomToolBoxController(this.bottomToolBox, this.verticalPanes);
		this.systemToolBoxController = new SystemToolBoxController(this.systemToolBox, this.verticalPanes);
		
		this.documentController = new DocumentController(this.tabs);
		this.windowController = new WindowController(this.context);
		
		this.statusBar = new StatusBar(this.statusIcon, this.statusMessage, this.systemToolBoxController);
		
	}
	
	/**
	 * @return	Main menu controller.
	 */
	public MenuController mainMenu() {
		return this.mainMenuController;
	}
	
	/**
	 * @return	Tool bar controller.
	 */
	public ToolBarController toolBar() {
		return this.toolBarController;
	}
	
	/**
	 * @return	Left tool box controller.
	 */
	public ToolBoxController leftToolBox() {
		return this.leftToolBoxController;
	}
	
	/**
	 * @return	Right tool box controller.
	 */
	public ToolBoxController rightToolBox() {
		return this.rightToolBoxController;
	}
	
	/**
	 * @return	Bottom tool box controller.
	 */
	public ToolBoxController bottomToolBox() {
		return this.bottomToolBoxController;
	}
	
	/**
	 * @return	System tool box controller.
	 */
	public ToolBoxController systemToolBox() {
		return this.systemToolBoxController;
	}
	
	/**
	 * Controller allows to manage application tabs.
	 * @return	Document controller.
	 */
	public DocumentController documents() {
		return this.documentController;
	}
	
	/**
	 * Controller allows to open different pop-up windows.
	 * @return	Window controller.
	 */
	public WindowController windows() {
		return this.windowController;
	}
	
	/**
	 * Controller allows to display different user dialogs and alerts.
	 * @return	Dialog controller.
	 */
	public DialogController dialogs() {
		if (this.dialogController == null) this.dialogController = DialogController.getInstance(this.context);
		return this.dialogController;
	}
	
	/**
	 * Controller allows to print messages of different severity to the status bar.
	 * @return	Status bar controller.
	 */
	public StatusBar statusBar() {
		return this.statusBar;
	}

}
