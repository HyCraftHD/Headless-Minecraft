package net.hycrafthd.headless_minecraft.launcher.loading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PluginDiscovery {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private final Set<PluginFile> files;
	
	PluginDiscovery() {
		files = new HashSet<>();
	}
	
	void discoverJars(Stream<Path> stream) {
		stream.filter(Files::isRegularFile) //
				.filter(path -> path.endsWith(".jar")) //
				.map(path -> {
					LOGGER.debug("Consider {} as jar plugin source", path);
					try {
						return new JarPluginFile(path, new JarFile(path.toFile()));
					} catch (IOException ex) {
						LOGGER.warn("Plugin file {} is not readable", path);
						LOGGER.debug("Plugin file threw exception while opening", ex);
					}
					return null;
				}) //
				.filter(file -> file != null) //
				.forEach(files::add);
	}
	
	void discoverDirectories(Stream<Path> stream) {
		stream.filter(Files::isDirectory) //
				.map(path -> {
					LOGGER.debug("Consider {} as directory plugin source", path);
					return new DirectoryPluginFile(path);
				}) //
				.forEach(files::add);
	}
	
	public Set<PluginFile> getFiles() {
		return files;
	}
	
}
