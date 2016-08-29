package ua.ivolynets.modfx.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Service responsible for handling application events. It's running in separate thread and delegates received events 
 * processing to registered listeners.
 * 
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class EventService {

	/**
	 * Queue that holds events.
	 */
	private BlockingQueue<Event> queue = new LinkedBlockingQueue<>();
	
	/**
	 * Map of registered listeners.
	 */
	private ConcurrentMap<Class<? extends Event>, List<EventListener<? extends Event>>> listeners = new ConcurrentHashMap<>();
	
	/**
	 * Executing task.
	 */
	private Future<?> task;
	
	/**
	 * Starts the event service.
	 */
	@SuppressWarnings("unchecked")
	public void start() {
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		try {
			
			this.task = executor.submit((Runnable) () -> {
				
				while ( ! Thread.currentThread().isInterrupted()) {
					
					try {
						
						Event event = queue.take();
						
						for (Class<? extends Event> eventType : listeners.keySet()) {
							if (eventType.isAssignableFrom(event.getClass())) {
								for (@SuppressWarnings("rawtypes") EventListener listener : listeners.get(eventType)) {
									listener.process(event);
								}
							}
						}
						
						// Exit loop when application closing event received and handled
						if (event instanceof ApplicationClosedEvent) break;
						
					} catch (InterruptedException ignored) {}
					
				}
				
			});
			
		} finally {
			executor.shutdown();
		}
		
	}
	
	/**
	 * Stops the event service.
	 */
	public void stop() {
		this.task.cancel(true);
	}
	
	/**
	 * Sends a new event to the world.
	 * @param event	Event instance.
	 */
	public void notify(Event event) {
		
		try {
			this.queue.put(event);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Registers a new event listener.
	 * 
	 * @param eventType	Event class instance.
	 * @param listener	Event listener.
	 */
	public void listen(Class<? extends Event> eventType, EventListener<? extends Event> listener) {
		
		this.listeners.putIfAbsent(eventType, Collections.synchronizedList(new ArrayList<>()));
		this.listeners.get(eventType).add(listener);
	}
	
}
