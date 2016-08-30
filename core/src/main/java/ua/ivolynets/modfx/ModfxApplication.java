package ua.ivolynets.modfx;

import java.io.File;
import java.io.FileFilter;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;
import ua.ivolynets.modfx.event.ApplicationClosedEvent;
import ua.ivolynets.modfx.plugin.ModfxPlugin;

/**
 * Application main class.
 * @author i.volynets
 */
public class ModfxApplication extends Application {
	
	/**
	 * Application context.
	 */
	private GuiContext context;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() throws Exception {
		
		this.context = new GuiContext();
		
		this.context.eventService().start();
		this.context.taskService().start();
		
		this.loadPlugins("../plugins");
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Mod FX Application");
		stage.getIcons().add(ApplicationResources.ICON_APPLICATION);
		
		stage.setScene(this.context.scene());
		stage.show();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() throws Exception {
		
		// Notify listeners about closing the application
		
		this.context.eventService().notify(new ApplicationClosedEvent());
		
		// Stop services
		
		this.context.taskService().stop();
		this.context.eventService().stop();
		
	}

	/**
	 * Loads plugins from the given directory.
	 * @param path	Plugins directory.
	 */
	private void loadPlugins(String path) {
		
		final File dir = new File(path);
		final File[] jars = dir.listFiles((FileFilter) (pathname) -> {
			return pathname.isFile() && pathname.getName().endsWith(".jar");
		});
		
		// Add plugins to classpath
		
		this.context.pluginManager().updateClasspath(jars);
		
		// Load plugins
		
		for (int i = 0; i < jars.length; i++) {
			
			this.notifyPreloader(new ModfxPreloader.MessageNotification("Loading " + jars[i]));
			ModfxPlugin plugin = this.context.pluginManager().load(jars[i]);
			this.notifyPreloader(new Preloader.ProgressNotification(((double) i * 2 + 1) / (jars.length * 2)));
			
			if (plugin != null) {
				
				this.notifyPreloader(new ModfxPreloader.MessageNotification("Initializing plugin " + plugin.name()));
				plugin.init(new GuiContext(plugin.getClass().getCanonicalName(), this.context));
				
			} else {
				this.notifyPreloader(new ModfxPreloader.MessageNotification("Error loading plugin"));
			}
			
			this.notifyPreloader(new Preloader.ProgressNotification(((double) i * 2 + 2) / (jars.length * 2)));
			
		}
		
	}
	
	/**
	 * Application entry point.
	 * @param args	Application startup arguments.
	 */
	public static void main(String ... args) {
		ModfxApplication.launch(args);
	}

}
