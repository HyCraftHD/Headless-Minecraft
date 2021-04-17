package net.hycrafthd.headless_minecraft.launcher.target;

import java.util.concurrent.Callable;

import cpw.mods.modlauncher.api.ILaunchHandlerService;
import cpw.mods.modlauncher.api.ITransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.minecraft_downloader.library.DownloadableFile;
import net.hycrafthd.minecraft_downloader.settings.GeneratedSettings;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public abstract class BaseLaunchTarget implements ILaunchHandlerService {
	
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
		
		configureTargetSpecificTransformationClassLoader(builder);
	}
	
	protected abstract void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder);
	
	@Override
	public Callable<Void> launchService(String[] arguments, ITransformingClassLoader launchClassLoader) {
		MinecraftSetup.destroy();
		return () -> {
			
			final Class<?> entryClass = Class.forName("net.hycrafthd.headless_minecraft.Main", true, launchClassLoader.getInstance());
			entryClass.getMethod("main", String[].class).invoke(null, (Object) arguments);
			
			return null;
		};
	}
	
}
