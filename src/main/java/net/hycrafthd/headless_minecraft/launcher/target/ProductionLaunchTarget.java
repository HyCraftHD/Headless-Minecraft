package net.hycrafthd.headless_minecraft.launcher.target;

import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class ProductionLaunchTarget extends BaseLaunchTarget {
	
	@Override
	public String name() {
		return "production-client";
	}
	
	@Override
	protected void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder, ProvidedSettings settings) {
		// Add minecraft jar
		builder.addTransformationPath(settings.getClientJarFile().toPath());
	}
}
