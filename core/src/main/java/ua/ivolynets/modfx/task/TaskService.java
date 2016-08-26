package ua.ivolynets.modfx.task;

import java.util.concurrent.ExecutorService;

import javafx.beans.binding.IntegerBinding;
import javafx.scene.image.ImageView;
import ua.ivolynets.modfx.ApplicationResources;
import ua.ivolynets.modfx.GuiContext;
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
	private GuiContext context;
	
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
	
}
