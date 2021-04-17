package net.hycrafthd.headless_minecraft.launcher.target;

import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;

public class ProductionLaunchTarget extends BaseLaunchTarget {
	
	@Override
	public String name() {
		return "production";
	}
	
	@Override
	protected void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		
	}
}
