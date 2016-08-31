package ua.ivolynets.modfx.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import ua.ivolynets.modfx.ApplicationResources;
import ua.ivolynets.modfx.GuiContext;
import ua.ivolynets.modfx.control.dialog.DialogStatus;
import ua.ivolynets.modfx.control.toolbox.Tool;

/**
 * Service responsible for handling background tasks. It's running a thread pool and submits tasks to it. It also 
 * controls execution of submitted tasks.
 * 
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class TaskService {

	/**
	 * Application context.
	 */
	private final GuiContext context;
	
	/**
	 * Reference to the executor service.
	 */
	private ExecutorService executorService;
	
	/**
	 * Tasks tool component.
	 */
	private Tool tasks;
	
	/**
	 * Tasks list size binding.
	 */
	private IntegerBinding binding;
	
	/**
	 * Cached tasks icon.
	 */
	private final ImageView tasksIcon = new ImageView(ApplicationResources.ICON_TASKS);
	
	/**
	 * Map of cached count icons.
	 */
	private final Map<Integer, ImageView> countIcons = new HashMap<>();
	
	/**
	 * Constructs task service.
	 * @param context	Application context.
	 */
	public TaskService(GuiContext context) {
		this.context = context;
	}
	
	/**
	 * Starts the task service.
	 */
	public void start() {
		
		this.executorService = Executors.newCachedThreadPool();
		this.tasks = this.context.controls().systemToolBox().addTool("Tasks", ApplicationResources.ICON_TASKS, ApplicationResources.FXML_TASKS);
		
		final TaskMonitorController controller = this.tasks.getController();
		this.binding = Bindings.size(controller.getTaskHandlers());
		
		final ToggleButton button = this.tasks.getButton();
		
		this.binding.addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
			
			Platform.runLater(() -> {
				
				StackPane stack = new StackPane();
				stack.getChildren().add(tasksIcon);
				ImageView count = getCountIcon(newValue.intValue());
				
				// Add additional icon if there is at least one task
				
				if (count != null) {
					stack.getChildren().add(count);
				}
				
				button.setGraphic(stack);
				
			});
			
		});
		
	}
	
	/**
	 * Stops the task service. 
	 */
	public void stop() throws InterruptedException {
		
		this.executorService.shutdown();
		
		// Ask before force termination
		
		while ( ! this.executorService.awaitTermination(5, TimeUnit.SECONDS)) {
			if (this.context.controls().dialogs().warning("There are background tasks that still running. Do you want to terminate them immediatly?", "Terminate", "Wait", null) == DialogStatus.POSITIVE) {
				this.executorService.shutdownNow();
			}
		}
		
	}
	
	/**
	 * Sends task for execution.
	 * 
	 * @param name	Task name.
	 * @param task	Task instance.
	 */
	public void execute(String name, Task task) {
		
		final TaskHandler item = new TaskHandler(name, task);
		final TaskMonitorController controller = this.tasks.getController();
		controller.getTaskHandlers().add(item);
		
		item.setOnFinished((event) -> { controller.getTaskHandlers().remove(item); });
		item.setOnCancelled((event) -> { controller.getTaskHandlers().remove(item); });
		
		this.executorService.execute(item);
		
	}
	
	/**
	 * Returns cached count icon. If icon does not exist then it will be created.
	 * 
	 * @param count	Count number.
	 * @return	Icon corresponding to count.
	 */
	private ImageView getCountIcon(int count) {
		
		if (count == 0) return null;
		if (count > 20) count = 21;
		
		if ( ! this.countIcons.containsKey(count)) {
			
			ImageView icon = new ImageView(new Image("icons/count/" + count + ".png"));
			
			icon.setTranslateX((16 - icon.getImage().getWidth()) / 2);
			icon.setTranslateY((16 - icon.getImage().getHeight()) / 2);
			
			this.countIcons.put(count, icon);
			
		}
		
		return this.countIcons.get(count);
		
	}
	
}
