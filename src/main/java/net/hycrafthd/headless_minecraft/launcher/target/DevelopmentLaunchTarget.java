package net.hycrafthd.headless_minecraft.launcher.target;

import java.nio.file.Paths;
import java.util.concurrent.Callable;

import cpw.mods.modlauncher.api.ILaunchHandlerService;
import cpw.mods.modlauncher.api.ITransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.headless_minecraft.launcher.MinecraftSetup;
import net.hycrafthd.minecraft_downloader.library.DownloadableFile;
import net.hycrafthd.minecraft_downloader.settings.GeneratedSettings;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class DevelopmentLaunchTarget implements ILaunchHandlerService {
	
	@Override
	public String name() {
		return "development";
	}
	
	@Override
	public void configureTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		final ProvidedSettings settings = MinecraftSetup.getInstance().getSettings();
		final GeneratedSettings generatedSettings = settings.getGeneratedSettings();
		
		// Add all libraries to the transforming path
		generatedSettings.getDownloadableFiles() //
				.stream() //
				.filter(downloadableFile -> !downloadableFile.isNative()) //
				.filter(DownloadableFile::hasDownloadedFile) //
				.map(DownloadableFile::getDownloadedFile) //
				.forEach(file -> builder.addTransformationPath(file.toPath()));
		
		// Add mapped minecraft jar
		builder.addTransformationPath(Paths.get(Constants.DEVELOPMENT_MAPPED_MINECRAFT));
		
		// Add headless minecraft implementation
		builder.addTransformationPath(Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD));
	}
	
	@Override
	public Callable<Void> launchService(String[] arguments, ITransformingClassLoader launchClassLoader) {
		MinecraftSetup.destroy();
		return () -> {
			return null;
		};
	}
	
}
