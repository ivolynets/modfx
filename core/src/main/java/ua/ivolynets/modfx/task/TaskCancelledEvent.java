package ua.ivolynets.modfx.task;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event which is indicating that task is cancelled.
 * @author Igor Volynets
 */
public class TaskCancelledEvent extends Event {

	/**
	 * Constructs event.
	 */
	public TaskCancelledEvent() {
		super(new EventType<TaskCancelledEvent>(EventType.ROOT));
	}
	
}
