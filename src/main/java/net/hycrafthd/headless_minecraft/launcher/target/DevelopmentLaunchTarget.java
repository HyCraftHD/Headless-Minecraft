package net.hycrafthd.headless_minecraft.launcher.target;

import java.util.concurrent.Callable;

import cpw.mods.modlauncher.api.ILaunchHandlerService;
import cpw.mods.modlauncher.api.ITransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;

public class DevelopmentLaunchTarget implements ILaunchHandlerService {
	
	@Override
	public String name() {
		return "development";
	}
	
	@Override
	public void configureTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		
	}
	
	@Override
	public Callable<Void> launchService(String[] arguments, ITransformingClassLoader launchClassLoader) {
		return () -> {
			return null;
		};
	}
	
}
