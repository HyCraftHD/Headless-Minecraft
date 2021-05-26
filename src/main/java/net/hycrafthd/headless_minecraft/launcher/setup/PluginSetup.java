package net.hycrafthd.headless_minecraft.launcher.setup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	
	private final List<Entry<String, Path>> pluginScanEntries;
	private final Map<Path, JarFile> pluginCanidates;
	
	public PluginSetup(Set<Path> extraFiles, Path pluginDirectory) {
		this.pluginDirectory = pluginDirectory;
		this.extraFiles = extraFiles;
		pluginScanEntries = new ArrayList<>();
		pluginCanidates = new HashMap<>();
	}
	
	public void discover() {
		pluginCanidates.putAll(searchPaths(extraFiles.stream()));
		
		try (final Stream<Path> stream = Files.walk(pluginDirectory, 0)) {
			final Map<Path, JarFile> pluginPaths = searchPaths(stream);
			pluginCanidates.putAll(pluginPaths);
			pluginPaths.entrySet().stream().map(entry -> new SimpleImmutableEntry<String, Path>(entry.getKey().getFileName().toString(), entry.getKey()));
		} catch (IOException ex) {
			throw new IllegalStateException("Cannot access plugin directory", ex);
		}
	}
	
	private Map<Path, JarFile> searchPaths(Stream<Path> stream) {
		final Map<Path, JarFile> files = new HashMap<>();
		stream.filter(Files::isRegularFile) //
				.filter(path -> path.endsWith(".jar")) //
				.forEach(path -> {
					LOGGER.debug("Consider {} as plugin file", path);
					try {
						files.put(path, new JarFile(path.toFile()));
					} catch (IOException ex) {
						LOGGER.warn("Plugin file {} is not readable", path);
						LOGGER.debug("Plugin file threw exception while opening", ex);
					}
				});
		return files;
	}
	
	public List<Entry<String, Path>> getPluginScanEntries() {
		return pluginScanEntries;
	}
	
	public Map<Path, JarFile> getPluginCanidates() {
		return pluginCanidates;
	}
	
}
