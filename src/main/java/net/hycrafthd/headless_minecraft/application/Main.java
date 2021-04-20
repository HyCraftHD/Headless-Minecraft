package net.hycrafthd.headless_minecraft.application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import net.hycrafthd.headless_minecraft.application.classloader.ApplicationClassLoader;
import net.hycrafthd.headless_minecraft.util.ManifestReader;
import net.hycrafthd.headless_minecraft.util.StreamUtil;
import net.hycrafthd.headless_minecraft.util.URLStreamHandlerClassPath;

public class Main {
	
	public static final ApplicationClassLoader CLASSLOADER = new ApplicationClassLoader();
	
	public static void main(String[] args) {
		// Set url stream handler to handle classpath urls (used for jar in jars)
		URL.setURLStreamHandlerFactory(protocol -> "classpath".equals(protocol) ? new URLStreamHandlerClassPath() : null);
		
		// Find our manifest
		final Attributes attributes = ManifestReader.findManifests().stream().filter(manifest -> {
			final Attributes mainAttributes = manifest.getMainAttributes();
			
			return mainAttributes.containsKey(Constants.CLASSPATH_DIRECTORY) && mainAttributes.containsKey(Constants.CLASSPATH_NAMES);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find our manifest file")).getMainAttributes();
		
		// Build urls for classpath
		final String directory = attributes.getValue(Constants.CLASSPATH_DIRECTORY);
		final String allNames = attributes.getValue(Constants.CLASSPATH_NAMES);
		
		final String[] names = allNames.split(": ");
		
		URL[] rsrcUrls = new URL[names.length + 1];
		for (int i = 0; i < names.length; i++) {
			String rsrcPath = names[i];
			try {
				rsrcUrls[i] = new URL("jar:classpath:" + directory + rsrcPath + "!/");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			System.out.println(rsrcUrls[rsrcUrls.length - 1]);
			rsrcUrls[rsrcUrls.length - 1] = new URL("classpath:" + "./");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Arrays.asList(rsrcUrls).forEach(s -> System.out.println(s));
		
		ClassLoader jceClassLoader = new URLClassLoader(rsrcUrls, null);
		
		System.out.println(jceClassLoader.getResource("net/hycrafthd/headless_minecraft/launcher/Constants.class"));
		System.out.println(jceClassLoader.getResource("org/objectweb/asm/Constants.class"));
		
		System.out.println("_____________________________________");
		
		try {
			StreamUtil.toStream(jceClassLoader.getResources(JarFile.MANIFEST_NAME)).forEach(s -> {
				try {
					System.out.println(s);
					Manifest manifest = new Manifest(s.openStream());
					System.out.println(manifest);
					System.out.println(manifest.getMainAttributes().keySet());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			System.out.println(":::::::::::::::::::::::::::::::::::::::::::");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			Class<?> clazz = Class.forName("net.hycrafthd.headless_minecraft.launcher.Constants", true, jceClassLoader);
			System.out.println(clazz);
			System.out.println(clazz.getClassLoader());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Launch the launcher jar
		try {
			final Class<?> entryClass = Class.forName("net.hycrafthd.headless_minecraft.launcher.Main", true, CLASSLOADER);
			entryClass.getMethod("main", String[].class).invoke(null, (Object) args);
		} catch (Throwable ex) {
			throw new IllegalStateException("An error has occured while launching headless minecraft", ex);
		}
	}
	
}
