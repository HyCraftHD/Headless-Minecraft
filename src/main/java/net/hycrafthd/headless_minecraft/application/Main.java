package net.hycrafthd.headless_minecraft.application;

import java.net.URL;

import net.hycrafthd.headless_minecraft.application.classloader.ApplicationClassLoader;
import net.hycrafthd.headless_minecraft.util.ManifestReader;
import net.hycrafthd.headless_minecraft.util.URLStreamHandlerClassPath;

public class Main {
	
	public static final ApplicationClassLoader CLASSLOADER = new ApplicationClassLoader();
	
	public static void main(String[] args) {
		// Set url stream handler to handle classpath urls (used for jar in jars)
		URL.setURLStreamHandlerFactory(protocol -> "classpath".equals(protocol) ? new URLStreamHandlerClassPath() : null);
		
		// TODO launch packed jars
		ManifestReader.findManifests();
		
		// Launch the launcher jar
		try {
			final Class<?> entryClass = Class.forName("net.hycrafthd.headless_minecraft.launcher.Main", true, CLASSLOADER);
			entryClass.getMethod("main", String[].class).invoke(null, (Object) args);
		} catch (Throwable ex) {
			throw new IllegalStateException("An error has occured while launching headless minecraft", ex);
		}
	}
	
}
