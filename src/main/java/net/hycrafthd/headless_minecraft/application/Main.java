package net.hycrafthd.headless_minecraft.application;

import java.net.URL;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import net.hycrafthd.headless_minecraft.application.classloader.ApplicationClassLoader;
import net.hycrafthd.headless_minecraft.util.ManifestReader;
import net.hycrafthd.headless_minecraft.util.URLStreamHandlerClassPath;

public class Main {
	
	public static final ApplicationClassLoader CLASSLOADER = new ApplicationClassLoader();
	
	public static void main(String[] args) {
		// Set url stream handler to handle classpath urls (used for jar in jars)
		URL.setURLStreamHandlerFactory(protocol -> "classpath".equals(protocol) ? new URLStreamHandlerClassPath() : null);
		
		// Find our manifest
		final Manifest manifest = ManifestReader.findManifests().stream().filter(manifestToCheck -> {
			final Attributes mainAttributes = manifestToCheck.getMainAttributes();
			
			return mainAttributes.containsKey(Constants.CLASSPATH_DIRECTORY) && mainAttributes.containsKey(Constants.CLASSPATH_NAMES);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find our manifest file"));
		
		// Launch the launcher jar
		try {
			final Class<?> entryClass = Class.forName("net.hycrafthd.headless_minecraft.launcher.Main", true, CLASSLOADER);
			entryClass.getMethod("main", String[].class).invoke(null, (Object) args);
		} catch (Throwable ex) {
			throw new IllegalStateException("An error has occured while launching headless minecraft", ex);
		}
	}
	
}
