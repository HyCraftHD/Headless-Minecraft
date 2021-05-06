package net.hycrafthd.headless_minecraft.launcher.target;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.jar.JarFile;

import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.headless_minecraft.launcher.util.EnumerationUtil;
import net.hycrafthd.headless_minecraft.launcher.util.ManifestUtil;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class ProductionLaunchTarget extends BaseLaunchTarget {
	
	@Override
	public String name() {
		return "production";
	}
	
	@Override
	protected void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		final ProvidedSettings settings = MinecraftSetup.getInstance().getSettings();
		
		// Add minecraft jar
		builder.addTransformationPath(settings.getClientJarFile().toPath());
		
		// Add headless minecraft implementation
		
		final String implementationJar = ManifestUtil.findClassPathManifests().stream().filter(manifest -> {
			return manifest.getMainAttributes().containsKey(Constants.PRODUCTION_IMPLEMENTATION_JAR);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find manifest property " + Constants.PRODUCTION_IMPLEMENTATION_JAR)).getMainAttributes().getValue(Constants.PRODUCTION_IMPLEMENTATION_JAR);
		
		try {
			final URL url = new URL("jar:classpath:" + implementationJar + "!/");
			final URLConnection connection = url.openConnection();
			
			if (!(connection instanceof JarURLConnection)) {
				throw new IllegalStateException(url + " does not point to a jar file");
			}
			
			final JarFile file = ((JarURLConnection) connection).getJarFile();
			
			builder.setResourceEnumeratorLocator(resource -> {
				try {
					if (file.getJarEntry(resource) == null) {
						return Collections.emptyEnumeration();
					} else {
						System.out.println(new URL(url, resource));
						return EnumerationUtil.singleEntry(new URL(url, resource));
					}
				} catch (IOException ex) {
					throw new IllegalStateException("A resource find threw an exception", ex);
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException("An error occured to load headless minecraft implementation jar to the classpath", ex);
		}
	}
}
