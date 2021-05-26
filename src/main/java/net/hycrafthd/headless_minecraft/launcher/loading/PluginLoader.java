package net.hycrafthd.headless_minecraft.launcher.loading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.modlauncher.api.IEnvironment;
import net.hycrafthd.headless_minecraft.common.HeadlessEnvironment;
import net.hycrafthd.headless_minecraft.common.PluginFile;

public class PluginLoader {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private final Path pluginDirectory;
	private final Set<Path> extraPluginPaths;
	
	private final PluginDiscovery discovery;
	
	private final List<Entry<String, Path>> pluginScanEntries;
	
	PluginLoader(Path pluginDirectory, Set<Path> extraPluginPaths) {
		this.pluginDirectory = pluginDirectory;
		this.extraPluginPaths = extraPluginPaths;
		discovery = new PluginDiscovery();
		pluginScanEntries = new ArrayList<>();
	}
	
	void discover(List<Entry<String, Path>> headlessMinecraftEntries) {
		LOGGER.info("Discover plugins");
		
		// Scan plugin directory
		try (final Stream<Path> stream = Files.walk(pluginDirectory, 1)) {
			discovery.discoverJars(stream);
		} catch (IOException ex) {
			throw new IllegalStateException("Cannot access plugin directory", ex);
		}
		
		// Scan extra plugin paths
		discovery.discoverJars(extraPluginPaths.stream());
		discovery.discoverDirectories(extraPluginPaths.stream());
		
		// Collect headless entries and all discovered plugins to plugin scan entries
		pluginScanEntries.addAll(headlessMinecraftEntries);
		discovery.getFiles().stream().map(file -> new SimpleImmutableEntry<>(file.getFileName(), file.getPath()));
		
		// Scan headless minecraft entries
		discovery.discoverJars(headlessMinecraftEntries.stream().map(Entry::getValue));
		discovery.discoverDirectories(headlessMinecraftEntries.stream().map(Entry::getValue));
	}
	
	void initPluginFiles(IEnvironment environment) {
		environment.computePropertyIfAbsent(HeadlessEnvironment.PLUGINS.get(), key -> discovery.getFiles().stream() //
				.filter(file -> file.getPluginMainClass().isPresent()) //
				.collect(Collectors.toSet()));
	}
	
	public List<Entry<String, Path>> getPluginScanEntries() {
		return pluginScanEntries;
	}
	
	public Set<PluginFile> getPluginFiles() {
		return discovery.getFiles();
	}
	
}
