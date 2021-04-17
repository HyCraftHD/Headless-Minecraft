package net.hycrafthd.headless_minecraft.launcher.target;

import java.nio.file.Paths;

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
		builder.addTransformationPath(Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD));
	}
}