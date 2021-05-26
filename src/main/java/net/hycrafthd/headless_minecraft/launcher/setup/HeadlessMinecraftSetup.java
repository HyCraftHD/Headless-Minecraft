package net.hycrafthd.headless_minecraft.launcher.setup;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.jar.Manifest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.common.util.ManifestUtil;
import net.hycrafthd.headless_minecraft.launcher.Constants;

public class HeadlessMinecraftSetup {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private final Path cache;
	private final List<Entry<String, Path>> files;
	
	public HeadlessMinecraftSetup(Path cache) {
		this.cache = cache;
		files = new ArrayList<>();
	}
	
	public void findJars() {
		LOGGER.info("Discover required headless minecraft files");
		if (Constants.DEVELOPMENT_MODE) {
			findDev();
		} else {
			findAndSetupProduction();
		}
	}
	
	public List<Entry<String, Path>> getFiles() {
		return files;
	}
	
	private void findDev() {
		files.add(new SimpleImmutableEntry<>("implementation", Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD)));
		files.add(new SimpleImmutableEntry<>("main-plugin", Paths.get(Constants.DEVELOPMENT_MAIN_PLUGIN_BUILD)));
	}
	
	private void findAndSetupProduction() {
		final Collection<Manifest> manifests = ManifestUtil.findClassPathManifests();
		
		final String implementationJar = manifests.stream().filter(manifest -> {
			return manifest.getMainAttributes().containsKey(Constants.PRODUCTION_IMPLEMENTATION_JAR);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find manifest property " + Constants.PRODUCTION_IMPLEMENTATION_JAR)).getMainAttributes().getValue(Constants.PRODUCTION_IMPLEMENTATION_JAR);
		
		final String mainPluginJar = manifests.stream().filter(manifest -> {
			return manifest.getMainAttributes().containsKey(Constants.PRODUCTION_MAIN_PLUGIN_JAR);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find manifest property " + Constants.PRODUCTION_MAIN_PLUGIN_JAR)).getMainAttributes().getValue(Constants.PRODUCTION_MAIN_PLUGIN_JAR);
		
		try {
			files.add(new SimpleImmutableEntry<>("implementation", extract(implementationJar, cache)));
			files.add(new SimpleImmutableEntry<>("main-plugin", extract(mainPluginJar, cache)));
		} catch (IOException ex) {
			throw new IllegalStateException("Cannot extract implementation or main plugin file. Things do not work.", ex);
		}
	}
	
	private Path extract(String resourceName, Path cache) throws IOException {
		final URL url = new URL("classpath:" + resourceName);
		final Path path = cache.resolve(resourceName);
		Files.copy(url.openStream(), path, StandardCopyOption.REPLACE_EXISTING);
		return path;
	}
	
}
