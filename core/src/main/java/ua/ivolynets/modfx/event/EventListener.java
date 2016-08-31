package ua.ivolynets.modfx.event;

/**
 * Interface represents event listener.
 * 
 * @param <T>	Event type.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public interface EventListener<T extends Event> {
	
	/**
	 * Processes received event.
	 * @param event	Event instance.
	 */
	public void process(T event);

}
