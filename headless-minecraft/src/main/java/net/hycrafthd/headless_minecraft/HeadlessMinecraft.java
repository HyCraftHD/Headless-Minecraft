package net.hycrafthd.headless_minecraft;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Callable;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;

import cpw.mods.modlauncher.api.ILaunchHandlerService;
import cpw.mods.modlauncher.api.ITransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;

public class HeadlessMinecraft implements ILaunchHandlerService {
	
	@Override
	public String name() {
		return "launchHeadless";
	}
	
	@Override
	public void configureTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		builder.addTransformationPath(Paths.get("D:\\Programmieren\\Java\\Forge\\U Team\\1.16.5 Projects\\Headless-Minecraft\\Headless-Minecraft\\minecraft\\build\\mapped\\mapped-minecraft.jar"));
		builder.addTransformationPath(Paths.get("D:\\Programmieren\\Java\\Forge\\U Team\\1.16.5 Projects\\Headless-Minecraft\\Headless-Minecraft\\bin\\main"));
	}
	
	@Override
	public Callable<Void> launchService(String[] arguments, ITransformingClassLoader launchClassLoader) {
		launchClassLoader.addTargetPackageFilter(s -> !s.startsWith("net.hycrafthd.headless_minecraft.mixin"));
		
		System.out.println("LAUNCH SERVICE" + HeadlessMinecraft.class.getClassLoader());
		return () -> {
			System.out.println("LAUNCHIN NOW" + launchClassLoader);
			
			System.out.println(Arrays.asList(arguments));
			final Class<?> xx = Class.forName("net.hycrafthd.headless_minecraft.Start", true, launchClassLoader.getInstance());
			xx.getMethod("main", String[].class).invoke(null, (Object) arguments);
			
			return null;
		};
	}
	
}
