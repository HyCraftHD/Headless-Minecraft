package net.hycrafthd.headless_minecraft.launcher.target;

import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.jar.Manifest;

import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.Constants;

public class DevelopmentLaunchTarget extends BaseLaunchTarget {
	
	@Override
	public String name() {
		return "development";
	}
	
	@Override
	public void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		// Add mapped minecraft jar
		builder.addTransformationPath(Paths.get(Constants.DEVELOPMENT_MAPPED_MINECRAFT));
		
		// Add headless minecraft implementation
		final Path implementationBuild = Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD);
		builder.addTransformationPath(implementationBuild);
		
		// Add test plugin build
		final Path testPluginBuild = Paths.get(Constants.DEVELOPMENT_TEST_PLUGIN_BUILD);
		builder.addTransformationPath(testPluginBuild);
		
		// Supply manifest for implementation and test plugin
		builder.setManifestLocator(connection -> {
			// Only check for non jar files
			if (!(connection instanceof JarURLConnection)) {
				final URL url = connection.getURL();
				try {
					if (Paths.get(url.toURI()).startsWith(implementationBuild)) {
						
						// TODO
						final Manifest manifest = new Manifest();
						manifest.getMainAttributes().putValue("Manifest-Version", "1.0");
						manifest.getMainAttributes().putValue("Implementation-Version", "1.66.5");
						manifest.getMainAttributes().putValue("MixinConfigs", "mixin.json");
						
						return Optional.of(manifest);
						//
					}
				} catch (URISyntaxException ex) {
					return Optional.empty();
				}
			}
			return Optional.empty();
		});
	}
}