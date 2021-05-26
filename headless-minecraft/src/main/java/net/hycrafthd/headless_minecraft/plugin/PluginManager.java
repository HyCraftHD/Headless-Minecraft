package net.hycrafthd.headless_minecraft.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.LamdbaExceptionUtils.Consumer_WithExceptions;
import net.hycrafthd.headless_minecraft.common.HeadlessEnvironment;
import net.hycrafthd.headless_minecraft.common.PluginFile;

public class PluginManager {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	private static final Map<HeadlessPlugin, PluginFile> LOADED_PLUGINS = new HashMap<>();
	
	public static void load() {
		LOGGER.info("Started to load plugins");
		
		final Set<PluginFile> files = Launcher.INSTANCE.environment().getProperty(HeadlessEnvironment.PLUGINS.get()).orElseThrow(() -> new IllegalStateException("The plugins key must be set"));
		files.stream() //
				.filter(file -> file.getPluginMainClass().isPresent()) //
				.forEach(file -> {
					try {
						final String className = file.getPluginMainClass().get();
						final Class<? extends HeadlessPlugin> mainClass = Class.forName(className).asSubclass(HeadlessPlugin.class);
						LOADED_PLUGINS.put(mainClass.getDeclaredConstructor().newInstance(), file);
					} catch (ClassCastException | ReflectiveOperationException ex) {
						LOGGER.error("Plugin {} at {} contains an invalid plugin main class", file.getFileName(), file.getPath().toAbsolutePath(), ex);
					} catch (Exception ex) {
						LOGGER.warn("Plugin {} threw an exception", file.getFileName(), ex);
					}
				});
		
		runPluginMethod(HeadlessPlugin::load);
		
		LOGGER.info("Finished loading plugins");
	}
	
	public static void enable() {
		LOGGER.info("Finished loading minecraft. Call plugin finished loading methods");
		runPluginMethod(HeadlessPlugin::enable);
	}
	
	private static void runPluginMethod(Consumer_WithExceptions<HeadlessPlugin, Exception> consumer) {
		LOADED_PLUGINS.forEach((plugin, file) -> {
			try {
				consumer.accept(plugin);
			} catch (Exception ex) {
				LOGGER.warn("Plugin {} threw an exception", file.getFileName(), ex);
			}
		});
	}
}
