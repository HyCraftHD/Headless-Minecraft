package net.hycrafthd.headless_minecraft.application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;

import net.hycrafthd.headless_minecraft.application.classloader.ApplicationClassLoader;
import net.hycrafthd.headless_minecraft.general_launcher.URLUtil;
import net.hycrafthd.headless_minecraft.general_launcher.url.classpath.Handler;
import net.hycrafthd.headless_minecraft.util.ManifestReader;

public class Main {
	
	public static final ApplicationClassLoader CLASSLOADER = new ApplicationClassLoader();
	
	public static void main(String[] args) {
		// Add classpath url stream handler to handle classpath urls (used for jar in jars)
		URLUtil.addUrlHandler(Handler.class);
		
		// Find our manifest and get the values
		final Attributes attributes = ManifestReader.findManifests().stream().filter(manifest -> {
			final Attributes mainAttributes = manifest.getMainAttributes();
			
			return mainAttributes.containsKey(Constants.CLASSPATH_DIRECTORY) && mainAttributes.containsKey(Constants.CLASSPATH_NAMES);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find our manifest file")).getMainAttributes();
		
		final String directory = attributes.getValue(Constants.CLASSPATH_DIRECTORY);
		final String[] names = attributes.getValue(Constants.CLASSPATH_NAMES).split(": ");
		
		// Add urls to classpath
		try {
			// Add all classpath name jars to the classloader
			for (String name : names) {
				CLASSLOADER.addURL(new URL("jar:classpath:" + directory + name + "!/"));
			}
			
			// Add current jar to the classpath
			CLASSLOADER.addURL(new URL("classpath:./"));
		} catch (MalformedURLException ex) {
			throw new IllegalStateException("Could not create url for packed classpath jar", ex);
		}
		
		// Set context classloader
		Thread.currentThread().setContextClassLoader(CLASSLOADER);
		
		// Launch the launcher jar
		try {
			final Class<?> entryClass = Class.forName("net.hycrafthd.headless_minecraft.launcher.Main", true, CLASSLOADER);
			entryClass.getMethod("main", String[].class).invoke(null, (Object) args);
		} catch (Throwable ex) {
			throw new IllegalStateException("An error has occured while launching headless minecraft", ex);
		}
	}
	
}
