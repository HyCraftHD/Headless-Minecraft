package net.hycrafthd.headless_minecraft.launcher.target;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
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
		
		// TODO only wip for testing
		
		// Add headless minecraft implementation
		try {
			builder.addTransformationPath(Paths.get(this.getClass().getResource("/headless_minecraft_implementation-1.16.5-1.0.0-SNAPSHOT.jar").toURI()));
		} catch (URISyntaxException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
