package ua.ivolynets.modfx.task;

import java.util.concurrent.FutureTask;

import javafx.application.Platform;
import javafx.event.EventHandler;

/**
 * Class represents a task handler.
 * @author Igor Volynets
 */
public class TaskHandler extends FutureTask<Object> {
	
	/**
	 * Task name.
	 */
	private String name;
	
	/**
	 * Task instance.
	 */
	private Task task;
	
	/**
	 * Task finished event handler.
	 */
	private EventHandler<TaskFinishedEvent> onFinished;
	
	/**
	 * Task cancelled event handler.
	 */
	private EventHandler<TaskCancelledEvent> onCancelled;
	
	/**
	 * Constructs task handler.
	 * 
	 * @param name	Task name.
	 * @param task	Task instance.
	 */
	public TaskHandler(String name, Task task) {
		
		super(task, null);
		
		this.name = name;
		this.task = task;
		
	}

	/**
	 * @return Task name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return Task instance.
	 */
	public Task getTask() {
		return this.task;
	}

	/**
	 * Sets task finished event handler.
	 * @param handler Event handler.
	 */
	public void setOnFinished(EventHandler<TaskFinishedEvent> handler) {
		this.onFinished = handler;
	}

	/**
	 * Sets task finished event handler.
	 * @param handler Event handler.
	 */
	public void setOnCancelled(EventHandler<TaskCancelledEvent> handler) {
		this.onCancelled = handler;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		
		boolean result = super.cancel(mayInterruptIfRunning);
		Platform.runLater(() -> { onCancelled.handle(new TaskCancelledEvent()); });
		return result;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void done() {
		
		super.done();
		Platform.runLater(() -> { onFinished.handle(new TaskFinishedEvent()); });
		
	}

}
