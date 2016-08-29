package ua.ivolynets.modfx.control.document;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Class intended for documents management. Documents are opened as tabs.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class DocumentController {
	
	/**
	 * Tabs pane component.
	 */
	private TabPane tabs;
	
	/**
	 * Constructs document controller.
	 * @param tabs	Tabs pane component.
	 */
	public DocumentController(TabPane tabs) {
		this.tabs = tabs;
	}
	
	/**
	 * Adds a new document as a tab and returns reference to it.
	 * 
	 * @param title		Document title.
	 * @param icon		Document icon.
	 * @param layout	Document layout URL.
	 * @return	Reference to the document object.
	 */
	public Document addDocument(String title, Image icon, URL layout) {
		
		try {
			
			final Tab tab = new Tab(title);
			
			if (icon != null) {
				final ImageView imageView = new ImageView(icon);
				tab.setGraphic(imageView);
			}
			
			final FXMLLoader loader = new FXMLLoader(layout);
			loader.load();
			
			final Parent root = loader.getRoot();
			
			AnchorPane.setTopAnchor(root, 0d);
			AnchorPane.setBottomAnchor(root, 0d);
			AnchorPane.setLeftAnchor(root, 0d);
			AnchorPane.setRightAnchor(root, 0d);
			
			final AnchorPane anchor = new AnchorPane();
			anchor.getChildren().add(root);
			
			tab.setContent(anchor);
			this.tabs.getTabs().add(tab);
			
			return new Document(tab, root, loader.getController());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Adds a new document as a tab and returns reference to it.
	 * 
	 * @param title		Document title.
	 * @param icon		Document icon resource.
	 * @param layout	Document layout resource.
	 * @return	Reference to the document object.
	 */
	public Document addDocument(String title, String icon, String layout) {
		
		final Image image = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream(icon));
		final URL descriptor = Thread.currentThread().getContextClassLoader().getResource(layout);
		return this.addDocument(title, image, descriptor);
		
	}
	
	/**
	 * Adds a new document as a tab and returns reference to it.
	 * 
	 * @param title		Document title.
	 * @param layout	Document layout URL.
	 * @return	Reference to the document object.
	 */
	public Document addDocument(String title, URL layout) {
		return this.addDocument(title, null, layout);
	}
	
	/**
	 * Adds a new document as a tab and returns reference to it.
	 * 
	 * @param title		Document title.
	 * @param layout	Document layout resource.
	 * @return	Reference to the document object.
	 */
	public Document addDocument(String title, String layout) {
		return this.addDocument(title, null, layout);
	}

}
