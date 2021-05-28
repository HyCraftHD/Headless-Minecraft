package net.hycrafthd.headless_minecraft.launcher.target;

import java.nio.file.Paths;

import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class DevelopmentLaunchTarget extends BaseLaunchTarget {
	
	@Override
	public String name() {
		return "development-client";
	}
	
	@Override
	protected void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder, ProvidedSettings settings) {
		// Add mapped minecraft jar
		builder.addTransformationPath(Paths.get(Constants.DEVELOPMENT_MAPPED_MINECRAFT));
	}
}