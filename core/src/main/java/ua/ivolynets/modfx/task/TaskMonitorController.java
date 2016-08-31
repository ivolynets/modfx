package ua.ivolynets.modfx.task;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.util.Callback;

/**
 * Controller class for the tasks monitor.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class TaskMonitorController implements Initializable {

	@FXML
	private TableView<TaskHandler> tasksTable;
	
	/**
	 * List of running tasks.
	 */
	private final ObservableList<TaskHandler> taskHandlers = FXCollections.observableArrayList();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		this.tasksTable.setPlaceholder(new Label("No background tasks"));
		this.tasksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn<TaskHandler, String> name = new TableColumn<>();
		name.setEditable(false);
		name.setSortable(false);
		
		name.setCellValueFactory(new PropertyValueFactory<TaskHandler, String>("name"));
		
		TableColumn<TaskHandler, Task> progress = new TableColumn<>();
		progress.setEditable(false);
		progress.setSortable(false);
		
		progress.setCellValueFactory(new PropertyValueFactory<TaskHandler, Task>("task"));
		progress.setCellFactory(new Callback<TableColumn<TaskHandler,Task>, TableCell<TaskHandler,Task>>() {
			
			@Override
			public TableCell<TaskHandler, Task> call(TableColumn<TaskHandler, Task> param) {
				return new TableCell<TaskHandler, Task>() {
					
					@Override
					protected void updateItem(Task task, boolean empty) {
						
						this.setAlignment(Pos.BASELINE_LEFT);
						
						if ( ! empty) {
							this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
							this.setGraphic(task.getProgressBar());
						}
						
					}
					
				};
			}
		});
		
		this.tasksTable.getColumns().add(name);
		this.tasksTable.getColumns().add(progress);
		
		this.tasksTable.setItems(this.taskHandlers);
		
	}

	/**
	 * @return List of running tasks.
	 */
	public ObservableList<TaskHandler> getTaskHandlers() {
		return this.taskHandlers;
	}
	
}
