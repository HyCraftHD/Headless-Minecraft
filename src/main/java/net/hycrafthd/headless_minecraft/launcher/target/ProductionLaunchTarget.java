package net.hycrafthd.headless_minecraft.launcher.target;

import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.headless_minecraft.launcher.setup.PluginSetup;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class ProductionLaunchTarget extends BaseLaunchTarget {
	
	@Override
	public String name() {
		return "production-client";
	}
	
	@Override
	protected void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		final ProvidedSettings settings = MinecraftSetup.getInstance().getSettings();
		
		// Add minecraft jar
		builder.addTransformationPath(settings.getClientJarFile().toPath());
		
		// Add plugins and implementation
		PluginSetup.getInstance().getPluginCanidates().forEach((path, jarFile) -> builder.addTransformationPath(path));
	}
}
