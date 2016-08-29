package ua.ivolynets.modfx.control.event;

/**
 * Global application event. This event and its extensions can be used for communication between different modules 
 * since its name is known in advance.
 * 
 * @param <T>	Type of event payload.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class GlobalEvent<T> implements Event {
	
	/**
	 * Module which sent the event.
	 */
	private String sender;
	
	/**
	 * Event type.
	 */
	private String type;
	
	/**
	 * Event payload.
	 */
	private T payload;

	/**
	 * Constructs global event with payload.
	 * 
	 * @param sender	Module which sent the event.
	 * @param type		Event type.
	 * @param payload	Event payload.
	 */
	public GlobalEvent(String sender, String type, T payload) {
		this.sender = sender;
		this.type = type;
		this.payload = payload;
	}

	/**
	 * Constructs global event without payload.
	 * 
	 * @param sender	Module which sent the event.
	 * @param type		Event type.
	 */
	public GlobalEvent(String sender, String type) {
		this(sender, type, null);
	}

	/**
	 * @return Module which sent the event.
	 */
	public String getSender() {
		return this.sender;
	}

	/**
	 * @return Event type.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return Event payload.
	 */
	public T getPayload() {
		return this.payload;
	}
	
}
