package ua.ivolynets.modfx.task;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event which is indicating that task is finished.
 * @author Igor Volynets
 */
public class TaskFinishedEvent extends Event {

	/**
	 * Constructs event.
	 */
	public TaskFinishedEvent() {
		super(new EventType<TaskFinishedEvent>(EventType.ROOT));
	}
	
}
