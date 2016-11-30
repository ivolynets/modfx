package ua.ivolynets.modfx.control.status;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import ua.ivolynets.modfx.ApplicationResources;

/**
 * Controller of the status message bar.
 * @author Igor Volynets
 */
public class StatusMessageController implements Initializable {
	
	@FXML
	private TableView<StatusMessageEntry> console;
	
	/**
	 * List of status messages.
	 */
	private ObservableList<StatusMessageEntry> entries = FXCollections.observableArrayList();
	
	/**
	 * Date and time formatter.
	 */
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		this.console.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.console.setPlaceholder(new Label());
		
		TableColumn<StatusMessageEntry, ImageView> icon = new TableColumn<>();
		icon.setEditable(false);
		icon.setSortable(false);
		icon.setMinWidth(24);
		icon.setMaxWidth(24);
		icon.setPrefWidth(24);
		
		icon.setCellValueFactory(new PropertyValueFactory<StatusMessageEntry, ImageView>("icon"));
		icon.setCellFactory(new Callback<TableColumn<StatusMessageEntry,ImageView>, TableCell<StatusMessageEntry,ImageView>>() {
			
			@Override
			public TableCell<StatusMessageEntry, ImageView> call(TableColumn<StatusMessageEntry, ImageView> param) {
				return new TableCell<StatusMessageEntry, ImageView>() {
					
					@Override
					protected void updateItem(ImageView icon, boolean empty) {
						
						this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						this.setAlignment(Pos.TOP_CENTER);
						
						if ( ! empty && icon != null) {
							this.setGraphic(icon);
						}
						
					}
					
				};
			}
		});
		
		TableColumn<StatusMessageEntry, Calendar> datetime = new TableColumn<>();
		datetime.setEditable(false);
		datetime.setSortable(false);
		datetime.setMinWidth(150);
		datetime.setMaxWidth(150);
		datetime.setPrefWidth(150);
		
		datetime.setCellValueFactory(new PropertyValueFactory<StatusMessageEntry, Calendar>("datetime"));
		datetime.setCellFactory(new Callback<TableColumn<StatusMessageEntry,Calendar>, TableCell<StatusMessageEntry,Calendar>>() {

			@Override
			public TableCell<StatusMessageEntry, Calendar> call(TableColumn<StatusMessageEntry, Calendar> param) {
				return new TableCell<StatusMessageEntry, Calendar>() {
					
					@Override
					protected void updateItem(Calendar datetime, boolean empty) {
						
						this.setAlignment(Pos.TOP_CENTER);
						
						if ( ! empty) {
							this.setText(formatter.format(datetime.getTime()));
						}
						
					}
					
				};
			}
			
		});
		
		TableColumn<StatusMessageEntry, String> message = new TableColumn<>();
		message.setEditable(false);
		message.setSortable(false);
		message.setCellValueFactory(new PropertyValueFactory<StatusMessageEntry, String>("message"));
		
		this.console.getColumns().add(icon);
		this.console.getColumns().add(datetime);
		this.console.getColumns().add(message);
		
		this.console.setItems(this.entries);
		
	}
	
	/**
	 * Prints information message to the status bar.
	 * @param text	Message text.
	 */
	public void information(String text) {
		this.message(ApplicationResources.ICON_MESSAGE_INFORMATION, text);
	}
	
	/**
	 * Prints warning message to the status bar.
	 * @param text	Message text.
	 */
	public void warning(String text) {
		this.message(ApplicationResources.ICON_MESSAGE_WARNING, text);
	}
	
	/**
	 * Prints error message to the status bar.
	 * @param text	Message text.
	 */
	public void error(String text) {
		this.message(ApplicationResources.ICON_MESSAGE_ERROR, text);
	}
	
	/**
	 * Prints message with given icon and text to the status bar.
	 * 
	 * @param icon	Message icon.
	 * @param text	Message text.
	 */
	private void message(final Image icon, final String text) {
		
		Platform.runLater(() -> {
			entries.add(new StatusMessageEntry(icon, Calendar.getInstance(), text));
			console.scrollTo(entries.size() - 1);
		});
		
	}
	
	/**
	 * Inner class represents status message entry.
	 * @author Igor Volynets
	 */
	public class StatusMessageEntry {
		
		private ImageView icon;
		private Calendar datetime;
		private String message;
		
		public StatusMessageEntry(Image icon, Calendar datetime, String message) {
			this.icon = new ImageView(icon);
			this.datetime = datetime;
			this.message = message;
		}

		public ImageView getIcon() {
			return this.icon;
		}

		public void setIcon(ImageView icon) {
			this.icon = icon;
		}

		public Calendar getDatetime() {
			return this.datetime;
		}

		public void setDatetime(Calendar datetime) {
			this.datetime = datetime;
		}

		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	}

}
