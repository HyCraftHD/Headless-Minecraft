package net.hycrafthd.headless_minecraft.launcher.target;

import java.util.concurrent.Callable;
import java.util.stream.Stream;

import cpw.mods.modlauncher.api.ILaunchHandlerService;
import cpw.mods.modlauncher.api.ITransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.Main;
import net.hycrafthd.headless_minecraft.launcher.loading.HeadlessMinecraftLoader;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.minecraft_downloader.library.DownloadableFile;
import net.hycrafthd.minecraft_downloader.settings.GeneratedSettings;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public abstract class BaseLaunchTarget implements ILaunchHandlerService {
	
	@Override
	public void configureTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		final ProvidedSettings settings = HeadlessMinecraftLoader.getMinecraftSetup().getSettings();
		final GeneratedSettings generatedSettings = settings.getGeneratedSettings();
		
		// Add all libraries to the transforming path
		generatedSettings.getDownloadableFiles() //
				.stream() //
				.filter(downloadableFile -> !downloadableFile.isNative()) //
				.filter(DownloadableFile::hasDownloadedFile) //
				.map(DownloadableFile::getDownloadedFile) //
				.distinct() //
				.forEach(file -> builder.addTransformationPath(file.toPath()));
		
		// Add implementation and plugins to the transforming path
		HeadlessMinecraftLoader.getPluginLoader().getPluginFiles().forEach(file -> builder.addTransformationPath(file.getPath()));
		
		configureTargetSpecificTransformationClassLoader(builder, settings);
	}
	
	protected abstract void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder, ProvidedSettings settings);
	
	@Override
	public Callable<Void> launchService(String[] arguments, ITransformingClassLoader launchClassLoader) {
		Main.LOGGER.info("Authenticate minecraft account");
		
		// Authenticate
		final MinecraftSetup setup = HeadlessMinecraftLoader.getMinecraftSetup();
		setup.authenticate();
		
		// Build arguments for implementation with auth args
		final String[] args = Stream.concat(setup.buildAuthenticationArgs().stream(), Stream.of(arguments)).toArray(String[]::new);
		
		return () -> {
			Main.LOGGER.info("Launch headless minecraft");
			
			final Class<?> entryClass = Class.forName("net.hycrafthd.headless_minecraft.Main", true, launchClassLoader.getInstance());
			entryClass.getMethod("main", String[].class).invoke(null, (Object) args);
			
			return null;
		};
	}
	
}
