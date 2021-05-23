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

import cpw.mods.modlauncher.api.IEnvironment;
import net.hycrafthd.headless_minecraft.common.HeadlessEnvironment;
import net.hycrafthd.headless_minecraft.common.util.ManifestUtil;
import net.hycrafthd.headless_minecraft.launcher.Constants;

public class HeadlessMinecraftSetup {
	
	public static List<Entry<String, Path>> run(IEnvironment environment) {
		final List<Entry<String, Path>> entries = new ArrayList<>();
		
		if (Constants.DEVELOPMENT_MODE) {
			populateDev(entries);
		} else {
			populateProduction(environment, entries);
		}
		
		return entries;
	}
	
	private static void populateDev(List<Entry<String, Path>> entries) {
		entries.add(new SimpleImmutableEntry<>("implementation", Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD)));
		entries.add(new SimpleImmutableEntry<>("main-plugin", Paths.get(Constants.DEVELOPMENT_MAIN_PLUGIN_BUILD)));
	}
	
	private static void populateProduction(IEnvironment environment, List<Entry<String, Path>> entries) {
		final Collection<Manifest> manifests = ManifestUtil.findClassPathManifests();
		
		final String implementationJar = manifests.stream().filter(manifest -> {
			return manifest.getMainAttributes().containsKey(Constants.PRODUCTION_IMPLEMENTATION_JAR);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find manifest property " + Constants.PRODUCTION_IMPLEMENTATION_JAR)).getMainAttributes().getValue(Constants.PRODUCTION_IMPLEMENTATION_JAR);
		
		final String mainPluginJar = manifests.stream().filter(manifest -> {
			return manifest.getMainAttributes().containsKey(Constants.PRODUCTION_MAIN_PLUGIN_JAR);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find manifest property " + Constants.PRODUCTION_MAIN_PLUGIN_JAR)).getMainAttributes().getValue(Constants.PRODUCTION_MAIN_PLUGIN_JAR);
		
		final Path cache = environment.getProperty(HeadlessEnvironment.CACHE_DIR.get()).orElseThrow(() -> new IllegalStateException("Cache directory must be set"));
		
		try {
			entries.add(new SimpleImmutableEntry<>("implementation", extract(implementationJar, cache)));
			entries.add(new SimpleImmutableEntry<>("main-plugin", extract(mainPluginJar, cache)));
		} catch (IOException ex) {
			throw new IllegalStateException("Cannot extract implementation or main plugin file. Things do not work.", ex);
		}
	}
	
	private static Path extract(String resourceName, Path cache) throws IOException {
		final URL url = new URL("classpath:" + resourceName);
		final Path path = cache.resolve(resourceName);
		Files.copy(url.openStream(), path, StandardCopyOption.REPLACE_EXISTING);
		return path;
	}
	
}
