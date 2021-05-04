package net.hycrafthd.headless_minecraft.plugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PluginManager {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	private static final List<HeadlessPlugin> LOADED_SCRIPTS = new ArrayList<>();
	
	public static void load() {
		LOGGER.info("Started to load plugins");
		
		// TODO currently hardcoded the the main plugin
		try {
			LOADED_SCRIPTS.add(Class.forName("net.hycrafthd.headless_minecraft.test_plugin.TestPlugin").asSubclass(HeadlessPlugin.class).newInstance());
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		
		LOADED_SCRIPTS.forEach(HeadlessPlugin::load);
		
		LOGGER.info("Finished loading plugins");
	}
	
	public static void enable() {
		LOGGER.info("Finished loading minecraft. Call plugin finished loading methods");
		LOADED_SCRIPTS.forEach(e -> e.enable());
	}
}
