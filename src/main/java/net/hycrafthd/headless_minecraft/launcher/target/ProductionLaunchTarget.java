package net.hycrafthd.headless_minecraft.launcher.target;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;

import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.headless_minecraft.launcher.Main;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.headless_minecraft.launcher.util.ManifestReader;
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
		
		final Attributes attributes = ManifestReader.findManifests().stream().filter(manifest -> {
			return manifest.getMainAttributes().containsKey(Constants.PRODUCTION_IMPLEMENTATION_JAR);
		}).findAny().orElseThrow(() -> new IllegalStateException("Cannot find our manifest file")).getMainAttributes();
		
		final String implementationJar = attributes.getValue(Constants.PRODUCTION_IMPLEMENTATION_JAR);
		
		Main.LOGGER.info("Implementation jar is {}", implementationJar);
		
		try {
			final URLClassLoader implementationLoader = new URLClassLoader(new URL[] { new URL("classpath://" + implementationJar) });
			
			builder.setResourceEnumeratorLocator(resource -> {
				try {
					return implementationLoader.findResources(resource);
				} catch (IOException ex) {
					throw new RuntimeException("A resource find threw an exception", ex);
				}
			});
			
		} catch (IOException ex) {
			throw new RuntimeException("An error occured to load headless minecraft implementation jar to the classpath", ex);
		}
	}
}
