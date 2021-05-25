package net.hycrafthd.headless_minecraft.launcher.setup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PluginSetup {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static PluginSetup INSTANCE;
	
	public static PluginSetup initialize(Set<Path> extraFiles, Path pluginDirectory) {
		return INSTANCE = new PluginSetup(extraFiles, pluginDirectory);
	}
	
	public static PluginSetup getInstance() {
		return INSTANCE;
	}
	
	private final Path pluginDirectory;
	private final Set<Path> extraFiles;
	
	private final Map<Path, JarFile> pluginCanidates;
	
	public PluginSetup(Set<Path> extraFiles, Path pluginDirectory) {
		this.pluginDirectory = pluginDirectory;
		this.extraFiles = extraFiles;
		pluginCanidates = new HashMap<>();
	}
	
	public void discover() {
		try (final Stream<Path> stream = Files.walk(pluginDirectory, 0)) {
			Stream.concat(stream, extraFiles.stream()) //
					.filter(Files::isRegularFile) //
					.filter(path -> path.endsWith(".jar")) //
					.forEach(path -> {
						LOGGER.debug("Consider {} as plugin file", path);
						try {
							pluginCanidates.put(path, new JarFile(path.toFile()));
						} catch (IOException ex) {
							LOGGER.warn("Plugin file {} is not readable", path);
							LOGGER.debug("Plugin file threw exception while opening", ex);
						}
					});
		} catch (IOException ex) {
			throw new IllegalStateException("Cannot access plugin directory", ex);
		}
	}
	
	public Map<Path, JarFile> getPluginCanidates() {
		return pluginCanidates;
	}
	
}
