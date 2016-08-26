package ua.ivolynets.modfx.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;

import javafx.fxml.FXMLLoader;

/**
 * Plugin manager.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class PluginManager {
	
	/**
	 * Reference to the class loader.
	 */
	private URLClassLoader loader;

	/**
	 * A list of loaded plugins.
	 */
	private List<ModfxPlugin> plugins = new ArrayList<>();
	
	/**
	 * Adds new JAR's to the plugin manager's class loader. It is recommended to call this method before loading a new 
	 * plugin, especially if you plan to load several plugins at once.
	 * 
	 * @param jars	Array of JAR files.
	 */
	public void updateClasspath(File[] jars) {
		
		try {
			
			URL[] urls = new URL[jars.length];
			for (int i = 0; i < jars.length; i++) urls[i].toURI().toURL();
			
			this.loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
			Thread.currentThread().setContextClassLoader(this.loader);
			FXMLLoader.setDefaultClassLoader(this.loader);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Loads a plugin. If the specified JAR is not in the classpath yet then it will be added implicitly.
	 * 
	 * Please note that this method does not perform plugin initialization, it should be called explicitly if needed.
	 * 
	 * @param jar	Plugin containing JAR file.
	 * @return	Loaded plugin's main class instance.
	 */
	public ModfxPlugin load(File jar) {
		
		try {
			
			if (this.loader == null || Arrays.binarySearch(this.loader.getURLs(), jar.toURI().toURL(), new Comparator<URL>() {
				
				public int compare(URL o1, URL o2) {
					if (o1.sameFile(o2)) return 0;
					else return -1;
				}
				
			}) == -1) {
				this.updateClasspath(new File[] {jar});
			}
			
			final String className = this.getPluginClassName(jar);
			Class<?> pluginClass = this.loader.loadClass(className);
			ModfxPlugin plugin = (ModfxPlugin) pluginClass.newInstance();
			
			this.plugins.add(plugin);
			return plugin;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Returns the list of plugins that were loaded so far.
	 * @return	List of loaded plugins.
	 */
	public List<ModfxPlugin> getLoadedPlugins() {
		return Collections.unmodifiableList(this.plugins);
	}
	
	/**
	 * Extracts plugin main class name from a given plugin JAR.
	 * 
	 * @param jar	Plugin containing JAR file.
	 * @return	Plugin's main class name.
	 */
	private String getPluginClassName(File jar) {
		
		try (JarInputStream stream = new JarInputStream(new FileInputStream(jar))) {
			
			Attributes attributes = stream.getManifest().getMainAttributes();
			return attributes.getValue("Plugin-Class");
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
